(ns contacts.contacts
  (:require [contacts.db :as db]))

(defn get-contacts
  [_]
  {:status 200
   :body (db/get-contacts db/config)})

(defn create-contact
  [{:keys [parameters]}]
  (let [data (:body parameters)
        created-id (db/insert-contact db/config data)]
    {:status 201
     :body (db/get-contact-by-id db/config created-id)}))

(defn get-contact-by-id
  [{:keys [parameters]}]
  (let [id (:path parameters)]
    {:status 200
     :body (db/get-contact-by-id db/config id)}))


(defn update-contact
  [{:keys [parameters]}]
  (let [id (get-in parameters [:path :id])
        body (:body parameters)
        data (assoc body :id id)
        updated-count (first (db/update-contact-by-id db/config data))]
    (if (= updated-count 1)
      {:status 200
       :body {:updated true
              :contact (db/get-contact-by-id db/config {:id id})}}
      {:status 404
       :body {:error (str updated-count "Unable to update contact")}})))


(defn delete-contact
  [{:keys [parameters]}]
  (let [id (:path parameters)
        before-deleted (db/get-contact-by-id db/config id)
        deleted-count (first (db/delete-contact-by-id db/config id))]
    #_(db/delete-contact-by-id db/config id)
    (if (= deleted-count 1)
      {:status 200
       :body {:deleted true
              :contact before-deleted}}
      {:status 404
       :body {:error (str deleted-count "Unable to delete contact")}})))


