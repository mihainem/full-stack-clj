(ns contacts.core
  (:require [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]
            [reitit.ring.middleware.exception :as mx]
            [reitit.ring.middleware.parameters :refer [parameters-middleware]]
            [reitit.ring.middleware.muuntaja :as mm]
            [reitit.ring.coercion :as coercion]
            [reitit.coercion.schema]
            [schema.core :as s]
            [muuntaja.core :as m]
            [contacts.routes :as routes]))



(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(def app
  (ring/ring-handler
   (ring/router
    [["/api"
      routes/ping-routes
      routes/contact-routes]]
    {:data {:coercion reitit.coercion.schema/coercion
            :muuntaja m/instance
            :middleware [parameters-middleware
                         mm/format-negotiate-middleware
                         mm/format-response-middleware
                         mx/exception-middleware
                         mm/format-request-middleware
                         coercion/coerce-exceptions-middleware
                         coercion/coerce-request-middleware
                         coercion/coerce-response-middleware]}})
   (ring/routes
    (ring/redirect-trailing-slash-handler)
    (ring/create-default-handler
     {:not-found (constantly {:status 404
                              :body "Route not found"})}))))

(defn -main []
  (println "Starting server on port 4000")
  (reset! server (run-server app {:port 4000 :join? false})))

(defn restart-server []
  (stop-server)
  (-main))


(comment
  (restart-server)
  @server
  (app {:request-method :get
        :uri "/api/contacts"}))
