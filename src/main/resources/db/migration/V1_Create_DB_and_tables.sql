create database if not exists hotel_db;

create table if not exists `user` (
  user_id bigint auto_increment primary key,
  user_name varchar(25),
  user_email varchar(35),
  user_password varchar(255),
  name varchar(20),
  last_name varchar(20),
  user_rol enum('ADMIN','CLIENT','EMPLOYEE') not null default 'CLIENT',
  user_status enum('ACTIVE','INACTIVE') not null default 'ACTIVE'
) engine=InnoDB;

create table if not exists category(
  category_id bigint auto_increment primary key,
  description varchar(255),
  price_per_night decimal(19,2) not null,
  max_occupancy int not null,
  category_type enum('SIMPLE','DOUBLE','SUITE') not null
) engine=InnoDB;

create table if not exists room(
  room_id bigint auto_increment primary key,
  room_number varchar(20) not null unique,
  room_status enum('OCCUPIED','DIRTY','CLEANING','AVAILABLE') not null default 'AVAILABLE',
  category_id bigint not null,
  constraint fk_room_category foreign key (category_id) references category(category_id)
) engine=InnoDB;

create table if not exists reservation(
  reservation_id bigint auto_increment primary key,
  check_in_date date not null,
  check_out_date date not null,
  number_of_guest int not null,
  total_amount decimal(19,2) not null,
  reservation_status enum('PENDING','CONFIRMED','CANCELLED','COMPLETED') not null default 'PENDING',
  user_id bigint,
  room_id bigint,
  constraint fk_reservation_user_id foreign key (user_id) references `user`(user_id),
  constraint fk_reservation_room_id foreign key (room_id) references room (room_id)
) engine=InnoDB;

create table if not exists room_service(
  room_service_id bigint auto_increment primary key,
  service_date date not null,
  start_service time not null,
  end_service time not null,
  notes varchar(255),
  service_type enum('CLEANING','MAINTENANCE','ROOM_SERVICE','LAUNDRY') not null default 'CLEANING',
  service_status enum('PENDING','IN_PROGRESS','CANCELLED','COMPLETED') not null default 'PENDING',
  user_id bigint,
  room_id bigint,
  constraint fk_room_service_employee_id foreign key (user_id) references `user`(user_id),
  constraint fk_room_service_room_id foreign key (room_id) references room (room_id)
) engine=InnoDB;
