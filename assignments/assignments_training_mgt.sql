-- queries
select * from candidate_batch order by candidate_id;

select trainer_name from trainer_batch join trainer on trainer.trainer_id=trainer_batch.trainer_id where batch_code=100;

select * from topic where course_id=8000;

select candidate_id,assignment.assignment_id,score from candidate_assignment join assignment on candidate_assignment.assignment_id=assignment.assignment_id where assignment.batch_code=300 and candidate_id=2;

select candidate.candidate_id,name,candidate_batch.status from candidate_batch join candidate on candidate.candidate_id=candidate_batch.candidate_id where status='completed';