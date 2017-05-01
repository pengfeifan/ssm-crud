CREATE DATABASE `ssm_crud`CHARACTER SET utf8; 
CREATE TABLE `ssm_crud`.`tb_emp`( `emp_id` INT(11) NOT NULL AUTO_INCREMENT, `emp_name` VARCHAR(255) NOT NULL, `gender` CHAR(1), `email` VARCHAR(255), `deptment_id` INT(11), PRIMARY KEY (`emp_id`) ); 
CREATE TABLE `ssm_crud`.`tb_dept`( `dept_id` INT(11) NOT NULL AUTO_INCREMENT, `dept_name` VARCHAR(255) NOT NULL, PRIMARY KEY (`dept_id`) );  
ALTER TABLE `ssm_crud`.`tb_emp` ADD CONSTRAINT `fk_emp_dept` FOREIGN KEY (`deptment_id`) REFERENCES `ssm_crud`.`tb_dept`(`dept_id`); 