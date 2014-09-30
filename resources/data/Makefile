DEST = ../db
DB = ${DEST}/enq4.db

all: enq4

enq4:
	if [ ! -d ${DEST} ]; then mkdir ${DEST}; fi
	cat create.sql | sqlite3  ${DB}

init:
	cp h26_syllabus.doc ../public/o/
	ruby init.rb ${DB} < init.csv

clean:
	${RM} ${DB}

