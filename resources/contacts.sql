-- :name create-contacts-table
-- :command :execute
-- :result :raw
-- :doc creates contacts table
CREATE TABLE contacts (
    id SERIAL PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    email TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);

-- :name get-contacts :? :*
SELECT * FROM contacts;

-- :name get-contact-by-id :? :*
SELECT * FROM contacts
WHERE id = :id;

-- :name insert-contact :insert :*
INSERT INTO contacts (first_name, last_name, email)
VALUES (:first_name, :last_name, :email)
RETURNING id;

-- :name update-contact-by-id :! :*
UPDATE contacts
SET first_name = :first_name,
    last_name = :last_name,
    email = :email
WHERE id = :id

-- :name delete-contact-by-id :! :*
DELETE FROM contacts
WHERE id = :id;
