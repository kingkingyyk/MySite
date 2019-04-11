from django.db.models.signals import post_save
from django.dispatch import receiver
from .models import Picture, Make, Lens, Camera
import requests

FLICKR_API_URL = 'https://api.flickr.com/services/rest/'
FLICKR_DISPLAY_PHOTO_URL = 'https://farm{}.staticflickr.com/{}/{}_{}_k.jpg'

def get_exif_data(picture_id):
    data = {}
    params = {
        'format': 'json',
        'nojsoncallback': 1,
        'photo_id': picture_id,
        'api_key': 'f665c808bb573b05caf029298373ec9e'
    }

    # EXIF
    response = requests.get(FLICKR_API_URL,
                            params={**params,**{'method': 'flickr.photos.getExif'}}).json()
    response = response['photo']['exif']
    tags = ['ISO', 'FocalLengthIn35mmFormat', 'FNumber', 'ExposureTime', 'Make', 'Lens', 'Model']
    for tag in tags:
        try:
            data[tag] = [x for x in response if x['tag'] == tag][0]['raw']['_content']
        except:
            data[tag] = None
    # Photo data
    response = requests.get(FLICKR_API_URL,
                            params={**params,**{'method': 'flickr.photos.getInfo'}}).json()

    response = response['photo']
    data['name'] = response['title']['_content']
    data['url'] = response['urls']['url'][0]['_content']
    # Display sizes
    response = requests.get(FLICKR_API_URL,
                            params={**params,**{'method': 'flickr.photos.getSizes'}}).json()
    data['display-url'] = response['sizes']['size'][-2]['source']
    return data


def update_picture_exif(picture):
    data = get_exif_data(picture.flickr_id)
    picture.display_url = data['display-url']
    if False:
        picture.url = data['url']
        picture.aperture = float(data['FNumber']) if data['FNumber'] is not None else None
        picture.focal_length = int(float(data['FocalLengthIn35mmFormat'].split(' ')[0])) if data['FocalLengthIn35mmFormat'] is not None else None
        picture.shutter_speed = data['ExposureTime']
        picture.iso = int(data['ISO']) if data['ISO'] is not None else None
        picture.name = data['name']

    if data['Make'] is not None:
        make = data['Make'].lower()
        make = make[0].upper()+make[1:]
        make_already_exist = Make.objects.filter(name=make).exists()
        if not make_already_exist:
            Make.objects.create(name=make)
        make = Make.objects.filter(name=make).first()

        if data['Lens'] is not None and picture.lens is None:
            if not Lens.objects.filter(model=data['Lens'], make=make).exists():
                lens = Lens.objects.create(model=data['Lens'], make=make)
            else:
                lens = Lens.objects.filter(model=data['Lens'], make=make).first()
            picture.lens = lens

        if data['Model'] is not None and picture.camera is None:
            if not Camera.objects.filter(model=data['Model'], make=make).exists():
                camera = Camera.objects.create(model=data['Model'], make=make)
            else:
                camera = Camera.objects.filter(model=data['Model'], make=make).first()
            picture.camera = camera

    post_save.disconnect(save_picture, sender=Picture)
    picture.save()
    post_save.connect(save_picture, sender=Picture)


@receiver(post_save, sender=Picture)
def create_picture(sender, instance, created, **kwargs):
    if created:
        update_picture_exif(instance)


@receiver(post_save, sender=Picture)
def save_picture(sender, instance, **kwargs):
    #update_picture_exif(instance)
    pass


def init_signals():
    pass
