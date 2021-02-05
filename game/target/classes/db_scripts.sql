create database bowling_game;
use bowling_game;
create table `player` (
`id` bigint(12) not null auto_increment,
`game_id` bigint(12) not null,
`player_name` varchar(100) not null,
`current_score` int not null default 0,
created_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
primary key(id),
index `idx_game_id_player_name` (game_id,player_name)
);

create table `moves` (
`id` bigint(12) not null auto_increment,
`game_id` bigint(12) not null,
`player_id` bigint(12) not null,
`pins_down` int not null default 0,
`previous_move_id` bigint(12) not null default 0,
`spare` tinyint not null default 0,
`strike` tinyint not null default 0,
`set_number` int not null default 1,
created_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
primary key(id),
index `game_id,player_id` (game_id,player_id)
);

create table `game` (
    `id` bigint(12) not null auto_increment,
    `status` varchar(25) not null,
    `players_count` int not null default 0,
    `lane_number` int not null,
    `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key(id)
);

create table `response_mapper` (
`id` bigint(12) not null auto_increment,
`resp_code` varchar(25) not null,
`resp_msg` varchar(255) not null,
created_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
primary key(id)
);

insert into response_mapper(resp_code,resp_msg) values('NO_PLAYER_FOUND','No player found with given details');
insert into response_mapper(resp_code,resp_msg) values('NOT_ENOUGH_FREE_LANES','No lane free to start a game');
insert into response_mapper(resp_code,resp_msg) values('NULL_REQUEST_RECEIVED','Invalid request received');
insert into response_mapper(resp_code,resp_msg) values('INVALID_PLAYERS','Invalid configuration provided for players');
insert into response_mapper(resp_code,resp_msg) values('INTERNAL_SYSTEM_ERROR','Internal System error');

create table `lane` (
`id` int(3) not null auto_increment,
`status` varchar(50) not null default 'FREE',
primary key(id));

insert into lane values(1,'FREE');
insert into lane values(2,'FREE');
insert into lane values(3,'FREE');
insert into lane values(4,'FREE');
insert into lane values(5,'FREE');


CREATE TABLE `application_properties` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_on` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `idx_updated_on` (`updated_on`)
);

insert into application_properties(name,value,created_by) values('TOTAL_NUMBER_OF_SETS','10','MANUAL');
insert into application_properties(name,value,created_by) values('CHANCE_PER_SET','2','MANUAL');
insert into application_properties(name,value,created_by) values('NUMBER_OF_PIN_BALLS_PER_SET','10','MANUAL');
insert into application_properties(name,value,created_by) values('BONUS_SCORE_FOR_STRIKE','10','MANUAL');
insert into application_properties(name,value,created_by) values('BONUS_SCORE_FOR_SPARE','5','MANUAL');
insert into application_properties(name,value,created_by) values('NUMBER_OF_PLAYERS_PER_LANE','3','MANUAL');
insert into application_properties(name,value,created_by) values('MAXIMUM_CHANCE_FOR_LAST_SET','3','MANUAL');