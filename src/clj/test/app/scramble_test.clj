(ns test.app.scramble-test
  (:require [clojure.test :refer [deftest is testing]]
            [app.scramble :refer [scramble?]]))

(deftest core
  (testing "scramble?"
    (is (= true (scramble? "rekqodlw" "world")))
    (is (= true (scramble? "cedewaraaossoqqyt" "codewars")))
    (is (= true (scramble? "builder" "bud")))
    (is (= false (scramble? "katas" "steak")))
    (is (= false (scramble? "cat" "tab")))
    (is (= false (scramble? "dog" "dogg")))))

(comment (time (dotimes [_ 1e5] (scramble? "rekqodlw" "world"))))
