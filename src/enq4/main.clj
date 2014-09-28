(ns enq4.main
  (:use enq4.handler
        [ring.adapter.jetty :only [run-jetty]]
        )
  (:gen-class)
  )

(defn -main [& [port]]
  (let [port (if port (Integer/parseInt port) 3000)]
    (run-jetty app {:port port})
    (println (str "server started at http://localhost:" port)))
)
