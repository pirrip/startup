drop table if exists `property_source`;
create table `property_source` (
  `pid` int unsigned not null auto_increment comment '프로퍼티아이디',
  `site` char(32) not null comment '사이트',
  `profile` text not null comment '스프링프로파일',
  `property_key` varchar(255) comment '키',
  `se` varchar(255) comment '구분',
  `property_name` varchar(255) comment '키설명',
  `property_value` char(1) comment '값',
  primary key  (`pid`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci;

drop table if exists `message_source`;
create table `message_source` (
  `page` varchar(255) not null comment '페이지',
  `locale` varchar(255) not null comment '로케일',
  `code` varchar(255) not null comment '메시지 코드(키)',
  `message` text not null comment '메시지',
  primary key  (`page`, `locale`, `code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci;


drop table if exists `file_info`;
create table `file_info` (
  `seq` bigint unsigned not null auto_increment comment '연번',
  `filename` char(32) not null comment '파일명',
  `original_filename` text not null comment '원파일명',
  `file_type` varchar(255) comment '파일형식',
  `mime_type` varchar(255) comment '마임타입',
  `tika_type` varchar(255) comment '티카타입',
  `is_use` char(1) comment '사용여부',
  `crdt` datetime comment '생성일',
  primary key  (`seq`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci;

drop table if exists `file_holder`;
create table `file_holder` (
  `seq` bigint unsigned not null comment '연번',
  `file_seq` int unsigned not null unique comment '파일연번',
  `file` longblob comment '파일',
  primary key  (`seq`, `file_seq`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci;

drop table if exists `file_meta`;
create table `file_meta` (
  `seq` bigint unsigned not null unique comment '연번',
  `file_seq` int unsigned not null unique comment '파일연번',
  `format` text,
  `identifier` text,
  `contributor` text,
  `coverage` text,
  `creator` text,
  `modifier` text,
  `creator_tool` text,
  `language` text,
  `publisher` text,
  `relation` text,
  `rights` text,
  `source` text,
  `type` text,
  `title`  text,
  `description` text,
  `keywords` text,
  `created` text,
  `modified` text,
  `print_date` text,
  `metadata_date` text,
  `latitude` text,
  `longitude` text,
  `altitude` text,
  `rating` text,
  `comments` text,
  primary key  (`seq`, `file_seq`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci;


