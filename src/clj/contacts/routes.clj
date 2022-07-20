(ns contacts.routes
  (:require
   [schema.core :as s]
   [contacts.contacts :as c]))

(def ping-routes
  ["/ping" {:get (fn [req] {:status 200
                            :body {:ping "pong"}})}])

(defn dummy [req]
  {:status 200
   :body {:ping "pong"}})

(def contact-routes
  ["/contacts"
   ["/" {:get c/get-contacts
         :post {:parameters {:body {:first_name s/Str
                                    :last_name s/Str
                                    :email s/Str}}
                :handler c/create-contact}}]
   ["/:id" {:parameters {:path {:id s/Int}}
            :get c/get-contact-by-id
            :put {:parameters {:body {:first_name s/Str
                                      :last_name s/Str
                                      :email s/Str}}
                  :handler c/update-contact}
            :delete c/delete-contact}]])
