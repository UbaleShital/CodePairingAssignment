CREATE DATABASE  IF NOT EXISTS `projectdb`;
USE `projectdb`;

drop table if exists `trade`;

create table `trade`(
`trade_id` varchar(10) not null,
`version` int(10) not null,
`counter_party_id` varchar(10),
`book_id` varchar(10),
`maturity_date` date,
`created_date` date,
`expired` varchar(1),
constraint trade_pk primary key(trade_id, version)
)

