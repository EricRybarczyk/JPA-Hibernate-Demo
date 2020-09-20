-- saved script to use in IntelliJ to make code-completion somewhat happy
create table COURSES
(
	ID INT,
	NAME VARCHAR(255) not null,
	CREATED_DATE TIMESTAMP default LOCALTIMESTAMP(),
	LAST_UPDATED_DATE TIMESTAMP default LOCALTIMESTAMP()
);

create table STUDENTS
(
	ID INT,
	FIRST_NAME VARCHAR(255) not null,
	LAST_NAME VARCHAR(255) not null
);

create table PASSPORTS
(
	ID INT,
	PASSPORT_NUMBER VARCHAR(20) not null
);

create table REVIEWS
(
	ID INT,
	RATING VARCHAR(20) not null,
	REVIEW_CONTENT VARCHAR(255)
);

