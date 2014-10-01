(ns enq4.routes.app
  (:require [compojure.core :refer :all]
            [noir.session :as session]
            [noir.response :refer [redirect]]
            [enq4.views :as views]))

(defn hello []
  (views/common [:h1 "Hello App!"]))

(defroutes app-routes
  (GET "/" []
       (views/enquets))

  (GET "/enquets" []
       (views/enquets))

  (GET "/enquet/:id" [id]
       (views/enquet-by-id id))

  (GET "/enquets-new" []
       (views/enquets-new))

  (POST "/enquets" [& params]
    (if (session/get :user)
      (views/make-enquet params)
      (redirect "/login")))

  ;; FIXME, was PUT.
  (POST "/enquet/:id" [id & params]
;;    (println (str "session:" (session/get :user)))
    (if (session/get :user)
     (views/update-enquet id params)
     (redirect "/login")))

  (GET "/delete/:id" [id]
    (if (session/get :user)
      (views/delete id)
      (redirect "/login")))
)
