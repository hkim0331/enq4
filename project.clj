(defproject enq4 "0.5"
  :description "respond to senba's request"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [korma "0.4.0"]
                 [lib-noir "0.8.9"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [clj-time "0.8.0"]
                 [ring-server "0.3.1"]]
  :plugins [[lein-ring "0.8.11"]]
  :auto-clean false
  :ring {:handler enq4.handler/app
         :init enq4.handler/init
         :destroy enq4.handler/destroy}
  :main enq4.main
  :aot :all
  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.2.1"]]}})
