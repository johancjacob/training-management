create table candidate(candidate_id int primary key,name varchar(20),email varchar(40),phone int);
alter table candidate change phone phone char(10);

create table batch(batch_code int primary key,course_id int,start_date date,end_date date);
alter table batch add foreign key(course_id) references course(course_id);

create table candidate_batch(candidate_id int,batch_code int,status varchar(20),primary key(candidate_id,batch_code),foreign key(candidate_id) references candidate(candidate_id),foreign key(batch_code) references batch(batch_code));

create table course(course_id int primary key,course_name varchar(50));

create table topic(topic_id int primary key,topic_name varchar(50),course_id int,foreign key(course_id) references course(course_id));

create table trainer(trainer_id int primary key,trainer_name varchar(50));

create table trainer_batch(trainer_id int,batch_code int,primary key(trainer_id,batch_code),foreign key(trainer_id) references trainer(trainer_id),foreign key(batch_code) references batch(batch_code));

create table assignment(assignment_id int primary key,title varchar(50),description text,batch_code int,assigned_byTrainer int,assigned_date date,due_date date,foreign key(batch_code) references batch(batch_code),foreign key(assigned_byTrainer) references trainer(trainer_id));

create table candidate_assignment(candidate_id int,assignment_id int,submission_date date,score int,primary key(candidate_id,assignment_id),foreign key(candidate_id) references candidate(candidate_id),foreign key(assignment_id) references assignment(assignment_id));