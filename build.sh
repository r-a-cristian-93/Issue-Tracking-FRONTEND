docker stop hd_frontend
docker rm hd_frontend
docker rmi hd_frontend
docker build -t hd_frontend .
