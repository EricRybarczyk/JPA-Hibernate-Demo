-- saved script to use in IntelliJ to make code-completion somewhat happy
create table COURSES
(
	ID BIGINT,
	NAME VARCHAR(255) not null,
	CREATED_DATE TIMESTAMP default LOCALTIMESTAMP(),
	LAST_UPDATED_DATE TIMESTAMP default LOCALTIMESTAMP(),
    primary key (ID)
);

create table PASSPORTS
(
    ID BIGINT,
    PASSPORT_NUMBER VARCHAR(20) not null,
    primary key (ID)
);

create table STUDENTS
(
	ID BIGINT,
	FIRST_NAME VARCHAR(255) not null,
	LAST_NAME VARCHAR(255) not null,
    PASSPORT_ID BIGINT,
    primary key (ID)
);

alter table STUDENTS
    add constraint FK_student_passport_id_ISA_passport_id
        foreign key (passport_id)
            references passports;

create table REVIEWS
(
	ID BIGINT,
	RATING VARCHAR(20) not null,
	REVIEW_CONTENT VARCHAR(255),
    primary key (ID)
);
