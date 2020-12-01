docker stop hd_tomcat
docker rm hd_tomcat
docker rmi hd_tomcat
docker build -t hd_tomcat .
