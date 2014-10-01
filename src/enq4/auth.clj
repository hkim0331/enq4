(ns enq4.auth
  (:require
   [compojure.core :refer [defroutes GET POST]]
   [enq4.views :as views]
   [noir.session :as session]
   [hiccup.form :refer
    [form-to label text-field password-field submit-button]]))

(defn control [field name text]
  (list (label name text)
		(field name)
		[:br]))

(defn registration-page []
  (views/common
   "please register"
   (form-to [:post "/register"]
			(control text-field :id "name")
			(control password-field :pass "Password")
			(control password-field :pass1 "Retype Password")
			(submit-button "register"))))

(defn login-page []
  (views/common
   "please login"
   (form-to [:post "/login"]
			(control text-field :name "name")
			(control password-field :pass "Password")
			(submit-button "login"))))

; (defn auth? [name pass]
;   (let [author (enq4.models/author-by-name name)]
;     (if (= pass (:password author))
;       (:id author)
;       false)))

(defn auth? [name pass]
  (= name pass)
  )
