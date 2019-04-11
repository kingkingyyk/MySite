from django.contrib import admin
from .models import *

# Register your models here.
admin.site.register(Profile)
admin.site.register(ProfileLink)
admin.site.register(ProjectCategory)
admin.site.register(Project)
admin.site.register(InternalSimplePage)
admin.site.register(ExperienceCategory)
admin.site.register(ExperienceEntry)
admin.site.register(AwardCategory)
admin.site.register(AwardEntry)
