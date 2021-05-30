docker stop hd_frontend
docker rm hd_frontend
docker create -v $PWD/webapps/:/usr/local/tomcat/webapps/ -p 443:8443 -p 80:8080 --name hd_frontend hd_frontend
docker network connect myNetwork hd_frontend

