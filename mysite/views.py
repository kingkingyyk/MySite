from django.shortcuts import render
from django.template.loader import render_to_string
from django.http import HttpResponse
from main.models import *
import requests
from lxml import html

def home(request):
    context = {
        'profile': Profile.objects.first(),
        'int_profile_links': ProfileLink.objects.filter(internal=True).order_by('display_order').all(),
        'ext_profile_links': ProfileLink.objects.filter(internal=False).order_by('display_order').all(),
    }
    return render(request, 'home.html', context)


def about(request):
    return render(request, 'about.html',
                  {'page': InternalSimplePage.objects.filter(name='About').first()})


def experience(request):
    exp_entry_map = {x: ExperienceEntry.objects
                        .filter(category=x)
                        .order_by('name').all()
                        for x in ExperienceCategory.objects.order_by('name').all()}
    return render(request, 'experience.html',
                  {'page': InternalSimplePage.objects.filter(name='Experience').first(),
                   'entry_map': exp_entry_map})


def awards(request):
    awd_entry_map = {x: AwardEntry.objects
                        .filter(category=x)
                        .order_by('name').all()
                        for x in AwardCategory.objects.order_by('name').all()}
    return render(request, 'awards.html',
                  {'page': InternalSimplePage.objects.filter(name='Honors & Awards').first(),
                   'entry_map': awd_entry_map})


def awards_uva_percentile(request):
    user_id = Profile.objects.first().uva_id
    tree = html.fromstring(requests.get('https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=20', timeout=5.0).content)
    text = tree.xpath('//tr[@class="sectiontableentry"]/td[@align="left"]/text()')[0].strip()
    total_users = float(text.split(' ')[-1])

    me = float(requests.get('https://uhunt.onlinejudge.org/api/ranklist/{}/0/0'.format(user_id), timeout=2.0).json()[0]['rank'])
    return HttpResponse('Top {0:.3f}%'.format((me/total_users)*100.0));


def awards_uva_rank_list(request):
    JSON_FIELDS = ['rank', 'name', 'username', 'ac', 'nos']
    user_id = Profile.objects.first().uva_id
    users = requests.get('https://uhunt.onlinejudge.org/api/ranklist/{}/3/3'.format(user_id), timeout=2.0).json()
    rank_list = [['<b>'+str(user[field])+'</b>' if user['userid'] == user_id else str(user[field]) for field in JSON_FIELDS] for user in users]
    return HttpResponse(render_to_string('awards_uva_rank_list.html', {'uva_rank_list': rank_list}));


def projects(request):
    proj_entry_map = {x: Project.objects
                        .filter(category=x)
                        .order_by('name').all()
                        for x in ProjectCategory.objects.order_by('name').all()}

    return render(request, 'projects.html',
                  {'page': InternalSimplePage.objects.filter(name='Projects').first(),
                   'entry_map': proj_entry_map})
