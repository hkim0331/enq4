(ns enq4.models
  (:use korma.db korma.core)
  (:require [clojure.java.io :refer [copy file]])
  (:require [enq4.time :refer [now]]))

(defdb enq4
  (sqlite3 {:db "resources/db/enq4.db"}))

(defentity enq4
  (entity-fields :id :name :subject :q1 :q2 :q3 :q4
                 :original :upload :timestamp :note :in_use))

(defn all-enquets []
  (select enq4))

(defn avail-enquets []
  (select enq4 (where {:in_use 1})))

(defn enquet-by-id [id]
  (first (select enq4 (where {:id id}))))

;; アプロードを処理し、URL を返す。
;; 保存先は resources/public/{dir}
(defn do-upload [{tempfile :tempfile filename :filename} dir id]
  (let [dest (if id id filename)]
   (copy tempfile (file "resources" "public" dir dest))
   (file dir dest)
   )
)

;; FIXME: (empty? original) のとき例外を出さなくちゃ。
(defn create-enquet [params]
  (let [u (do-upload (:upload params) "o" nil)
        p (assoc (dissoc params :upload)
            :original u
            :timestamp (now))]
    (insert enq4 (values p))
     ))

;; FIXME: コードがダサイ。
(defn update-enquet [id params]
  (if (empty? (:filename (:upload params)))
    (let [p (assoc (dissoc params :upload)
             :timestamp (now))]
      (update enq4 (set-fields p) (where {:id id})))
    (let [p (assoc (dissoc params :upload)
             :upload (do-upload (:upload params) "u" id) ;; 2014-10-03
             :timestamp (now))]
      (update enq4 (set-fields p) (where {:id id})))
    ))

(defn delete-enquet [id]
  (update enq4
    (set-fields {:in_use 0 :timestamp (now)})
   (where {:id id})))

