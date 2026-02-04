create table tbl_apply
(
    id                        bigint unsigned auto_increment primary key comment '지원 ID',
    experience_program_id     bigint unsigned not null comment '프로그램 ID',
    individual_member_id      bigint unsigned not null comment '개인회원 ID',
    apply_portfolio_file_path varchar(255) comment '포트폴리오 파일 경로',
    apply_portfolio_file_size int comment '포트폴리오 파일 크기 (byte)',
    apply_portfolio_file_name varchar(255) comment '포트폴리오 파일명',
    apply_status              enum ('applied', 'document_pass', 'document_fail',
        'interview_scheduled', 'interview_pass', 'interview_fail',
        'final_pass', 'final_fail', 'withdrawn') default 'applied' comment '지원 상태',
    apply_applied_at          timestamp          default current_timestamp comment '지원일',
    apply_updated_at          timestamp          default current_timestamp on update current_timestamp comment '수정일',
    foreign key (experience_program_id) references tbl_experience_program (id),
    foreign key (individual_member_id) references tbl_individual_member (id),
    unique key uk_program_member (experience_program_id, individual_member_id)
);