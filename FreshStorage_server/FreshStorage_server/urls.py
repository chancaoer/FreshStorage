"""untitled2 URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from django.contrib import admin
from yy import views

urlpatterns = [
    url(r'^loginc/', views.loginc),
    url(r'^regis/', views.regis),
    url(r'^title/', views.title),
    url(r'^title1/', views.title1),
    url(r'^logink/', views.logink),
    url(r'^date/', views.date),
    url(r'^nowhum/', views.nowhum),
    url(r'^nowtem/', views.nowtem),
    url(r'^abnormal/', views.abnormal),
    url(r'^modifytem/', views.modifytem),
    url(r'^modifyt/', views.modifyt),
    url(r'^modifyhum/', views.modifyhum),
    url(r'^modifyh/', views.modifyh),
    url(r'^modify/', views.modify),
    url(r'^police/', views.police),
    url(r'^regisg/', views.regisg),
    url(r'^loging/', views.loging),

]
