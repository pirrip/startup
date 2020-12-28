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
CREATE TABLE `FILE_INFO` (
  `FILENAME` CHAR(32) NOT NULL COMMENT '파일명',
  `ORIGINAL_FILENAME` TEXT NOT NULL COMMENT '원파일명',
  `FILE_TYPE` VARCHAR(255) COMMENT '파일형식',
  `MIME_TYPE` VARCHAR(255) COMMENT '마임타입',
  `TIKA_TYPE` VARCHAR(255) COMMENT '티카타입',
  `IS_USE` CHAR(1) COMMENT '사용여부',
  `CRDT` DATETIME COMMENT '생성일',
  PRIMARY KEY  (`FILENAME`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci;

drop table if exists `file_holder`;
CREATE TABLE `FILE_HOLDER` (
  `FILENAME` CHAR(32) NOT NULL COMMENT '파일명',
  `FILE` LONGBLOB COMMENT '파일',
  PRIMARY KEY  (`FILENAME`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci;

drop table if exists `file_meta`;
CREATE TABLE `FILE_META` (
  `FILENAME` CHAR(32) NOT NULL COMMENT '파일명',
  `FORMAT` TEXT,
  `IDENTIFIER` TEXT,
  `CONTRIBUTOR` TEXT,
  `COVERAGE` TEXT,
  `CREATOR` TEXT,
  `MODIFIER` TEXT,
  `CREATOR_TOOL` TEXT,
  `LANGUAGE` TEXT,
  `PUBLISHER` TEXT,
  `RELATION` TEXT,
  `RIGHTS` TEXT,
  `SOURCE` TEXT,
  `TYPE` TEXT,
  `TITLE`  TEXT,
  `DESCRIPTION` TEXT,
  `KEYWORDS` TEXT,
  `CREATED` TEXT,
  `MODIFIED` TEXT,
  `PRINT_DATE` TEXT,
  `METADATA_DATE` TEXT,
  `LATITUDE` TEXT,
  `LONGITUDE` TEXT,
  `ALTITUDE` TEXT,
  `RATING` TEXT,
  `COMMENTS` TEXT,
  PRIMARY KEY  (`FILENAME`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci;


