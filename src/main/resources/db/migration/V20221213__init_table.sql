DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(255),
    `password` VARCHAR(255),
    `salt` VARCHAR(255),
    `refresh_token` VARCHAR(255)
);