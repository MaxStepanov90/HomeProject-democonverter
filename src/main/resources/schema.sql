DROP TABLE IF EXISTS public.usr;

CREATE TABLE public.usr (
                            id int8 NOT NULL,
                            login varchar(255) NULL,
                            "password" varchar(255) NULL,
                            CONSTRAINT usr_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS public.currency;
CREATE TABLE public.currency (
                                 "identity" varchar(255) NOT NULL,
                                 char_code varchar(255) NULL,
                                 "name" varchar(255) NULL,
                                 nominal int4 NOT NULL,
                                 value float8 NOT NULL,
                                 CONSTRAINT currency_pkey PRIMARY KEY (identity)
);

DROP TABLE IF EXISTS public.currency_exchange;
CREATE TABLE public.currency_exchange (
                                          id int8 NOT NULL,
                                          creation_date date NULL,
                                          source_char_code varchar(255) NULL,
                                          source_count float8 NOT NULL,
                                          source_name varchar(255) NULL,
                                          target_char_code varchar(255) NULL,
                                          target_count float8 NOT NULL,
                                          target_name varchar(255) NULL,
                                          user_id INTEGER REFERENCES public.usr (id),
                                          CONSTRAINT currency_exchange_pkey PRIMARY KEY (id)

);