1. 打包
mvn clean package -Dmaven.test.skip=true

2. 上传
上传 scp biz/main/target/base.jar root@39.96.189.242:/home/admin/base

3. 停止服务
查看java进程：ps -ef | grep、停止java进程：kill -9 pid

3. 部署
nohup java -jar base.jar -Xmx1g --spring.profiles.active=online --ResourceStaticUrl=/Users/zhangjiaming/Documents/fujing/static/  &
nohup java -jar base.jar -Xmx1g --spring.profiles.active=online --ResourceStaticUrl=/home/admin/base/fujing/static/ &


> 把上面的"部署"命令用一个start.sh文件报错，后台启动：nohup ./start.sh &