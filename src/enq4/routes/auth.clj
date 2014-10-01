(ns enq4.routes.auth
  (:require
   [compojure.core :refer [defroutes GET POST]]
   [enq4.views :as views]
   [hiccup.form :refer [form-to label text-field password-field submit-button]]
   [noir.response :refer [redirect]]
   [noir.session :as session]
))

(defroutes auth-routes
  ; (GET "/register" [] (views/registration-page))
  ; (POST "/register" [id pass pass1]     ; パラメータが三つということはわかる。
  ;       (do
  ;         (if (= pass pass1)
  ;           (redirect "/")
  ;           (views/registration-page))))

  (GET "/login" [] (views/login-page))

  (POST "/login" [name pass]
        (if (= name pass)
          (do (session/put! :user name)
              (redirect "/"))
          (redirect "/login")))

  (GET "/logout" []
    (views/common
     "logout"
      [:p "Are you sure?"]
      (form-to [:post "/logout"]
        (submit-button "logout"))))
  
  (POST "/logout" []
    (session/clear!)
    (redirect "/"))
  )
