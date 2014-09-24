(ns enq4.models
  (:use korma.db korma.core))

(defdb enq4
  (sqlite3 {:db "resources/db/enq4.db"}))

(defentity enq4
  (entity-fields :id :name :subject :q1 :q2 :q3 :q4
                 :original :upload :timestamp))

(defn all-enquets []
  (select enq4))

(defn enquet-by-id [id]
  (first (select enq4 (where {:id id}))))

;; timestamp?
(defn create-enquet [params]
  (insert enq4 (values params)))

;; timestamp?
(defn update-enquet [id params]
  (update enq4 (set-fields params) (where {:id id})))
