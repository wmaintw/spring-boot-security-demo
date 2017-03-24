CREATE TABLE users (
  id int not null auto_increment PRIMARY KEY,
  username varchar(32) not null,
  password varchar(128) not null,
  role varchar(128) not null
);

CREATE TABLE dealers (
  id int not null auto_increment PRIMARY KEY,
  name varchar(32) not null
);

CREATE TABLE stores (
  id int not null auto_increment PRIMARY KEY,
  dealer_id int not null,
  name varchar(32) not null,
  address varchar(128) not null
);

CREATE TABLE dealer_users (
  id int not null auto_increment PRIMARY KEY,
  user_id int not null,
  store_id int not null,
  name varchar(32) not null
);

CREATE TABLE orders (
  id int not null auto_increment PRIMARY KEY,
  content varchar(128) not null,
  owner_id int not null,
  store_id int not null,
  status varchar(32)
);

CREATE TABLE ratings (
  id int not null auto_increment PRIMARY KEY,
  star int not null,
  comments varchar(128),
  order_id int not null,
  user_id int not null
);

CREATE TABLE logged_in_users (
  token varchar(128) not null,
  user_id int not null
);
