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

ALTER TABLE customer
ADD CONSTRAINT fk_customer_address FOREIGN KEY (address_id)
REFERENCES address (address_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE customer
ADD CONSTRAINT fk_customer_store FOREIGN KEY (store_id)
REFERENCES store (store_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE inventory
ADD CONSTRAINT fk_inventory_film FOREIGN KEY (film_id)
REFERENCES film (film_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE inventory
ADD CONSTRAINT fk_inventory_store FOREIGN KEY (store_id)
REFERENCES store (store_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE payment
ADD CONSTRAINT fk_payment_customer FOREIGN KEY (customer_id)
REFERENCES customer (customer_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE payment
ADD CONSTRAINT fk_payment_rental FOREIGN KEY (rental_id)
REFERENCES rental (rental_id) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE payment
ADD CONSTRAINT fk_payment_staff FOREIGN KEY (staff_id)
REFERENCES staff (staff_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE rental
ADD CONSTRAINT fk_rental_customer FOREIGN KEY (customer_id)
REFERENCES customer (customer_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE rental
ADD CONSTRAINT fk_rental_inventory FOREIGN KEY (inventory_id)
REFERENCES inventory (inventory_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE rental
ADD CONSTRAINT fk_rental_staff FOREIGN KEY (staff_id)
REFERENCES staff (staff_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE staff
ADD CONSTRAINT fk_staff_address FOREIGN KEY (address_id)
REFERENCES address (address_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE staff
ADD CONSTRAINT fk_staff_store FOREIGN KEY (store_id)
REFERENCES store (store_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE store
ADD CONSTRAINT fk_store_address FOREIGN KEY (address_id)
REFERENCES address (address_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE store
ADD CONSTRAINT fk_store_staff FOREIGN KEY (manager_staff_id)
REFERENCES staff (staff_id) ON DELETE RESTRICT ON UPDATE CASCADE;

CREATE PROCEDURE film_not_in_stock (IN p_film_id int, IN p_store_id int, OUT p_film_count int)
READS SQL DATA
BEGIN
  SELECT
    inventory_id
  FROM inventory
  WHERE film_id = p_film_id
  AND store_id = p_store_id
  AND NOT inventory_in_stock(inventory_id);;
  SELECT
    FOUND_ROWS() INTO p_film_count;;
END;

CREATE FUNCTION get_customer_balance (p_customer_id int, p_effective_date datetime)
RETURNS decimal(5, 2)
DETERMINISTIC
READS SQL DATA
BEGIN

  DECLARE v_rentfees decimal(5, 2);;
  DECLARE v_overfees integer;;
  DECLARE v_payments decimal(5, 2);;

  SELECT
    ifnull(SUM(film.rental_rate), 0) INTO v_rentfees
  FROM film,
       inventory,
       rental
  WHERE film.film_id = inventory.film_id
  AND inventory.inventory_id = rental.inventory_id
  AND rental.rental_date <= p_effective_date
  AND rental.customer_id = p_customer_id;;

  SELECT
    ifnull(SUM(IF((to_days(rental.return_date) - to_days(rental.rental_date)) > film.rental_duration,
    ((to_days(rental.return_date) - to_days(rental.rental_date)) - film.rental_duration), 0)), 0) INTO v_overfees
  FROM rental,
       inventory,
       film
  WHERE film.film_id = inventory.film_id
  AND inventory.inventory_id = rental.inventory_id
  AND rental.rental_date <= p_effective_date
  AND rental.customer_id = p_customer_id;;


  SELECT
    ifnull(SUM(payment.amount), 0) INTO v_payments
  FROM payment

  WHERE payment.payment_date <= p_effective_date
  AND payment.customer_id = p_customer_id;;

  RETURN v_rentfees + v_overfees - v_payments;;
END;

CREATE FUNCTION inventory_held_by_customer (p_inventory_id int)
RETURNS int(11)
READS SQL DATA
BEGIN
  DECLARE v_customer_id int;;
  DECLARE EXIT HANDLER FOR NOT FOUND RETURN NULL;;

  SELECT
    customer_id INTO v_customer_id
  FROM rental
  WHERE return_date IS NULL
  AND inventory_id = p_inventory_id;;

  RETURN v_customer_id;;
END;

CREATE FUNCTION inventory_in_stock (p_inventory_id int)
RETURNS tinyint(1)
READS SQL DATA
BEGIN
  DECLARE v_rentals int;;
  DECLARE v_out int;;

  SELECT
    COUNT(*) INTO v_rentals
  FROM rental
  WHERE inventory_id = p_inventory_id;;

  IF v_rentals = 0 THEN
    RETURN TRUE;;
  END IF;;

  SELECT
    COUNT(rental_id) INTO v_out
  FROM inventory
    LEFT JOIN rental USING (inventory_id)
  WHERE inventory.inventory_id = p_inventory_id
  AND rental.return_date IS NULL;;

  IF v_out > 0 THEN
    RETURN FALSE;;
  ELSE
    RETURN TRUE;;
  END IF;;
END;

CREATE OR REPLACE
VIEW actor_info
AS
SELECT
  `a`.`actor_id` AS `actor_id`,
  `a`.`first_name` AS `first_name`,
  `a`.`last_name` AS `last_name`,
  GROUP_CONCAT(DISTINCT concat(`c`.`name`, ': ', (SELECT
      GROUP_CONCAT(`f`.`title` ORDER BY `f`.`title` ASC SEPARATOR ', ')
    FROM ((`film` `f`
      JOIN `film_category` `fc`
        ON ((`f`.`film_id` = `fc`.`film_id`)))
      JOIN `film_actor` `fa`
        ON ((`f`.`film_id` = `fa`.`film_id`)))
    WHERE ((`fc`.`category_id` = `c`.`category_id`) AND (`fa`.`actor_id` = `a`.`actor_id`)))) ORDER BY `c`.`name` ASC SEPARATOR ';; ') AS `film_info`
FROM (((`actor` `a`
  LEFT JOIN `film_actor` `fa`
    ON ((`a`.`actor_id` = `fa`.`actor_id`)))
  LEFT JOIN `film_category` `fc`
    ON ((`fa`.`film_id` = `fc`.`film_id`)))
  LEFT JOIN `category` `c`
    ON ((`fc`.`category_id` = `c`.`category_id`)))
GROUP BY `a`.`actor_id`,
         `a`.`first_name`,
         `a`.`last_name`;;
;

CREATE OR REPLACE
VIEW customer_list
AS
SELECT
  `cu`.`customer_id` AS `id`,
  concat(`cu`.`first_name`, _utf8 ' ', `cu`.`last_name`) AS `name`,
  `a`.`address` AS `address`,
  `a`.`postal_code` AS `zip code`,
  `a`.`phone` AS `phone`,
  `city`.`city` AS `city`,
  `country`.`country` AS `country`,
  IF(`cu`.`active`, _utf8 'active', _utf8 '') AS `notes`,
  `cu`.`store_id` AS `sid`
FROM (((`customer` `cu`
  JOIN `address` `a`
    ON ((`cu`.`address_id` = `a`.`address_id`)))
  JOIN `city`
    ON ((`a`.`city_id` = `city`.`city_id`)))
  JOIN `country`
    ON ((`city`.`country_id` = `country`.`country_id`)));;	
;

CREATE OR REPLACE
VIEW film_list
AS
SELECT
  `film`.`film_id` AS `fid`,
  `film`.`title` AS `title`,
  `film`.`description` AS `description`,
  `category`.`name` AS `category`,
  `film`.`rental_rate` AS `price`,
  `film`.`length` AS `length`,
  `film`.`rating` AS `rating`,
  GROUP_CONCAT(concat(`actor`.`first_name`, _utf8 ' ', `actor`.`last_name`) SEPARATOR ', ') AS `actors`
FROM ((((`category`
  LEFT JOIN `film_category`
    ON ((`category`.`category_id` = `film_category`.`category_id`)))
  LEFT JOIN `film`
    ON ((`film_category`.`film_id` = `film`.`film_id`)))
  JOIN `film_actor`
    ON ((`film`.`film_id` = `film_actor`.`film_id`)))
  JOIN `actor`
    ON ((`film_actor`.`actor_id` = `actor`.`actor_id`)))
GROUP BY `film`.`film_id`,
         `category`.`name`;;
;

CREATE OR REPLACE
VIEW nicer_but_slower_film_list
AS
SELECT
  `film`.`film_id` AS `fid`,
  `film`.`title` AS `title`,
  `film`.`description` AS `description`,
  `category`.`name` AS `category`,
  `film`.`rental_rate` AS `price`,
  `film`.`length` AS `length`,
  `film`.`rating` AS `rating`,
  GROUP_CONCAT(concat(concat(ucase(SUBSTR(`actor`.`first_name`, 1, 1)), lcase(SUBSTR(`actor`.`first_name`, 2, length(`actor`.`first_name`))), _utf8 ' ', concat(ucase(SUBSTR(`actor`.`last_name`, 1, 1)), lcase(SUBSTR(`actor`.`last_name`, 2, length(`actor`.`last_name`)))))) SEPARATOR ', ') AS `actors`
FROM ((((`category`
  LEFT JOIN `film_category`
    ON ((`category`.`category_id` = `film_category`.`category_id`)))
  LEFT JOIN `film`
    ON ((`film_category`.`film_id` = `film`.`film_id`)))
  JOIN `film_actor`
    ON ((`film`.`film_id` = `film_actor`.`film_id`)))
  JOIN `actor`
    ON ((`film_actor`.`actor_id` = `actor`.`actor_id`)))
GROUP BY `film`.`film_id`,
         `category`.`name`;;
;

CREATE OR REPLACE
VIEW sales_by_film_category
AS
SELECT
  `c`.`name` AS `category`,
  SUM(`p`.`amount`) AS `total_sales`
FROM (((((`payment` `p`
  JOIN `rental` `r`
    ON ((`p`.`rental_id` = `r`.`rental_id`)))
  JOIN `inventory` `i`
    ON ((`r`.`inventory_id` = `i`.`inventory_id`)))
  JOIN `film` `f`
    ON ((`i`.`film_id` = `f`.`film_id`)))
  JOIN `film_category` `fc`
    ON ((`f`.`film_id` = `fc`.`film_id`)))
  JOIN `category` `c`
    ON ((`fc`.`category_id` = `c`.`category_id`)))
GROUP BY `c`.`name`
ORDER BY SUM(`p`.`amount`) DESC;;
;

CREATE OR REPLACE
VIEW sales_by_store
AS
SELECT
  concat(`c`.`city`, _utf8 ',', `cy`.`country`) AS `store`,
  concat(`m`.`first_name`, _utf8 ' ', `m`.`last_name`) AS `manager`,
  SUM(`p`.`amount`) AS `total_sales`
FROM (((((((`payment` `p`
  JOIN `rental` `r`
    ON ((`p`.`rental_id` = `r`.`rental_id`)))
  JOIN `inventory` `i`
    ON ((`r`.`inventory_id` = `i`.`inventory_id`)))
  JOIN `store` `s`
    ON ((`i`.`store_id` = `s`.`store_id`)))
  JOIN `address` `a`
    ON ((`s`.`address_id` = `a`.`address_id`)))
  JOIN `city` `c`
    ON ((`a`.`city_id` = `c`.`city_id`)))
  JOIN `country` `cy`
    ON ((`c`.`country_id` = `cy`.`country_id`)))
  JOIN `staff` `m`
    ON ((`s`.`manager_staff_id` = `m`.`staff_id`)))
GROUP BY `s`.`store_id`
ORDER BY `cy`.`country`, `c`.`city`;;
;

CREATE OR REPLACE
VIEW staff_list
AS
SELECT
  `s`.`staff_id` AS `id`,
  concat(`s`.`first_name`, _utf8 ' ', `s`.`last_name`) AS `name`,
  `a`.`address` AS `address`,
  `a`.`postal_code` AS `zip code`,
  `a`.`phone` AS `phone`,
  `city`.`city` AS `city`,
  `country`.`country` AS `country`,
  `s`.`store_id` AS `sid`
FROM (((`staff` `s`
  JOIN `address` `a`
    ON ((`s`.`address_id` = `a`.`address_id`)))
  JOIN `city`
    ON ((`a`.`city_id` = `city`.`city_id`)))
  JOIN `country`
    ON ((`city`.`country_id` = `country`.`country_id`)));;
;

CREATE TRIGGER customer_create_date
BEFORE INSERT
ON customer
FOR EACH ROW
  SET new.created_at = now()
;

CREATE TRIGGER del_film
AFTER DELETE
ON film
FOR EACH ROW
BEGIN
  DELETE
    FROM film_text
  WHERE film_id = old.film_id;;
END
;

CREATE TRIGGER ins_film
AFTER INSERT
ON film
FOR EACH ROW
BEGIN
  INSERT INTO film_text (film_id, title, description)
    VALUES (new.film_id, new.title, new.description);;
END
;

CREATE TRIGGER upd_film
AFTER UPDATE
ON film
FOR EACH ROW
BEGIN
  IF (old.title != new.title) OR (old.description != new.description) OR (old.film_id != new.film_id)
    THEN
    UPDATE film_text
    SET title = new.title,
        description = new.description,
        film_id = new.film_id
    WHERE film_id = old.film_id;;
  END IF;;
END
;

CREATE TRIGGER payment_date
BEFORE INSERT
ON payment
FOR EACH ROW
  SET new.created_at = now()
;

CREATE TRIGGER rental_date
BEFORE INSERT
ON rental
FOR EACH ROW
  SET new.created_at = now()
;


# --- !Downs

drop table if exists actor;

drop table if exists category;

drop table if exists country;

drop table if exists film_text;

drop table if exists language;

drop table if exists city;

drop table if exists film;

drop table if exists film_actor;

drop table if exists film_category;

drop table if exists customer;

drop table if exists inventory;

drop table if exists payment;

drop table if exists rental;

drop table if exists staff;

drop table if exists store;

# --- !Downs
