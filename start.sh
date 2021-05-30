docker start hd_frontend
docker exec hd_frontend cp /usr/local/tomcat/webapps/server.xml /usr/local/tomcat/conf/
docker stop hd_frontend
docker start $1 hd_frontend
