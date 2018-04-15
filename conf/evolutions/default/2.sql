# --- !Ups

CREATE TABLE images (
  id                        	varchar(255),
  images	                  	text,
  CONSTRAINT images_pkey PRIMARY KEY (id)
);

ALTER TABLE actor ADD COLUMN images TEXT;

# --- !Downs

DROP TABLE images;

ALTER TABLE actor DROP images;