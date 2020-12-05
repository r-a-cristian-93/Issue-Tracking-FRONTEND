docker stop hd_tomcat
docker rm hd_tomcat
docker create -v /home/taifun/HelpDesk/tomcat/webapps/:/usr/local/tomcat/webapps/ -p 8080:8080 -p 443:8443 --name hd_tomcat hd_tomcat
docker network connect myNetwork hd_tomcat

