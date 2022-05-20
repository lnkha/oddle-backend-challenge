--
-- Create DB Oddle
--
CREATE DATABASE `oddle`
DEFAULT CHARACTER SET utf8

--
-- Create table tbl_weather
--
CREATE TABLE tbl_weather (
  `id` bigint(20) NOT NULL,
  `clouds` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `main` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `temperature` varchar(255) DEFAULT NULL,
  `wind_speed` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

