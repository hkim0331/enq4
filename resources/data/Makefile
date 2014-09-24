DB = "../db/enq4.db"

all: enq4

enq4:
	cat create.sql | sqlite3  ${DB}

clean:
	${RM} ${DB}

