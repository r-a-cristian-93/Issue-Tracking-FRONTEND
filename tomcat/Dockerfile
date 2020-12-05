FROM tomcat
COPY lib/json-20200518.jar /usr/local/tomcat/lib
COPY lib/mysql-connector-java-8.0.22.jar /usr/local/tomcat/lib
COPY lib/commons-codec-1.14.jar /usr/local/tomcat/lib
COPY lib/jackson-databind-2.10.3.jar /usr/local/tomcat/lib
COPY lib/jackson-annotations-2.10.3.jar /usr/local/tomcat/lib
COPY lib/jackson-core-2.10.3.jar /usr/local/tomcat/lib
COPY lib/java-jwt-3.11.0.jar /usr/local/tomcat/lib

WORKDIR /usr/local/tomcat/webapps
ENV CLASSPATH="/usr/local/tomcat/lib/servlet-api.jar:/usr/local/tomcat/lib/json-20200518.jar:/usr/local/tomcat/lib/tomcat-jdbc.jar:/usr/local/tomcat/lib/commons-codec-1.14.jar:/usr/local/tomcat/lib/jackson-databind-2.10.3.jar:/usr/local/tomcat/lib/jackson-annotations-2.10.3.jar:/usr/local/tomcat/lib/jackson-core-2.10.3.jar:/usr/local/tomcat/lib/java-jwt-3.11.0.jar"



