-- Schema DDL for KickDrum Smart Home Application
-- This schema is compatible with H2 and typical relational DBs

CREATE TABLE users (
  user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  creation_time_stamp TIMESTAMP,
  updation_time_stamp TIMESTAMP,
  deleted_date TIMESTAMP,
  version BIGINT
);

CREATE TABLE house (
  house_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  admin_id BIGINT,
  house_name VARCHAR(255),
  house_address VARCHAR(255),
  creation_time_stamp TIMESTAMP,
  updation_time_stamp TIMESTAMP,
  deleted_date TIMESTAMP,
  version BIGINT,
  CONSTRAINT fk_house_admin FOREIGN KEY (admin_id) REFERENCES users(user_id)
);

CREATE TABLE room (
  room_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  house_id BIGINT,
  room_name VARCHAR(255),
  creation_time_stamp TIMESTAMP,
  updation_time_stamp TIMESTAMP,
  deleted_date TIMESTAMP,
  version BIGINT,
  CONSTRAINT fk_room_house FOREIGN KEY (house_id) REFERENCES house(house_id)
);

CREATE TABLE device_inventory (
  kickston_id VARCHAR(6) PRIMARY KEY,
  device_username VARCHAR(255) NOT NULL,
  device_password VARCHAR(255) NOT NULL,
  manufacture_date_time TIMESTAMP,
  manufacture_factory_place VARCHAR(255),
  updation_time_stamp TIMESTAMP,
  deleted_date TIMESTAMP,
  version BIGINT
);

CREATE TABLE device (
  device_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  kickston_id VARCHAR(6) NOT NULL UNIQUE,
  house_id BIGINT NOT NULL,
  room_id BIGINT,
  creation_time_stamp TIMESTAMP,
  updation_time_stamp TIMESTAMP,
  deleted_date TIMESTAMP,
  version BIGINT,
  CONSTRAINT fk_device_house FOREIGN KEY (house_id) REFERENCES house(house_id),
  CONSTRAINT fk_device_inventory FOREIGN KEY (kickston_id) REFERENCES device_inventory(kickston_id),
  CONSTRAINT fk_device_room FOREIGN KEY (room_id) REFERENCES room(room_id)
);

CREATE TABLE user_home_membership (
  membership_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  house_id BIGINT,
  user_id BIGINT,
  creation_time_stamp TIMESTAMP,
  updation_time_stamp TIMESTAMP,
  deleted_date TIMESTAMP,
  version BIGINT,
  CONSTRAINT fk_membership_house FOREIGN KEY (house_id) REFERENCES house(house_id),
  CONSTRAINT fk_membership_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Indexes for common lookups
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_device_house ON device(house_id);
CREATE INDEX idx_room_house ON room(house_id);
