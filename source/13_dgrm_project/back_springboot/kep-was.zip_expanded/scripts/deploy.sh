#!/bin/bash

## Delete Old Version
rm -rf /data/was-app/dehs_old
mkdir /data/was-app/dehs_old
chown -R tomcat:was /data/was-app/dehs_old
mv /data/was-app/ROOT/META-INF /data/was-app/dehs_old/
mv /data/was-app/ROOT/WEB-INF /data/was-app/dehs_old/

## Copying From codedeploy to dehs instance ROOT
unzip /data/was-app/codedeploy/target/dehs-online-1.0.1.war -d /data/was-app/ROOT

chown -R tomcat:was /data/was-app/dehs_old
chown -R tomcat:was /data/was-app/ROOT

## 운영서버 SSO 인증 파일 교체용 was 2번 서버이면 sso2.properties 파일을 sso.properties 파일로 교체한다
ipaddr=`hostname -I`
if [ $ipaddr = "172.30.135.24" ]
then
	rm /data/was-app/ROOT/WEB-INF/classes/sso.properties
	mv /data/was-app/ROOT/WEB-INF/classes/sso2.properties /data/was-app/ROOT/WEB-INF/classes/sso.properties
	rm /data/was-app/ROOT/WEB-INF/classes/application.yml
	mv /data/was-app/ROOT/WEB-INF/classes/application2.yml /data/was-app/ROOT/WEB-INF/classes/application.yml
else
    rm /data/was-app/ROOT/WEB-INF/classes/sso2.properties
    rm /data/was-app/ROOT/WEB-INF/classes/application2.yml
fi
