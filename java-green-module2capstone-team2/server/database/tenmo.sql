BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, transfer_type, transfer;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfer_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
    user_id int NOT NULL DEFAULT nextval('seq_user_id'),
    username varchar(50) NOT NULL,
    password_hash varchar(200) NOT NULL,
    CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
    CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
    account_id int NOT NULL DEFAULT nextval('seq_account_id'),
    user_id int NOT NULL,
    balance numeric(13, 2) NOT NULL,
    CONSTRAINT PK_account PRIMARY KEY (account_id),
    CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE TABLE transfer_type
(
    transfer_type_id integer NOT NULL,
    transfer_type_desc character varying(100),
    CONSTRAINT pk_transfer_type_id PRIMARY KEY (transfer_type_id)
);

CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

CREATE TABLE transfer
(
    transfer_id integer NOT NULL DEFAULT nextval('seq_transfer_id'),
    transfer_amount numeric(13,2) NOT NULL,
    sender_name character varying(100)  NOT NULL,
    receiver_name character varying(100)  NOT NULL,
    CONSTRAINT transfer_id PRIMARY KEY (transfer_id),
    CONSTRAINT fk_receiver_name FOREIGN KEY (receiver_name)
        REFERENCES public.tenmo_user (username),
    CONSTRAINT fk_sender_name FOREIGN KEY (sender_name)
        REFERENCES public.tenmo_user (username),
--    CONSTRAINT fk_transfer_type FOREIGN KEY (transfer_type_id)
--        REFERENCES public.transfer_type (transfer_type_id)
);

COMMIT;


