CREATE DATABASE university;

\c university;

CREATE TABLE students (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) UNIQUE NOT NULL,
   phone VARCHAR(30),
   cpf CHAR(11) UNIQUE NOT NULL
);

CREATE TABLE professors (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) UNIQUE NOT NULL,
   phone VARCHAR(30),
   cpf CHAR(11) UNIQUE NOT NULL
);

CREATE TABLE degrees (
   id SERIAL PRIMARY KEY,
   name VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE programs (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   degree_id INTEGER NOT NULL,
   total_semesters SMALLINT NOT NULL,
   active BOOLEAN NOT NULL DEFAULT TRUE,
   version INTEGER NOT NULL,
   UNIQUE(name, degree_id),
   FOREIGN KEY (degree_id) REFERENCES degrees (id)
);

CREATE TABLE programs_history (
   id SERIAL,
   name VARCHAR(255) NOT NULL,
   degree_id INTEGER NOT NULL,
   total_semesters SMALLINT NOT NULL,
   modification_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   active BOOLEAN NOT NULL,
   version INTEGER NOT NULL,
   PRIMARY KEY (id, version)
);

CREATE TABLE classrooms (
   id SERIAL PRIMARY KEY,
   name VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE subjects (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255) UNIQUE NOT NULL,
   total_hours INTEGER NOT NULL,
   classroom_id INTEGER,
   professor_id INTEGER,
   active BOOLEAN NOT NULL DEFAULT TRUE,
   version INTEGER NOT NULL,
   FOREIGN KEY (classroom_id) REFERENCES classrooms (id),
   FOREIGN KEY (professor_id) REFERENCES professors (id)
);

CREATE TABLE subjects_history (
   id SERIAL,
   name VARCHAR(255) UNIQUE NOT NULL,
   total_hours INTEGER NOT NULL,
   classroom_id INTEGER,
   professor_id INTEGER,
   modification_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   active BOOLEAN NOT NULL,
   version INTEGER NOT NULL,
   PRIMARY KEY (id, version)
);

CREATE TABLE programs_subjects (
   subject_id INTEGER,
   program_id INTEGER,
   PRIMARY KEY (subject_id, program_id),
   FOREIGN KEY (subject_id) REFERENCES subjects (id),
   FOREIGN KEY (program_id) REFERENCES programs (id)
);

CREATE TABLE subject_enrollment (
   enrollment_id SERIAL,
   student_id INTEGER,
   subject_id INTEGER,
   score DECIMAL(3,1),
   enroll_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   drop_time TIMESTAMP,
   active BOOLEAN NOT NULL DEFAULT TRUE,
   PRIMARY KEY (enrollment_id),
   FOREIGN KEY (student_id) REFERENCES students (id),
   FOREIGN KEY (subject_id) REFERENCES subjects (id),
);

CREATE TABLE program_enrollment (
   enrollment_id SERIAL,
   student_id INTEGER,
   program_id INTEGER,
   enroll_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   drop_time TIMESTAMP,
   active BOOLEAN NOT NULL DEFAULT TRUE,
   PRIMARY KEY (enrollment_id),
   FOREIGN KEY (student_id) REFERENCES students (id),
   FOREIGN KEY (program_id) REFERENCES programs (id),
);