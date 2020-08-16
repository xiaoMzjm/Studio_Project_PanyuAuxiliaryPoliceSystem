### 一、打包
mvn clean package -Dmaven.test.skip=true

### 二、上传
scp biz/main/target/base.jar root@39.96.189.242:/home/admin/base

### 三、停止服务
查看java进程：ps -ef | grep、停止java进程：kill -9 pid

### 部署
1. mac机器 ：nohup java -jar base.jar -Xmx1g --spring.profiles.active=online --ResourceStaticUrl=/Users/zhangjiaming/Documents/fujing/static/  &
2. linux机器：nohup java -jar base.jar -Xmx1g --spring.profiles.active=online --ResourceStaticUrl=/home/admin/base/fujing/static/ &

### linux杀进程+启动脚本
```shell
#!/bin/sh
RESOURCE_NAME=base.jar
 
tpid=`ps -ef|grep $RESOURCE_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
echo 'Stop Process...'
kill -15 $tpid
fi
sleep 5
tpid=`ps -ef|grep $RESOURCE_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
echo 'Kill Process!'
kill -9 $tpid
else
echo 'Stop Success!'
fi
 
tpid=`ps -ef|grep $RESOURCE_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
    echo 'App is running.'
else
    echo 'App is NOT running.'
fi
 
rm -f tpid
nohup java -jar base.jar -Xmx1g --spring.profiles.active=online --ResourceStaticUrl=/home/admin/base/fujing/static/ &
echo $! > tpid
echo Start Success!
```