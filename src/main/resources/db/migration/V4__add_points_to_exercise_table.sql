ALTER TABLE `diploma`.`exercise` ADD COLUMN `points` INT NOT NULL AFTER `answer`;

ALTER TABLE `diploma`.`user_has_exercise`
    ADD COLUMN `given_answer` TEXT NULL AFTER `exercise_id`,
    ADD COLUMN `checked` BOOLEAN AFTER `given_answer`,
    ADD COLUMN `taken_points` INT AFTER `checked`;
