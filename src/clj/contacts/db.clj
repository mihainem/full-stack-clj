(ns contacts.db
  (:require [hugsql.core :as hugsql]))

(def config
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "//localhost:5432/clj_contacts"
   :user "postgres"
   :password "postgres"})

(hugsql/def-db-fns "contacts.sql")

(comment
  (create-contacts-table config)
  (get-contacts config)
  (get-contact-by-id config {:id 1})
  (delete-contact-by-id config {:id 1})
  (insert-contact config {:first_name "John" :last_name "Doe" :email "john@doe.com"})
  (update-contact-by-id config {:id 1 :email "johnny@cagez.com"}))
