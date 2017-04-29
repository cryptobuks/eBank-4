ALTER TABLE "order"
RENAME TO order_details;

ALTER TABLE order_details
ADD description text;