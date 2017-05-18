ALTER TABLE order_details
  ALTER date DROP DEFAULT, 
  ALTER date type timestamp USING date::timestamp;