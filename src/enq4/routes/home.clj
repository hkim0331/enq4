(ns enq4.routes.home
  (:require [compojure.core :refer :all]
            [enq4.views :as layout]))

(defn home []
  (layout/common [:h1 "Hello World!"]))

(defroutes home-routes
  (GET "/" [] (home)))
