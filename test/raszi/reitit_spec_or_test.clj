(ns raszi.reitit-spec-or-test
  (:require
   [clojure.test :refer [deftest is]]
   [raszi.reitit-spec-or :as subject]))

(deftest app
  (is (true? (subject/app {:request-method :post
                           :uri            "/test"
                           :body-params    {:a ""}})))

  (is (true? (subject/app {:request-method :post
                           :uri            "/test"
                           :body-params    {:b []}}))))
