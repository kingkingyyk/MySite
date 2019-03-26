from django.contrib import admin
from .models import *


class PictureAdmin(admin.ModelAdmin):
    list_display = ('flickr_id', 'name', 'url', 'iso', 'aperture', 'shutter_speed', 'focal_length', 'camera', 'lens')


class PictureTagAdmin(admin.ModelAdmin):
    list_display = ('picture', 'tag')
    fields = ('picture', 'tag', 'picture_preview')
    readonly_fields = ['picture_preview']


admin.site.register(Make)
admin.site.register(Camera)
admin.site.register(Lens)
admin.site.register(Tag)
admin.site.register(Picture, PictureAdmin)
admin.site.register(PictureTag, PictureTagAdmin)