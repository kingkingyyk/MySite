from django.db import models


class Profile(models.Model):
    name = models.TextField()
    profile_picture = models.URLField(blank=True, null=True)
    uva_id = models.IntegerField()


class ProfileLink(models.Model):
    icon = models.TextField(blank=True, null=True)
    name = models.TextField()
    link = models.TextField()
    internal = models.BooleanField()
    display_order = models.IntegerField()

    def __str__(self):
        return ('Internal' if self.internal else 'External') + ' > ' + self.name


class InternalSimplePage(models.Model):
    name = models.TextField()
    content = models.TextField()

    def __str__(self):
        return self.name


class ExperienceCategory(models.Model):
    name = models.TextField()

    def __str__(self):
        return self.name


class ExperienceEntry(models.Model):
    name = models.TextField()
    description = models.TextField(blank=True, null=True)
    level = models.TextField(blank=True, null=True)
    tag = models.TextField(blank=True, null=True)
    category = models.ForeignKey(ExperienceCategory, on_delete=models.CASCADE)

    def __str__(self):
        return self.category.name + ' > ' + self.name


class AwardCategory(models.Model):
    name = models.TextField()

    def __str__(self):
        return self.name


class AwardEntry(models.Model):
    name = models.TextField()
    description = models.TextField(blank=True, null=True)
    tag = models.TextField(blank=True, null=True)
    category = models.ForeignKey(AwardCategory, on_delete=models.CASCADE)

    def __str__(self):
        return self.category.name + ' > ' + self.name


class ProjectCategory(models.Model):
    name = models.TextField()

    def __str__(self):
        return self.name


class Project(models.Model):
    name = models.TextField()
    tag = models.TextField()
    description = models.TextField()
    url = models.URLField(blank=True, null=True)
    category = models.ForeignKey(ProjectCategory, on_delete=models.CASCADE)

    def __str__(self):
        return self.category.name + ' > ' + self.name

    def tag_tokens(self):
        return sorted([x.strip() for x in self.tag.split(',')], reverse=True)
