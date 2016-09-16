DROP TABLE IF EXISTS contacts;

CREATE TABLE contacts
(
  id bigint NOT NULL auto_increment,
  name VARCHAR,
  CONSTRAINT pk_id PRIMARY KEY (id)
)