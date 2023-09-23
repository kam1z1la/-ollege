create table schedule_replacement_table
(
    id           bigserial primary key,
    resource     bytea,
    posting_date date
);

--SELECT * FROM main.schedule_replacement_table WHERE posting_date = '2023-09-22';


