create table tbl_qna_job_category_small
(
    id                      bigint unsigned auto_increment primary key comment '직무 카테고리 ID',
    job_category_parent_id  bigint unsigned comment '부모 카테고리 ID',
    job_category_name       varchar(100) not null comment '카테고리명',
    job_category_code       varchar(20) comment '카테고리 코드',
    job_category_is_active  boolean   default true comment '활성 상태',
    job_category_created_at datetime default current_timestamp comment '생성일',
    foreign key (job_category_parent_id) references tbl_qna_job_category_big (id)
)