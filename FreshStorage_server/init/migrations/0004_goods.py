# -*- coding: utf-8 -*-
# Generated by Django 1.11.13 on 2018-09-18 02:27
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('yy', '0003_abnormal_rid'),
    ]

    operations = [
        migrations.CreateModel(
            name='Goods',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('rid', models.CharField(default='', max_length=100)),
                ('gid', models.CharField(default='', max_length=100)),
                ('gclass', models.CharField(default='', max_length=100)),
                ('gstate', models.CharField(default='', max_length=100)),
            ],
        ),
    ]