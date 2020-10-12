(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [ajax.core :refer [POST]]))

(defonce str1 (r/atom ""))
(defonce str2 (r/atom ""))
(defonce result (r/atom nil))

(defn send-to-server [event]
  (.preventDefault event)
  (POST "/scramble"
        {:params {:str1 @str1
                  :str2 @str2}
         :handler #(reset! result %)
         :error-handler #(reset! result (:response %))}))

(defn set-value [target value]
  (reset! target value)
  (reset! result nil))

(defn input [id label value]
  [:div
   [:label
    {:for id}
    label]
   [:input {:id id
            :on-change #(set-value value (-> % .-target .-value))
            :type "text"
            :value @value}]])

(defn form []
  [:div
   [:form {:on-submit send-to-server}
    [input "str1" "Str1" str1]
    [input "str2" "Str2" str2]
    [:input {:type "submit"
             :value "Check"}]]
   (cond
     (:error @result) [:div {:style {:color "red"}} (:error @result)]
     (not (nil? (:result @result))) [:div "Result is: " (str (:result @result))]
     :else nil)])

(defn ^:dev/after-load start []
  (rdom/render [form] (js/document.getElementById "root")))

(defn ^:export init []
  (start))
