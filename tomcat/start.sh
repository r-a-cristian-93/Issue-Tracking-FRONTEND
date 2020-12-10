docker start hd_tomcat
docker exec hd_tomcat cp /usr/local/tomcat/webapps/server.xml /usr/local/tomcat/conf/
docker stop hd_tomcat
docker start $1 hd_tomcat
