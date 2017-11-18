# --- !Ups

CREATE TABLE actor (
  actor_id smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (actor_id),
  INDEX idx_actor_last_name (last_name)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE category (
  category_id tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  name varchar(25) NOT NULL,
  created_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (category_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE country (
  country_id smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT,
  country varchar(50) NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (country_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE film_text (
  film_id smallint(6) NOT NULL,
  title varchar(255) NOT NULL,
  description text DEFAULT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (film_id),
  FULLTEXT INDEX idx_title_description (title, description)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE language (
  language_id tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  name char(20) NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (language_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE city (
  city_id smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT,
  city varchar(50) NOT NULL,
  country_id smallint(5) UNSIGNED NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (city_id),
  INDEX idx_fk_country_id (country_id),
  CONSTRAINT fk_city_country FOREIGN KEY (country_id)
  REFERENCES country (country_id) ON DELETE RESTRICT ON UPDATE CASCADE
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE film (
  film_id smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT,
  title varchar(255) NOT NULL,
  description text DEFAULT NULL,
  release_year year(4) DEFAULT NULL,
  language_id tinyint(3) UNSIGNED NOT NULL,
  original_language_id tinyint(3) UNSIGNED DEFAULT NULL,
  rental_duration tinyint(3) UNSIGNED NOT NULL DEFAULT 3,
  rental_rate decimal(4, 2) NOT NULL DEFAULT 4.99,
  length smallint(5) UNSIGNED DEFAULT NULL,
  replacement_cost decimal(5, 2) NOT NULL DEFAULT 19.99,
  rating enum ('G', 'PG', 'PG-13', 'R', 'NC-17') DEFAULT 'G',
  special_features set ('Trailers', 'Commentaries', 'Deleted Scenes', 'Behind the Scenes') DEFAULT NULL,
  created_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (film_id),
  INDEX idx_fk_language_id (language_id),
  INDEX idx_fk_original_language_id (original_language_id),
  INDEX idx_title (title),
  CONSTRAINT fk_film_language FOREIGN KEY (language_id)
  REFERENCES language (language_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_film_language_original FOREIGN KEY (original_language_id)
  REFERENCES language (language_id) ON DELETE RESTRICT ON UPDATE CASCADE
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE address (
  address_id smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT,
  address varchar(50) NOT NULL,
  address2 varchar(50) DEFAULT NULL,
  district varchar(20) NOT NULL,
  city_id smallint(5) UNSIGNED NOT NULL,
  postal_code varchar(10) DEFAULT NULL,
  phone varchar(20) NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (address_id),
  INDEX idx_fk_city_id (city_id),
  CONSTRAINT fk_address_city FOREIGN KEY (city_id)
  REFERENCES city (city_id) ON DELETE RESTRICT ON UPDATE CASCADE
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE film_actor (
  actor_id smallint(5) UNSIGNED NOT NULL,
  film_id smallint(5) UNSIGNED NOT NULL,
  created_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (actor_id, film_id),
  INDEX idx_fk_film_id (film_id),
  CONSTRAINT fk_film_actor_actor FOREIGN KEY (actor_id)
  REFERENCES actor (actor_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_film_actor_film FOREIGN KEY (film_id)
  REFERENCES film (film_id) ON DELETE RESTRICT ON UPDATE CASCADE
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE film_category (
  film_id smallint(5) UNSIGNED NOT NULL,
  category_id tinyint(3) UNSIGNED NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (film_id, category_id),
  CONSTRAINT fk_film_category_category FOREIGN KEY (category_id)
  REFERENCES category (category_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_film_category_film FOREIGN KEY (film_id)
  REFERENCES film (film_id) ON DELETE RESTRICT ON UPDATE CASCADE
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE customer (
  customer_id smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT,
  store_id tinyint(3) UNSIGNED NOT NULL,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  email varchar(50) DEFAULT NULL,
  address_id smallint(5) UNSIGNED NOT NULL,
  active tinyint(1) NOT NULL DEFAULT 1,
  created_at datetime NOT NULL,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (customer_id),
  INDEX idx_fk_address_id (address_id),
  INDEX idx_fk_store_id (store_id),
  INDEX idx_last_name (last_name)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE inventory (
  inventory_id mediumint(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  film_id smallint(5) UNSIGNED NOT NULL,
  store_id tinyint(3) UNSIGNED NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (inventory_id),
  INDEX idx_fk_film_id (film_id),
  INDEX idx_store_id_film_id (store_id, film_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE payment (
  payment_id smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT,
  customer_id smallint(5) UNSIGNED NOT NULL,
  staff_id tinyint(3) UNSIGNED NOT NULL,
  rental_id int(11) DEFAULT NULL,
  amount decimal(5, 2) NOT NULL,
  payment_date datetime NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (payment_id),
  INDEX idx_fk_customer_id (customer_id),
  INDEX idx_fk_staff_id (staff_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE rental (
  rental_id int(11) NOT NULL AUTO_INCREMENT,
  rental_date datetime NOT NULL,
  inventory_id mediumint(8) UNSIGNED NOT NULL,
  customer_id smallint(5) UNSIGNED NOT NULL,
  return_date datetime DEFAULT NULL,
  staff_id tinyint(3) UNSIGNED NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (rental_id),
  INDEX idx_fk_customer_id (customer_id),
  INDEX idx_fk_inventory_id (inventory_id),
  INDEX idx_fk_staff_id (staff_id),
  UNIQUE INDEX rental_date (rental_date, inventory_id, customer_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE staff (
  staff_id tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  address_id smallint(5) UNSIGNED NOT NULL,
  picture blob DEFAULT NULL,
  email varchar(50) DEFAULT NULL,
  store_id tinyint(3) UNSIGNED NOT NULL,
  active tinyint(1) NOT NULL DEFAULT 1,
  username varchar(16) NOT NULL,
  password varchar(40) binary CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (staff_id),
  INDEX idx_fk_address_id (address_id),
  INDEX idx_fk_store_id (store_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE store (
  store_id tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  manager_staff_id tinyint(3) UNSIGNED NOT NULL,
  address_id smallint(5) UNSIGNED NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (store_id),
  INDEX idx_fk_address_id (address_id),
  UNIQUE INDEX idx_unique_manager (manager_staff_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;

# --- !Downs
