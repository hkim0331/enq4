#-*- coding: utf-8 -*-
TOMCAT  = /var/lib/tomcat7
APP = enq4
VERSION = 0.4.1

# immutant:8080
immutant:
	lein immutant deploy --context-path /${APP}
	@echo check src/ape/handler.clj, context-path.

undeploy:
	lein immutant undeploy ${APP}

run:
	@echo exec lein immutant run -b 0.0.0.0

# jetty, 3000/tcp
jetty:
	lein ring uberjar
	java -jar target/${APP}-${VERSION}-standalone.jar

# tomcat, 8080/tcp
war:
	lein ring uberwar
	touch war

tomcat: war
	cp target/${APP}-${VERSION}-standalone.war ${TOMCAT}/webapps/${APP}.war
	chown tomcat7:tomcat7 ${TOMCAT}/webapps/${APP}.tar
	rm -r ${TOMCAT}/webapps/${APP}
	service tomcat7 restart

# 動き出してからの db.sqlite3 上書きはまずいだろ。
db:
	mkdir -p ${TOMCAT}/resources/db
	cp resources/db/${APP}.db ${TOMCAT}/resources/db/
	chown -R tomcat7:tomcat7 ${TOMCAT}/resources
clean:
	${RM} target/*.jar target/*.war