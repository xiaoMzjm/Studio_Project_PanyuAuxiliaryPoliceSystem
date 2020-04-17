# 目录结构
1. web：业务web模块
2. common：通用模块
3. advertisement：广告模块
4. user：登录、用户模块
5. suggest：建议和反馈模块

# Swagger
Swagger本地：https://localhost/swagger-ui.html
Swagger阿里云：https://www.keephealthy.top/swagger-ui.html

# 部署

1. 打包
mvn clean package

2. 停止服务
查看java进程：ps -ef | grep

停止java进程：kill -9 pid

3. 上传
scp web/target/web-0.0.1-SNAPSHOT.jar root@39.96.189.242:/home/admin/base

4. 部署
java -jar base.jar --spring.profiles.active=local --ResourceStaticUrl=/Users/zhangjiaming/Documents/fujing/static/

把上面的命令用一个start.sh文件报错，后台启动：nohup ./start.sh &