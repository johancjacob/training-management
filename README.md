## Assumptions

-The candidate table contains the details of all the candidates.
-The batch table consists of all the batches, and a third table candidate_batch contains the combination of the values in candidate and batch table. The combination of (candidate_id + batch_code) is set as the primary key. This is done because each candidate can have multiple batches, and every batch can have many candidates.
-The trainer_batch works in a similar way as in candidate_batch, linking each trainer with as many batches as needed. Hence, we have set (trainer_id + batch_code) as the primary key for this table.
-The assignment table contains all information regarding each assignment, and another table named candidate_assignment takes care of all the combinations of candidates and assignments.

## Notes
-Here, it's assumed that while entering values in assigned_byTrainer column, we ensure that the trainer exists in the given batch.
-We must also be careful while entering dates, ensuring that they follow the correct order of occurrence.

## Refer to the ER Diagram for a complete understanding of the database schema.
