-- At least some Unit Tests are likely to break when data changes are made here

-- COURSES
insert into courses (id, name, created_date, last_updated_date, is_deleted)
values (10001, 'first course', LOCALTIMESTAMP(), LOCALTIMESTAMP(), false);
insert into courses (id, name, created_date, last_updated_date, is_deleted)
values (10002, 'second course', LOCALTIMESTAMP(), LOCALTIMESTAMP(), false);
insert into courses (id, name, created_date, last_updated_date, is_deleted)
values (10003, 'fun course', LOCALTIMESTAMP(), LOCALTIMESTAMP(), false);
insert into courses (id, name, created_date, last_updated_date, is_deleted)
values (9999, 'delete this course', LOCALTIMESTAMP(), LOCALTIMESTAMP(), false);

-- PASSPORTS
insert into passports (id, passport_number)
values (30001, 'J0012340023B45');
insert into passports (id, passport_number)
values (30002, 'E0022Y18355DCB');
insert into passports (id, passport_number)
values (30003, 'B001234B173762');

-- STUDENTS
insert into students (id, first_name, last_name, passport_id)
values (20001, 'Michael', 'Jordan', 30001);
insert into students (id, first_name, last_name, passport_id)
values (20002, 'Emmit', 'Smith', 30002);
insert into students (id, first_name, last_name, passport_id)
values (20003, 'Barry', 'Bonds', 30003);

-- REVIEWS
insert into reviews (id, rating, review_content, course_id)
values (40001, 'FIVE', 'Excellent course', 10001);
insert into reviews (id, rating, review_content, course_id)
values (40002, 'THREE', 'Average course', 10001);
insert into reviews (id, rating, review_content, course_id)
values (40003, 'ONE', 'Bad course', 10002);

insert into student_course (student_id, course_id)
values (20001, 10001);
insert into student_course (student_id, course_id)
values (20002, 10001);
insert into student_course (student_id, course_id)
values (20003, 10001);
insert into student_course (student_id, course_id)
values (20001, 10002);