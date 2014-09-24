(ns enq4.routes.app
  (:require [compojure.core :refer :all]
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
        (views/make-enquet params))

  ;; FIXME, was PUT.
  (POST "/enquet/:id" [id & params]
       (views/update-enquet id params))

)
