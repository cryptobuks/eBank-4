
DROP TABLE public.customer_role;

ALTER TABLE public.customer
ADD role_id integer;

ALTER TABLE public.customer
ADD CONSTRAINT fk_role_id
FOREIGN KEY (role_id) REFERENCES public.role (id);
