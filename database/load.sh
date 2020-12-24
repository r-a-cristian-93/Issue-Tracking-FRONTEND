docker exec -it hd_database mysql -uroot -zproot --host localhost --port 3306 -e 'SOURCE /share/script.sql;'
