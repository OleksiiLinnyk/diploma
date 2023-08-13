TRUNCATE TABLE `diploma`.`test`;

ALTER TABLE `diploma`.`test`
  ADD COLUMN `user_id` INT NOT NULL;

ALTER TABLE `diploma`.`test`
ADD CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `diploma`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION;