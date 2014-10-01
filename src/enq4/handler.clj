(ns enq4.handler
  (:require
   [compojure.core :refer [defroutes routes]]
   [ring.middleware.resource :refer [wrap-resource]]
   [ring.middleware.file-info :refer [wrap-file-info]]
   [ring.middleware.keyword-params :refer [wrap-keyword-params]]
   [ring.middleware.params :refer [wrap-params]]
   [hiccup.middleware :refer [wrap-base-url]]
   [compojure.handler :as handler]
   [compojure.route :as route]
   [enq4.routes.app :refer [app-routes]]
;;   [enq4.routes.home :refer [home-routes]]
   ))

(defn init []
  (println "enq4 is starting"))

(defn destroy []
  (println "enq4 is shutting down"))

(def app
    (-> (routes app-routes)
      (handler/site)
      (wrap-resource "public")          ;
;      (wrap-keyword-params)
;      (wrap-params)
;      (wrap-base-url)
      ))
