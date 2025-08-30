-- candidate
insert into candidate values(1,'johan','jcj@gmqail.com','8776326441');
insert into candidate values(2,'joan','joan.com','9999999220');
insert into candidate values(3,'jacob','jcb.com','9941946625');
insert into candidate values(4,'siji','haisji.com','9710157569');
insert into candidate values(5,'leo','leo.com','9710157568');
insert into candidate values(6,'rachel','rachel.com','7643845761');
insert into candidate values(7,'nikkitta','nikky.com','9710150569');
insert into candidate values(8,'joel','joel.com','9010157569');

-- batch
insert into batch values(100,5000,'2000-06-21','2001-09-07');
insert into batch values(200,5000,'2005-06-23','2006-09-07');
insert into batch values(300,6000,'2000-09-21','2001-12-04');
insert into batch values(400,6000,'2006-02-12','2006-12-17');
insert into batch values(500,6000,'2007-06-21','2008-09-02');
insert into batch values(600,7000,'2002-06-27','2003-09-05');
insert into batch values(700,8000,'2004-03-12','2004-11-07');
insert into batch values(800,8000,'2001-06-21','2001-10-08');
insert into batch values(900,8000,'2006-06-21','2007-05-06');
insert into batch values(1000,8000,'2007-01-21','2007-09-02');

-- candidate_batch
insert into candidate_batch values(1,100,'completed');
insert into candidate_batch values(1,300,'in_progress');
insert into candidate_batch values(2,300,'completed');
insert into candidate_batch values(3,600,'in_progress');
insert into candidate_batch values(4,200,'completed');
insert into candidate_batch values(4,700,'in_progress');
insert into candidate_batch values(5,100,'in_progress');
insert into candidate_batch values(5,400,'in_progress');
insert into candidate_batch values(6,200,'completed');
insert into candidate_batch values(5,800,'completed');
insert into candidate_batch values(6,500,'in_progress');
insert into candidate_batch values(6,600,'in_progress');
insert into candidate_batch values(7,900,'completed');
insert into candidate_batch values(8,300,'completed');
insert into candidate_batch values(8,1000,'in_progress');

-- course
insert into course values(5000,'maths');
insert into course values(6000,'physics');
insert into course values(7000,'chemistry');
insert into course values(8000,'biology');

-- topic
insert into topic values(5005,'geometry',5000);
insert into topic values(5006,'algebra',5000);
insert into topic values(6003,'quantum_mechanics',6000);
insert into topic values(6008,'refraction',6000);
insert into topic values(7002,'organic_chemistry',7000);
insert into topic values(8006,'evolution',8000);
insert into topic values(8007,'life_processes',8000);
insert into topic values(8008,'anatomy',8000);

-- trainer
insert into trainer values(1,'rajeev');
insert into trainer values(2,'rajesh');
insert into trainer values(3,'ramesh');

--trainer_batch
insert into trainer_batch values(1,100);
insert into trainer_batch values(1,200);
insert into trainer_batch values(1,300);
insert into trainer_batch values(2,400);
insert into trainer_batch values(2,100);
insert into trainer_batch values(2,500);
insert into trainer_batch values(2,600);
insert into trainer_batch values(2,700);
insert into trainer_batch values(3,200);
insert into trainer_batch values(3,400);
insert into trainer_batch values(3,700);
insert into trainer_batch values(3,800);
insert into trainer_batch values(3,900);
insert into trainer_batch values(3,1000);

--assignment
insert into assignment values(1,'parallel lines','check if two lines are parallel',100,1,'2000-07-21','2000-07-22');
insert into assignment values(2,'area of shapes','find area of the given shapes',200,1,'2005-07-24','2005-07-25');
insert into assignment values(3,'important formulae','write down all the important formulae taught in class',300,1,'2000-09-21','2000-10-21');
insert into assignment values(4,'force','compute the force using equation',400,2,'2006-02-24','2006-02-28');
insert into assignment values(5,'work','find the work done using equation',400,3,'2006-02-25','2005-06-26');
insert into assignment values(6,'work','find the work done using equation',500,2,'2007-07-24','2007-07-25');
insert into assignment values(7,'nomenclature','write down the scientific names of all inert gases',600,2,'2002-05-24','2005-05-25');
insert into assignment values(8,'food chain','draw the food chain',700,2,'2004-07-24','2004-07-25');
insert into assignment values(9,'food chain','draw the food chain',800,2,'2001-07-24','2001-07-25');
insert into assignment values(10,'food chain','draw the food chain',900,3,'2007-07-22','2007-07-23');
insert into assignment values(11,'food chain','draw the food chain',1000,3,'2007-10-24','2007-10-25');
insert into assignment values(12,'heirarchial classification','explain heirarchial classification',1000,3,'2007-11-24','2007-11-25');

--candidate_assignment
insert into candidate_assignment values(1,1,'2000-07-22',100);
insert into candidate_assignment values(5,1,'2000-07-22',76);
insert into candidate_assignment values(4,2,'2005-07-25',89);
insert into candidate_assignment values(6,2,'2005-07-24',100);
update assignment set due_date='2006-02-28' where assignment_id=5;
insert into candidate_assignment values(1,3,'2000-09-24',88);
insert into candidate_assignment values(2,3,'2000-09-23',100);
insert into candidate_assignment values(8,3,'2000-09-28',77);
insert into candidate_assignment values(5,4,'2006-06-25',45);
insert into candidate_assignment values(5,5,'2006-02-25',100);
insert into candidate_assignment values(6,6,'2007-07-25',95);
insert into candidate_assignment values(3,7,'2002-05-24',90);
insert into candidate_assignment values(6,7,'2002-05-24',100);
insert into candidate_assignment values(4,8,'2004-07-25',67);
insert into candidate_assignment values(5,9,'2001-07-24',63);
insert into candidate_assignment values(7,10,'2007-07-22',100);
insert into candidate_assignment values(8,11,'2007-10-24',58);
insert into candidate_assignment values(8,12,'2007-11-25',100);