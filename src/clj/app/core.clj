(ns app.core
  (:require [cognitect.transit :as transit]
            [compojure.core :refer [defroutes GET POST]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.util.response :refer [resource-response]]
            [clojure.string :as str]
            [app.scramble :refer [scramble?]])
  (:import [java.io ByteArrayOutputStream])
  (:gen-class))


(def default-response
  {:headers {"Content-Type" "application/transit+json"}})

(defn handler [request]
  (let [reader (transit/reader (:body request) :json)
        data (transit/read reader)
        str1 (:str1 data)
        str2 (:str2 data)
        output (ByteArrayOutputStream. 1024)
        writer (transit/writer output :json)]
    (if (or (str/blank? str1) (str/blank? str2))
      (do
        (transit/write writer {:error "no parameters passed"})
        (merge default-response {:status 422 :body (.toString output)}))
      (do
        (transit/write writer {:result (scramble? str1 str2)})
        (merge default-response {:status 200 :body (.toString output)})))))

(defroutes app-routes
  (GET "/" [] (resource-response "public/index.html"))
  (POST "/scramble" [] handler))

(def app
  (-> app-routes
      (wrap-resource "public")))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") 3000))]
    (run-jetty app {:join false
                    :port port})))
