#-*- coding: utf-8 -*-
TOMCAT  = /var/lib/tomcat7
APP = enq4
VERSION = 0.5.1

all:
	@echo "'make jetty' to run server at 3000/tcp."
	@echo "'make immutant && make run' is for immutant. 8080/tcp."
	@echo "'make war && makt tomcat deloy onto tomcat7."
	@echo "'make init'"

init:
	(cd resources/data && make clean all init)

# jetty, 3000/tcp
jetty:
	lein ring uberjar

jetty-run:
	java -jar target/${APP}-${VERSION}-standalone.jar

# immutant:8080
immutant:
	lein immutant deploy --context-path /${APP}
	@echo check src/${APP}/handler.clj, context-path.

undeploy:
	lein immutant undeploy ${APP}

immutant-run:
	lein immutant run -b 0.0.0.0

# tomcat, 8080/tcp
war:
	lein ring uberwar
	touch war

tomcat: war
	cp target/${APP}-${VERSION}-standalone.war ${TOMCAT}/webapps/${APP}.war
	chown tomcat7:tomcat7 ${TOMCAT}/webapps/${APP}.war
	rm -r ${TOMCAT}/webapps/${APP}
	service tomcat7 restart

# 動き出してからの db.sqlite3 上書きはまずいだろ。
db:
	mkdir -p ${TOMCAT}/resources/db
	cp resources/db/${APP}.db ${TOMCAT}/resources/db/
	chown -R tomcat7:tomcat7 ${TOMCAT}/resources
clean:
	${RM} target/*.jar target/*.war
