-- At least some Unit Tests are likely to break when data changes are made here

-- COURSES
insert into courses (id, name, created_date, last_updated_date)
values (10001, 'first course', LOCALTIMESTAMP(), LOCALTIMESTAMP());
insert into courses (id, name, created_date, last_updated_date)
values (10002, 'second course', LOCALTIMESTAMP(), LOCALTIMESTAMP());
insert into courses (id, name, created_date, last_updated_date)
values (10003, 'fun course', LOCALTIMESTAMP(), LOCALTIMESTAMP());
insert into courses (id, name, created_date, last_updated_date)
values (9999, 'delete this course', LOCALTIMESTAMP(), LOCALTIMESTAMP());

-- PASSPORTS
insert into passports (id, passport_number)
values (30001, 'J9398003');
insert into passports (id, passport_number)
values (30002, 'E2218355');
insert into passports (id, passport_number)
values (30003, 'B0173762');

-- STUDENTS
insert into students (id, first_name, last_name, passport_id)
values (20001, 'Michael', 'Jordan', 30001);
insert into students (id, first_name, last_name, passport_id)
values (20002, 'Emmit', 'Smith', 30002);
insert into students (id, first_name, last_name, passport_id)
values (20003, 'Barry', 'Bonds', 30003);

-- REVIEWS
insert into reviews (id, rating, review_content, course_id)
values (40001, 'excellent', 'Excellent course', 10001);
insert into reviews (id, rating, review_content, course_id)
values (40002, 'average', 'Average course', 10001);
insert into reviews (id, rating, review_content, course_id)
values (40003, 'poor', 'Bad course', 10002);

insert into student_course (student_id, course_id)
values (20001, 10001);
insert into student_course (student_id, course_id)
values (20002, 10001);
insert into student_course (student_id, course_id)
values (20003, 10001);
insert into student_course (student_id, course_id)
values (20001, 10002);