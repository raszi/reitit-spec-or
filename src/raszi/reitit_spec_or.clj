(ns raszi.reitit-spec-or
  (:require
   [clojure.spec.alpha :as s]
   [reitit.ring :as ring]
   [reitit.ring.coercion :as ring.coercion]
   [reitit.coercion.spec]))

(s/def ::a string?)
(s/def ::A (s/keys :req-un [::a]))

(s/def ::b vector?)
(s/def ::B (s/keys :req-un [::b]))

(def app
  (ring/ring-handler
   (ring/router
    [["/test" {:post {:coercion   reitit.coercion.spec/coercion
                      :parameters {:body (s/or :foo ::A :bar ::B)}
                      :responses  {200 {}}
                      :handler    (constantly true)}}]]
    {:data {:middleware [reitit.ring.coercion/coerce-exceptions-middleware
                         reitit.ring.coercion/coerce-request-middleware
                         reitit.ring.coercion/coerce-response-middleware]}})))

(comment
  (app {:request-method :post
        :uri            "/test"
        :body-params    {:a ""}})

  (app {:request-method :post
        :uri            "/test"
        :body-params    {:b []}}))
