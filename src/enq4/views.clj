(ns enq4.views
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.form :refer [form-to label text-field file-upload
                                 submit-button]]
            [hiccup.element :refer [link-to]]
            [hiccup.util :refer [escape-html url-encode]]
            [noir.response :refer [redirect]]
            [noir.io :refer [upload-file resource-path]]
            [enq4.time :refer [now]]
            [enq4.models :as models ]))

(defn common [& body]
  (html5
    [:head
     (include-css "/css/screen.css")
     [:title "Welcome to enq4"]]
    [:body
     [:div.container body]
     [:footer "programmed by hkimura."]]))

(defn enquets []
  (common
   [:h1 "アンケート"]
   [:table
    [:tr
     [:th "名前"]
     [:th "科目名"]
     [:th "q1"] [:th "q2"] [:th "q3"] [:th "q4"]
     [:th "旧シラバス"]
     [:th "更新済み"]
     [:th "更新日"]
     [:th "アクション"]]
    (for [e (models/avail-enquets)]
      [:tr
       [:td (:name e)]
       [:td (:subject e)]
       [:td (:q1 e)] [:td (:q2 e)] [:td (:q3 e)] [:td (:q4 e)]
       [:td (link-to (:original e) (:original e))]
       (if (:upload e)
         [:td (link-to (:upload e) (:upload e))]
         [:td])
       [:td (:timestamp e)]
       [:td (link-to (str "/enquet/" (:id e)) "編集") " | "
            (link-to {:onclick "return confirm('delete?')"} 
              (str "/delete/" (:id e)) "削除") 
        ]])
    ]
   [:p (link-to "/enquets-new" "追加")]
))

;; DRY, enquet-new と被る。Rails を参考にできないか?
;; PUT なのに POST?
(defn enquet-by-id [id]
  (let [d (models/enquet-by-id id)]
    (common
     [:h1 "編集"]
     (form-to {:enctype "multipart/form-data"}
              [:post (str "/enquet/" id)]

              (label "name" "氏名")
              (text-field {:value (:name d)} "name") [:br]

              (label "subject" "科目名")
              (text-field {:value (:subject d)} "subject") [:br]

              (label "q1" "Q1")
              (text-field {:value (:q1 d)} "q1") [:br]

              (label "q2" "Q2")
              (text-field {:value (:q2 d)} "q2") [:br]

              (label "q3" "Q3")
              (text-field {:value (:q3 d)} "q3") [:br]

              (label "q4" "Q4")
              (text-field {:value (:q4 d)} "q4") [:br]

              (label "upload" "シラバス更新")
              (file-upload "upload") [:br]

              (submit-button {:class "button"} "update")
              ))))


;; DRY, enquet-by-id と被る。Rails を参考にできないか?
(defn enquets-new []
    (common
     [:h1 "追加"]
     (form-to {:enctype "multipart/form-data"}
              [:post "/enquets"]

              (label "name" "氏名")
              (text-field "name") [:br]

              (label "subject" "科目名")
              (text-field "subject") [:br]

              (label "q1" "Q1")
              (text-field "q1") [:br]

              (label "q2" "Q2")
              (text-field "q2") [:br]

              (label "q3" "Q3")
              (text-field "q3") [:br]

              (label "q4" "Q4")
              (text-field "q4") [:br]

              (label "upload" "更新したシラバス")
              (file-upload "upload") [:br]

              (submit-button {:class "button"} "create")

              )))

;; create するときは upload も含めてすべてのフィールドが揃っているはず。
(defn make-enquet [params]
  (models/create-enquet params)
  (redirect "/enquets")
)

;; upload はデータがないときもある。
(defn update-enquet [id params]
  (models/update-enquet id params)
  (redirect "/enquets")
)

(defn delete [id]
  (models/delete-enquet id)
  (redirect "/enquets")
)
