ALTER TABLE `diploma`.`exercise`
DROP CONSTRAINT `fk_exercise_test1`;

ALTER TABLE `diploma`.`exercise`
ADD CONSTRAINT `fk_exercise_test1`
    FOREIGN KEY (`test_id`)
    REFERENCES `diploma`.`test` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION;