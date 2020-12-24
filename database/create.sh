docker stop hd_database
docker rm hd_database
docker create -v $PWD/share:/share -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 --name hd_database hd_database
docker network connect myNetwork hd_database
