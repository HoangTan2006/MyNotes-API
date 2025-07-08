CREATE TABLE `users` (
    `id` INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(20) NOT NULL,
    `last_name` VARCHAR(20) NOT NULL,
    `username` VARCHAR(50) UNIQUE NOT NULL,
    `password` VARCHAR(256) NOT NULL,
    `email` VARCHAR(100) UNIQUE NOT NULL,
    `is_lock` BOOLEAN DEFAULT FALSE,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `vocabularies` (
    `id` BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `user_id` INTEGER,
    `word` VARCHAR(100) NOT NULL,
    `note` VARCHAR(500),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `roles` (
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `role_name` VARCHAR(50) UNIQUE NOT NULL,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `user_has_role` (
    `user_id` INTEGER,
    `role_id` INTEGER,
    PRIMARY KEY (`user_id`, `role_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES `roles`(`id`) ON DELETE CASCADE,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `user_has_vocabulary` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INTEGER,
    `vocabulary_id` BIGINT,
    UNIQUE KEY (`user_id`, `vocabulary_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`vocabulary_id`) REFERENCES `vocabularies`(`id`) ON DELETE CASCADE,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO `roles`(`role_name`) VALUES ('ROLE_USER');
INSERT INTO `roles` (`role_name`) VALUES ('ROLE_ADMIN');