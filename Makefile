#-*- coding: utf-8 -*-
TOMCAT  = /var/lib/tomcat7
APP     = enq4
VERSION = 0.4

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
	@echo 'please "sudo su && sudo su tomcat7 && make tomcat"'
	cp target/${APP}-${VERSION}-standalone.war ${TOMCAT}/webapps/${APP}.war
	rm -r ${TOMCAT}/webapps/${APP}
	service tomcat7 restart

db:
	mkdir -p ${TOMCAT}/resources/db
	cp resources/db/enq4.db ${TOMCAT}/resources/db/
	chown -R tomcat7:tomcat7 ${TOMCAT}/resources


clean:
	${RM} target/*.jar target/*.war
