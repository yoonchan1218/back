create table tbl_experience_program_company_category
(
    id                                  bigint unsigned auto_increment primary key,
    experience_program_category_name    varchar(255) not null,
    experience_program_category_checked boolean default false
);
