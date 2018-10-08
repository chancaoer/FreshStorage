# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.http import HttpResponse

from django.shortcuts import render
from yy.models import Admin
from yy.models import Room
from yy.models import Goods
from yy.models import Abnormal
import datetime
import json

TEST=0
RID="00"
def title(request):
    list = Goods.objects.all()
    result = []
    for var in list:
        b = {}
        b['rid'] = var.rid
        b['gid'] = var.gid
        b['gclass'] = var.gclass
        b['gstate']=var.gstate
        b['time'] = var.time
        result.append(b)
    want = json.dumps(result)
    print want
    return HttpResponse(want)
def title1(request):
    list = Room.objects.all()
    result = []
    for var in list:
        b = {}
        b['rid'] = var.rid
        b['nowtem'] = var.nowtem
        b['nowhum'] = var.nowhum
        b['nowtime']=var.admin
        result.append(b)
    want = json.dumps(result)
    print want
    return HttpResponse(want)

def loginc(request):
    userpassword = request.POST.get("cardId", "")
    print userpassword
    result = []
    response = Admin.objects.filter(cid=userpassword)
    #list = Nowroom.objects.all()
    for var in response:
        b={}
        b['state'] = 'true'
        result.append(b)
    if len(result) == 0:
        b = {}
        b['state'] = 'false'##该卡号未注册，请联系管理员注册
        result.append(b)
        return HttpResponse('false')
    else:
        return HttpResponse('true')
    print result




    # result = []
    # for row in list:
    #     b={}
    #     id, rid, nowtem, nowhum, nowtime = row
    #     b['rid'] = rid
    #     b['nowtem'] = nowtem
    #     b['nowhum'] = nowhum
    #     b['nowtime'] = nowtime
    #     result.append(b)
    # want = json.dumps(result)

def logink(request):
    username=request.POST.get("name", "")
    userpassword = request.POST.get("password", "")
    result = []
    response = Admin.objects.filter(cid=userpassword)
    #list = Nowroom.objects.all()
    for var in response:
        b={}
        b['cid'] = var.cid
        b['name'] = var.name
        b['state'] = 'true'
        result.append(b)
    if len(result) == 0:
        b = {}
        b['cid'] = 'null'
        b['name'] = 'null'
        b['state'] = 'false'##该卡号未注册，请联系管理员注册
        result.append(b)
        return HttpResponse('Error1')
    else:
         for dic in result:
             if (cmp(username, dic['name']) == 0):
                 print username
                 print dic['name']
                 dic['state'] = 'true'######登录成功
                 return HttpResponse('OK')
             else:
                 return HttpResponse('Error2')#######用户名错了
    print len(result)
    print result

    # result = []
    # for row in list:
    #     b={}
    #     id, rid, nowtem, nowhum, nowtime = row
    #     b['rid'] = rid
    #     b['nowtem'] = nowtem
    #     b['nowhum'] = nowhum
    #     b['nowtime'] = nowtime
    #     result.append(b)
    # want = json.dumps(result)

def loging(request):
    gid = request.POST.get("gid", "")
    gstate="已出库"
    result = []
    response = Goods.objects.filter(gid=gid)
    #list = Nowroom.objects.all()
    for var in response:
        rid=var.rid
        gclass=var.gclass
        b={}
        b['state'] = 'true'
        result.append(b)
    print len(result)
    if len(result) == 0:
        b = {}
        b['state'] = 'false'##货物无入库记录，请联系管理员核对
        result.append(b)
        return HttpResponse('false')
    else:
        time = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        test1 = Goods(rid=rid, gid=gid, gclass=gclass, gstate=gstate,time=time)
        test1.save()
        return HttpResponse('true')
    print result

