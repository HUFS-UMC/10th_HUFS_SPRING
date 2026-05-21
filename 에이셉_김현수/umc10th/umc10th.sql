-- CREATE DATABASE umc10th;
-- USE umc10th;

-- 지역 테이블
CREATE TABLE `location` (
    `location_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL COMMENT '강남구, 종로구 등',
    PRIMARY KEY (`location_id`)
);

-- 음식 카테고리 테이블
CREATE TABLE `food` (
    `food_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` ENUM('KOREAN', 'JAPANESE', 'CHINESE', 'WESTERN', 'ETC') NOT NULL,
    PRIMARY KEY (`food_id`)
);

-- 약관 테이블
CREATE TABLE `term` (
    `term_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` ENUM('AGE', 'SERVICE', 'PRIVACY', 'LOCATION', 'MARKETING') NOT NULL,
    PRIMARY KEY (`term_id`)
);

-- 리뷰 답글 테이블 (리뷰보다 먼저 혹은 독립적으로 생성)
CREATE TABLE `reply` (
    `reply_id` BIGINT NOT NULL AUTO_INCREMENT,
    `content` TEXT NOT NULL,
    PRIMARY KEY (`reply_id`)
);

-- 사용자 테이블
CREATE TABLE `member` (
    `member_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(5) NOT NULL,
    `gender` ENUM('MALE', 'FEMALE', 'NONE') NOT NULL,
    `birth` DATE NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `detail_address` VARCHAR(255) NOT NULL,
    `social_uid` VARCHAR(255) NOT NULL,
    `social_type` ENUM('KAKAO', 'NAVER', 'APPLE', 'GOOGLE') NOT NULL,
    `point` INT NOT NULL DEFAULT 0,
    `email` VARCHAR(50) NOT NULL,
    `phone_number` VARCHAR(11) NULL,
    `profile_url` TEXT NULL,
    `deleted_at` TIMESTAMP NULL,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`member_id`)
);

-- 사용자 선호 음식 (N:M 매핑)
CREATE TABLE `member_food` (
    `member_food_id` BIGINT NOT NULL AUTO_INCREMENT,
    `member_id` BIGINT NOT NULL,
    `food_id` BIGINT NOT NULL,
    PRIMARY KEY (`member_food_id`),
    FOREIGN KEY (`member_id`) REFERENCES `member`(`member_id`),
    FOREIGN KEY (`food_id`) REFERENCES `food`(`food_id`)
);

-- 사용자 약관 동의 (N:M 매핑)
CREATE TABLE `member_term` (
    `member_term_id` BIGINT NOT NULL AUTO_INCREMENT,
    `member_id` BIGINT NOT NULL,
    `term_id` BIGINT NOT NULL,
    PRIMARY KEY (`member_term_id`),
    FOREIGN KEY (`member_id`) REFERENCES `member`(`member_id`),
    FOREIGN KEY (`term_id`) REFERENCES `term`(`term_id`)
);


-- 가게 테이블
CREATE TABLE `store` (
    `store_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `manager_number` BIGINT NOT NULL,
    `detail_address` VARCHAR(255) NOT NULL,
    `location_id` BIGINT NOT NULL,
    PRIMARY KEY (`store_id`),
    FOREIGN KEY (`location_id`) REFERENCES `location`(`location_id`)
);

-- 미션 테이블
CREATE TABLE `mission` (
    `mission_id` BIGINT NOT NULL AUTO_INCREMENT,
    `deadline` DATE NOT NULL,
    `conditional` TEXT NOT NULL,
    `point` INT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP NULL,
    `store_id` BIGINT NOT NULL,
    PRIMARY KEY (`mission_id`),
    FOREIGN KEY (`store_id`) REFERENCES `store`(`store_id`)
);

-- 사용자 미션 수행 현황 (N:M 매핑)
CREATE TABLE `member_mission` (
    `member_mission_id` BIGINT NOT NULL AUTO_INCREMENT,
    `is_complete` BIT(1) NOT NULL DEFAULT 0,
    `mission_id` BIGINT NOT NULL,
    `member_id` BIGINT NOT NULL,
    PRIMARY KEY (`member_mission_id`),
    FOREIGN KEY (`mission_id`) REFERENCES `mission`(`mission_id`),
    FOREIGN KEY (`member_id`) REFERENCES `member`(`member_id`)
);

-- 리뷰 테이블
CREATE TABLE `review` (
    `review_id` BIGINT NOT NULL AUTO_INCREMENT,
    `content` TEXT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `star` FLOAT NOT NULL,
    `store_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `reply_id` BIGINT NULL,
    PRIMARY KEY (`review_id`),
    FOREIGN KEY (`store_id`) REFERENCES `store`(`store_id`),
    FOREIGN KEY (`user_id`) REFERENCES `member`(`member_id`),
    FOREIGN KEY (`reply_id`) REFERENCES `reply`(`reply_id`)
);

-- 리뷰 사진 테이블
CREATE TABLE `review_photo` (
    `review_photo_id` BIGINT NOT NULL AUTO_INCREMENT,
    `photo_url` TEXT NULL,
    `review_id` BIGINT NOT NULL,
    PRIMARY KEY (`review_photo_id`),
    FOREIGN KEY (`review_id`) REFERENCES `review`(`review_id`)
);