# -*- coding: utf-8 -*-
# Generated by Django 1.11.13 on 2018-09-17 02:49
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('yy', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='room',
            name='nowhum',
            field=models.CharField(default='', max_length=100),
        ),
        migrations.AddField(
            model_name='room',
            name='nowtem',
            field=models.CharField(default='', max_length=100),
        ),
    ]