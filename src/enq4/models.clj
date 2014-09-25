(ns enq4.models
  (:use korma.db korma.core)
  (:require [enq4.time :refer [now]]))

(defdb enq4
  (sqlite3 {:db "resources/db/enq4.db"}))

(defentity enq4
  (entity-fields :id :name :subject :q1 :q2 :q3 :q4
                 :original :upload :timestamp))

(defn all-enquets []
  (select enq4))

(defn enquet-by-id [id]
  (first (select enq4 (where {:id id}))))

;; FIXME
(defn do-upload [{tmpfile :tempfile filename :filename}]
  filename
)

;; timestamp を足し、upload を処理する。
;; FIXME: (empty? original) のとき。
(defn create-enquet [params]
  (let [u (do-upload (:upload params))
        p (assoc (dissoc params :upload)
            :original u
            :timestamp (now))]
    (insert enq4 (values p))
     ))

(defn update-enquet [id params]
  (update enq4
          (set-fields (assoc params :timestamp (now)))
          (where {:id id})))
