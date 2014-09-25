DEST = ../db
DB = ${DEST}/enq4.db

all: enq4

enq4:
	if [ ! -d ${DEST} ]; then mkdir ${DEST}; fi
	cat create.sql | sqlite3  ${DB}

clean:
	${RM} ${DB}

