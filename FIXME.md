## 2014-09-30

### 文字化け

rabbit に tomcat7 入れて動かしてみると、POST の際に文字化けが発生してしまう。

### context-path

(redirect )が context-path を意識しない。


### オリジナルデータに機種依存文字

(FIXED)

````sh
$ make init
cp h26_syllabus.doc ../public/o/
ruby init.rb ../db/enq4.db < init.csv
init.rb:8:in `split': invalid byte sequence in UTF-8 (ArgumentError)
	from init.rb:8:in `<main>'
make: *** [init] Error 1
````
