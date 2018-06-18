CREATE TABLE account (
  account_id int NOT NULL,
  user_name varchar(45) NOT NULL,
  password varchar(80) NOT NULL,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  email varchar(45),
  cellphone_no varchar(45),
  status int NOT NULL,
  created_time datetime NOT NULL,
  last_updated datetime NOT NULL,
  PRIMARY KEY (account_id)
);

CREATE UNIQUE INDEX user_name_UNIQUE ON account (user_name);
CREATE UNIQUE INDEX email_UNIQUE ON account (email);
CREATE UNIQUE INDEX cellphone_no_UNIQUE ON account (cellphone_no);

CREATE TABLE account_role (
  account_id int NOT NULL,
  role_id int NOT NULL,
  PRIMARY KEY (account_id,role_id)
);

CREATE TABLE account_status (
  status_id int NOT NULL,
  code varchar(45) NOT NULL,
  description varchar(45),
  PRIMARY KEY (status_id)
);

CREATE UNIQUE INDEX account_status_UNIQUE ON account_status (code);

CREATE TABLE authority (
  authority_id int NOT NULL,
  name varchar(45) NOT NULL,
  description varchar(45),
  PRIMARY KEY (authority_id)
);

CREATE TABLE role (
  role_id int NOT NULL,
  name varchar(45) NOT NULL,
  description varchar(45),
  PRIMARY KEY (role_id)
);

CREATE UNIQUE INDEX role_UNIQUE ON role (name);

CREATE TABLE role_authority (
  role_id int NOT NULL,
  authority_id int NOT NULL,
  PRIMARY KEY (role_id,authority_id)
);