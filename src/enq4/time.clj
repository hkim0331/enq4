;; required 'lein clean' before compile.
(ns enq4.time
  (:refer-clojure :exclude [extend second])
  (:require
   [clojure.string :as string]
   [clj-time.core :as t]
   [clj-time.format :as f]
   [clj-time.local :as l]
   ))

;; used in ape project
;; (defn utc-to-localtime [utc]
;;   (let* [s  (string/split utc #"[\s\-:]")
;;          dt (apply t/date-time (map #(Integer. %) s))
;;          jst (t/to-time-zone dt (t/time-zone-for-offset +9))
;;          ]
;;         (f/unparse (f/formatter-local "yyyy/MM/dd kk:mm:ss") jst)))

(defn now []
  (f/unparse (f/formatter-local "yyyy/MM/dd kk:mm:ss")
             (l/local-now)))