def regis(request):
    username = request.POST.get("name", "")
    userpassword = request.POST.get("cardId", "")
    result = []
    response = Admin.objects.filter(cid=userpassword)
    for var in response:
        b = {}
        b['state'] = 'null'
        result.append(b)
    print result
    if len(result) == 0:
        b={}
        b['state'] = 'OK'  #####草儿注册成功
        test1 = Admin(cid=userpassword,name=username)
        test1.save()
        return HttpResponse("true")
        result.append(b)
    else:
        b = {}
        b['state']="NO"######该卡已注册
        result.append(b)
        return HttpResponse("false")
    print len(result)


def regisg(request):
    rid = request.POST.get("rid", "")
    gid = request.POST.get("gid", "")
    gclass=request.POST.get("gclass", "")
    gstate = request.POST.get("gstate", "")

    result = []
    response = Goods.objects.filter(gid=gid)
    for var in response:
        b = {}
        b['state'] = 'null'
        result.append(b)
    print result
    if len(result) == 0:
        time = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        b={}
        b['state'] = 'OK'  #####草儿注册成功
        test1 = Goods(rid=rid,gid=gid,gclass=gclass,gstate=gstate,time=time)
        test1.save()
        return HttpResponse("true")
        result.append(b)
    else:
        b = {}
        b['state']="NO"######该卡已注册
        result.append(b)
        return HttpResponse("false")
    print len(result)

def police(request):
    admin = request.POST.get("admin", "")
    result = []
    response = Room.objects.filter(admin=admin)
    tt=0
    #list = Goods.objects.all()
    for var in response:
        b = {}
        b['rid'] = var.rid
        b['deftem'] = var.deftem
        b['nowtem'] = var.nowtem
        b['defhum'] = var.defhum
        b['nowhum'] = var.nowhum
        result.append(b)
    for dic in result:
        print 'okk'
        a=float(dic['deftem'])
        b=float(dic['nowtem'])
        c = float(dic['defhum'])
        d = float(dic['nowhum'])
        time=datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        if (((b-a)>=-2)and((b-a)<=2) and ((d-c)>=-5)and((d-c)<=4)):
            tt=tt+1
            print "OK"
        elif(((b-a)<-2) and ((d-c)<-5)):
            test1 = Abnormal(rid=dic['rid'], time=time, content="温度过低且湿度过低")
            test1.save()
            #return HttpResponse("ERROR0")
        elif (((b - a) < -2) and ((d - c) > 4)):
            test1 = Abnormal(rid=dic['rid'], time=time, content="温度过低且湿度过高")
            test1.save()
            #return HttpResponse("ERROR1")
        elif (((b-a)>2) and ((d-c)<-5)):
            test1 = Abnormal(rid=dic['rid'], time=time, content="温度过高且湿度过低")
            test1.save()
            #return HttpResponse("ERROR2")
        elif (((b-a)>2) and ((d - c) > 4)):
            test1 = Abnormal(rid=dic['rid'], time=time, content="温度过高且湿度过高")
            test1.save()
            #return HttpResponse("ERROR3")
        elif((b-a)<-2):
            print "OK2"
            test1 = Abnormal(rid=dic['rid'], time=time,content="温度过低")
            test1.save()
            #return HttpResponse("ERROR4")
        elif ((b-a)>2):
            print "OK3"
            test2 = Abnormal(rid=dic['rid'], time=time, content="温度过高")
            test2.save()
            #return HttpResponse("ERROR5")
        elif((d-c)<-5):
            print "OK4"
            test3 = Abnormal(rid=dic['rid'], time=time, content="湿度过低")
            test3.save()
            #return HttpResponse("ERROR6")
        elif ((d - c) > 4):
            print "OK5"
            test4= Abnormal(rid=dic['rid'], time=time, content="湿度过高")
            test4.save()
            #return HttpResponse("ERROR7")
    if(tt>0):
        return HttpResponse("OK")
    else:
        return HttpResponse("NO")



