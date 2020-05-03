1. 打包
mvn clean package

2. 停止服务
查看java进程：ps -ef | grep
、停止java进程：kill -9 pid

3. 部署
java -jar base.jar --spring.profiles.active=online --ResourceStaticUrl=/Users/zhangjiaming/Documents/fujing/static/

> 把上面的"部署"命令用一个start.sh文件报错，后台启动：nohup ./start.sh &