(ns app.scramble
  (:require [clojure.string :as str]))

(defn scramble? [str1 str2]
  (empty?
   (reduce
    #(str/replace-first %1 %2 "") str2 str1)))
