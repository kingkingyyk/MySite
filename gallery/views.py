from django.shortcuts import render
from main.models import *
from .models import *
import operator

def index(request):
    cameras = sorted({x.id:str(x) for x in Camera.objects.all()}.items(), key=operator.itemgetter(1))
    lenses = sorted({x.id:str(x) for x in Lens.objects.all()}.items(), key=operator.itemgetter(1))
    tags = sorted({x.id:x.name for x in Tag.objects.all()}.items(), key=operator.itemgetter(1))

    return render(request, 'gallery.html',
                  {'page': InternalSimplePage.objects.filter(name='Gallery').first(),
                   'cameras': cameras,
                   'lenses': lenses,
                   'tags': tags,
                   })


def query_pictures(request):
    camera_id_list = request.GET.get('cameras','').split(',')
    if camera_id_list[0] == '':
        camera_id_list = []

    lens_id_list = request.GET.get('lenses','').split(',')
    if lens_id_list[0] == '':
        lens_id_list = []

    tags_id_list = request.GET.get('tags','').split(',')
    if tags_id_list[0] == '':
        tags_id_list = []

    filtered_pictures = []

    if len(camera_id_list) > 0 and len(lens_id_list) > 0 and len(tags_id_list) > 0:
        pictures = Picture.objects.all()
        tag_map = {x.id:x for x in Tag.objects.all()}
        for picture in pictures:
            if str(picture.camera.id) in camera_id_list and str(picture.lens.id) in lens_id_list:
                for tag_id in tags_id_list:
                    tag = tag_map[int(tag_id)]
                    if PictureTag.objects.filter(picture=picture, tag=tag).exists():
                        filtered_pictures.append(picture)
                        break
    return render(request, 'gallery_picture_card.html',
                  {'pictures': filtered_pictures})