def abnormal(request):
    list = Abnormal.objects.all()
    result = []
    for var in list:
        b = {}
        b['time'] = var.time
        b['content'] = var.content
        b['rid'] = var.rid
        result.append(b)
    want = json.dumps(result)
    return HttpResponse(want)

def date(request):
    sid = request.POST.get("sid", "")
    nowtem = request.POST.get("nowtem", "")
    nowhum=request.POST.get("nowhum", "")
    print sid
    print nowtem
    print nowhum
    if(len(sid)==0):
        return HttpResponse("NO")
    else:
        test1 = Room.objects.get(sid=sid)
    #####看看能不能直接存进去  不能再转型
        test1.nowtem= nowtem
        test1.nowhum = nowhum
        test1.save()
        return HttpResponse("OK")

#######修改当前温度要用别的服务器
def modifytem(request):
    password = request.POST.get("rid", "")
    deftem = request.POST.get("deftem", "")
    print deftem
######要不要修改数据库中当前温度值  演示的时候再说吧
    test1 = Room.objects.get(rid=password)
    test1.deftem = deftem
    test1.save()
    return HttpResponse("OK")
def modifyhum(request):
    password = request.POST.get("rid", "")
    defhum = request.POST.get("defhum", "")
    print defhum
    test1 = Room.objects.get(rid=password)
    test1.defhum = defhum
    test1.save()
    return HttpResponse("OK")
def modifyt(request):
    global TEST
    global RID
    password = request.POST.get("rid", "")
    tem=request.POST.get("tem", "")
    RID=password
    print len(tem)
    print password
    print tem
    test1 = Room.objects.get(rid=password)
    test1.nowtem = tem
    test1.save()
    TEST=1
    return HttpResponse("OK")

def modifyh(request):
    global TEST
    global RID
    password = request.POST.get("rid", "")
    hum = request.POST.get("hum", "")
    RID = password
    print hum
    test1 = Room.objects.get(rid=password)
    test1.nowhum = hum
    test1.save()
    TEST = 2
    return HttpResponse("OK")

def modify(request):
    result = []
    password = request.POST.get("rid", "")
    tem = request.POST.get("tem", "")
    global TEST
    global RID
    print TEST
    if(TEST==1):
        TEST = 0
        #return HttpResponse("OK")
        response = Room.objects.filter(rid=RID)
        for var in response:
            b = {}
            b['state'] = "OK"
            b['rid'] = var.rid
            b['nowtem'] = var.nowtem
            b['nowhum']=""
        result.append(b)
        want = json.dumps(result)
        return HttpResponse(want)
    elif(TEST==2):
        TEST = 0
        response = Room.objects.filter(rid=RID)
        for var in response:
            b = {}
            b['state'] = "OK"
            b['rid'] = var.rid
            b['nowhum'] = var.nowhum
            b['nowtem'] = ""
        result.append(b)
        want = json.dumps(result)
        return HttpResponse(want)
    else:
        #return HttpResponse("NO")
        b = {}
        b['state'] = "NO"
        b['rid'] = ""
        b['nowhum'] = ""
        b['nowtem'] = ""
        result.append(b)
        want = json.dumps(result)
        return HttpResponse(want)





def nowtem(request):
    admin=request.POST.get("admin", "")
    print admin
    result = []
    response = Room.objects.filter(admin=admin)
    # list = Nowroom.objects.all()
    for var in response:
        b = {}
        b['rid'] = var.rid
        b['deftem'] = var.deftem
        b['nowtem']=var.nowtem
        result.append(b)
    want = json.dumps(result)
    print want
    return HttpResponse(want)

def nowhum(request):
    admin = request.POST.get("admin", "")
    result = []
    response = Room.objects.filter(admin=admin)
    for var in response:
        b = {}
        b['rid'] = var.rid
        b['defhum'] = var.defhum
        b['nowhum'] = var.nowhum
        result.append(b)
    want = json.dumps(result)
    print want
    return HttpResponse(want)

# Create your views here.

