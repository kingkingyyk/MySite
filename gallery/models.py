from django.db import models
from django.utils.safestring import mark_safe

class Make(models.Model):
    name = models.TextField()

    def __str__(self):
        return self.name


class Camera(models.Model):
    make = models.ForeignKey(Make, on_delete=models.CASCADE)
    model = models.TextField()

    def __str__(self):
        return self.make.name + ' ' + self.model


class Lens(models.Model):
    make = models.ForeignKey(Make, on_delete=models.CASCADE)
    model = models.TextField()

    def __str__(self):
        return self.make.name + ' ' + self.model


class Tag(models.Model):
    name = models.TextField()

    def __str__(self):
        return self.name


class Picture(models.Model):
    flickr_id = models.TextField()
    name = models.TextField(blank=True, null=True)
    url =  models.URLField(blank=True, null=True)
    display_url = models.URLField(blank=True, null=True)
    iso = models.IntegerField(blank=True, null=True)
    aperture = models.FloatField(blank=True, null=True)
    shutter_speed = models.TextField(blank=True, null=True)
    focal_length = models.IntegerField(blank=True, null=True)
    camera = models.ForeignKey(Camera, blank=True, null=True, on_delete=models.CASCADE)
    lens = models.ForeignKey(Lens, blank=True, null=True, on_delete=models.CASCADE)

    def __str__(self):
        return self.url.split('/')[-1] if self.name is None else self.name

    def detailed_information(self):
        iso_info = 'ISO-'+str(self.iso) if self.iso is not None else ''
        aperture_info = 'f/'+(str(self.aperture)[:-2] if str(self.aperture).endswith('.0') else str(self.aperture)) if self.aperture is not None else ''
        ss_info = str(self.shutter_speed)+'s' if self.shutter_speed is not None else ''
        focal_length_info = str(self.focal_length)+'mm' if self.focal_length is not None else ''

        return ', '.join([iso_info, aperture_info, ss_info, focal_length_info])


class PictureTag(models.Model):
    picture = models.ForeignKey(Picture, on_delete=models.CASCADE)
    tag = models.ForeignKey(Tag, on_delete=models.CASCADE)

    def __str__(self):
        return str(self.tag) + ' on ' + str(self.picture)

    def picture_preview(self):
        return mark_safe('<img src="'+self.picture.display_url+'" width="20%"/>')
    picture_preview.short_description = 'Preview'