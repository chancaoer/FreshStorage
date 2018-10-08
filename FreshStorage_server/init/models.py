# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

class Admin(models.Model):
    cid = models.CharField(max_length=100)
    name = models.CharField(max_length=100)
class Nowroom(models.Model):
    rid=models.CharField(max_length=100)
    nowtem = models.CharField(max_length=100)
    nowhum = models.CharField(max_length=100)
    nowtime=models.CharField(max_length=100)
class Goods(models.Model):

    rid = models.CharField(max_length=100, default="")
    gid = models.CharField(max_length=100, default="")
    gclass=models.CharField(max_length=100, default="")
    gstate=models.CharField(max_length=100, default="")
    time = models.CharField(max_length=100,default="")

class Room(models.Model):
    sid = models.CharField(max_length=100, default="")
    rid = models.CharField(max_length=100)
    deftem = models.CharField(max_length=100)
    defhum = models.CharField(max_length=100)
    nowtem = models.CharField(max_length=100,default="")
    nowhum = models.CharField(max_length=100,default="")
    admin = models.CharField(max_length=100)
class Abnormal(models.Model):
    rid = models.CharField(max_length=100, default="")
    time = models.CharField(max_length=100)
    content = models.CharField(max_length=200)

# Create your models here.
