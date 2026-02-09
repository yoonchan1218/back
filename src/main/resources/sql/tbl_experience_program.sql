-- ============================================================
create table tbl_experience_program
(
    id                                     bigint unsigned auto_increment primary key comment '프로그램 ID',
    corp_id                                bigint unsigned not null comment '기업 ID',
    address_id                             bigint unsigned comment '근무지 주소 ID (FK)',
    experience_program_title               varchar(255)    not null comment '프로그램 제목',
    experience_program_description         text comment '프로그램 설명',
    experience_program_level               varchar(1) check (experience_program_level in ('a', 'b', 'c', 'd', 'e')) comment '체험 단계 (a~e)',
    experience_program_recruitment_count   int                                                 default 1 comment '모집 인원',
    experience_program_work_days           varchar(100) comment '근무 요일',
    experience_program_work_hours          varchar(100) comment '근무 시간',
    experience_program_start_date          date comment '시작일',
    experience_program_end_date            date comment '종료일',
    experience_program_deadline            date comment '지원 마감일',
    experience_program_status              enum ('draft', 'recruiting', 'closed', 'cancelled') default 'draft' comment '프로그램 상태',
    experience_program_view_count          int                                                 default 0 comment '조회수',
    created_datetime                       datetime                                            default current_timestamp,
    updated_datetime                       datetime                                            default current_timestamp,
    constraint fk_experience_program_corp foreign key (corp_id) references tbl_corp (id),
    constrain
        t fk_experience_program_address foreign key (address_id) references tbl_address (id)
);
