ALTER TABLE order_details
ADD type text;

ALTER TABLE creditor
ADD ime_na_banka text;

ALTER TABLE customer
ADD CONSTRAINT customer_unique_transaction_number UNIQUE (transaction_number);