-- COURSES
insert into courses (id, name, created_date, last_updated_date)
values (10001, 'first course', LOCALTIMESTAMP(), LOCALTIMESTAMP());
insert into courses (id, name, created_date, last_updated_date)
values (10002, 'second course', LOCALTIMESTAMP(), LOCALTIMESTAMP());
insert into courses (id, name, created_date, last_updated_date)
values (10003, 'fun course', LOCALTIMESTAMP(), LOCALTIMESTAMP());
insert into courses (id, name, created_date, last_updated_date)
values (9999, 'delete this course', LOCALTIMESTAMP(), LOCALTIMESTAMP());

-- STUDENTS
insert into students (id, first_name, last_name)
values (20001, 'Michael', 'Jordan');
insert into students (id, first_name, last_name)
values (20002, 'Emmit', 'Smith');
insert into students (id, first_name, last_name)
values (20003, 'Barry', 'Bonds');

-- PASSPORTS
insert into passports (id, passport_number)
values (30001, 'J9398003');
insert into passports (id, passport_number)
values (30002, 'E2218355');
insert into passports (id, passport_number)
values (30003, 'B0173762');

-- REVIEWS
insert into reviews (id, rating, review_content)
values (40001, 'excellent', 'Excellent course');
insert into reviews (id, rating, review_content)
values (40002, 'average', 'Average course');
insert into reviews (id, rating, review_content)
values (40003, 'poor', 'Bad course');