-- ============================================================
create table tbl_qna_job_category
(
    id                     bigint unsigned auto_increment primary key comment '직무 카테고리 ID',
    job_category_name      varchar(255) not null comment '카테고리명',
    job_category_code      varchar(255) comment '카테고리 고유 식별 코드 (예: DEV_BACKEND, DEV_FRONTEND, DESIGN_UI 등)',
    job_category_big_id bigint unsigned not null comment '대카 FK',
    constraint fk_qna_job_category_big foreign key  (job_category_big_id) references tbl_qna_job_category_big(id)
);

drop table tbl_qna_job_category;

insert tbl_qna_job_category(id, job_category_name, job_category_code, job_category_big_id) values 
