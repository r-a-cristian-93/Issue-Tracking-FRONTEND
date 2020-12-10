set -x
rm helpdesk/WEB-INF/classes/*
javac -d ./helpdesk/WEB-INF/classes/ ./helpdesk/WEB-INF/src/*.java
touch ./helpdesk/WEB-INF/web.xml
