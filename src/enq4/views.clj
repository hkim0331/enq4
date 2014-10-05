(ns enq4.views
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.form :refer
              [form-to label text-field file-upload password-field
                                 submit-button]]
            [hiccup.element :refer [link-to]]
            [hiccup.util :refer [escape-html url-encode]]
            [noir.response :refer [redirect]]
            [noir.io :refer [upload-file resource-path]]
            [noir.session :as session]
            [enq4.time :refer [now]]
            [enq4.models :as models ]))

(defn common [& body]
  (html5
    [:head
     (include-css "/css/bootstrap.min.css")
     (include-css "/css/screen.css")
     [:meta {:charset "utf-8"}]
     [:title "Welcome to enq4"]]
    [:body
     [:div {:class "navbar navbar-inverse"}
     [:div {:class "navbar-inner"}
      (link-to {:class :brand} "/" "ENQ4 | ")
      (if (session/get :user)
        (link-to "/logout" "LOGOUT")
        (link-to "/login" "PLEASE LOGIN"))
      [:form {:class "navbar-form pull-right"}
       [:input {:type :text :class :search-query :placeholder :Search}]]]]
     [:div.container body]
     [:footer "programmed by hkimura with clojure."]]))

(defn enquets []
  (common
   [:h1 "H27大学院時間割等修正・確認"]
   (if (session/get :user)
    [:ol
      [:li "旧シラバスの欄からシラバスをダウンロードします。"]
      [:li "ダウンロードした旧シラバスから担当授業分を抜き出し、新シラバスを作成、PCに保存して下さい。"]
      [:li "編集ボタンを押し、q1, q2, q3, q4を適切に書き換え、
        先ほど保存した新シラバスを選び、update ボタンを押します。"]
      [:li "作業が完了したら左上の LOGOUT からログアウトして下さい。"]]
    [:p "左上の PLEASE LOGIN をクリックし、ログイン後に作業をお願いします。"])
   [:table {:class "tbl"}
    [:tr
     [:th {:class "name"} "名前"]
     [:th {:class "subject"} "科目名"]
     [:th {:class "q"} "q1"]
     [:th {:class "q"} "q2"]
     [:th {:class "q"} "q3"]
     [:th {:class "q"} "q4"]
     [:th "旧シラバス<br>(全員分)"]
     [:th "修正シラバス<br>(当該科目のみ)"]
     [:th "備考"]
     [:th "更新日"]
     [:th "アクション"]]
    (for [[n e] (map-indexed vector (models/avail-enquets))]
      [:tr {:class (if (even? n) :even :odd)}
       [:td (:name e)]
       [:td (:subject e)]
       [:td (:q1 e)] [:td (:q2 e)] [:td (:q3 e)] [:td (:q4 e)]
       [:td (link-to (:original e) (:original e))]
       (if (:upload e)
         [:td (link-to (:upload e) (:upload e))]
         [:td])
       [:td (:note e)]
       [:td (:timestamp e)]
       (if (session/get :user)
         [:td (link-to (str "/enquet/" (:id e)) "編集") 
         ; " | "
         ;    (link-to {:onclick "return confirm('delete?')"}
         ;      (str "/delete/" (:id e)) "削除")
          ]
         [:td])
        ])
    ]
    (if (session/get :user)
      [:p (link-to "/enquets-new" "追加")]
    )
))

;; DRY, enquet-new と被る。Rails を参考にできないか?
;; PUT なのに POST?
(defn enquet-by-id [id]
  (let [d (models/enquet-by-id id)]
    (common
     [:h1 "編集"]
     (form-to {:enctype "multipart/form-data" :character-encoding "utf-8"}
              [:post (str "/enquet/" id)]
        [:table {:class "edit"}      
          [:tr [:th "氏名"]
               [:td (text-field {:value (:name d)} "name")]]
          [:tr [:th "科目名"]
              [:td (text-field {:value (:subject d)} "subject")]]
          [:tr [:th "Q1"]
              [:td (text-field {:value (:q1 d)} "q1")]]
          [:tr [:th "Q2"]
              [:td (text-field {:value (:q2 d)} "q2")]]
          [:tr [:th "Q3"]
              [:td (text-field {:value (:q3 d)} "q3")]]
          [:tr [:th "Q4"]
              [:td (text-field {:value (:q4 d)} "q4")]]
          [:tr [:th "シラバス"]
              [:td (file-upload "upload")]]
          [:tr [:th "備考"]
              [:td (text-field {:value (:note d)} "note")]]
          ]
          [:p (submit-button {:class "button"} "update")]))))



;; DRY, enquet-by-id と被る。Rails を参考にできないか?
(defn enquets-new []
    (common
     [:h1 "追加"]
     (form-to {:enctype "multipart/form-data" :character-encoding "utf-8"}
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

              (label "note" "備考")
              (text-field "note") [:br]

              (submit-button {:class "button"} "create")

              )))

;; 以下の関数は本来 models にあるべきか?
;; create するときは upload も含めてすべてのフィールドが揃っているはず。
(defn make-enquet [params]
  (models/create-enquet params)
  (redirect "/enquets")
  ; (common
  ;  [:h1 "params test"]
  ;  [:p (str "params: " params)])
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
;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; login
(defn- control [field name text]
  (list (label name text) (field name) [:br]))

(defn login-page []
  (common
    [:p "Please login."]
    (form-to [:post "/login"]
      [:table {:class "login"}
        [:tr [:th "name"] [:td (text-field :name)]]
        [:tr [:th "password"] [:td (password-field :pass)]]]
        [:br]
        [:p (submit-button "login")]
      )))

