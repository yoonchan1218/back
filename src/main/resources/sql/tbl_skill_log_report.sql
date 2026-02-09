-- ============================================================
create table tbl_skill_log_report
(
    id                             bigint unsigned primary key comment '기술블로그 리포트 ID (tbl_report.id)',
    skill_log_report_target_type   enum ('log', 'comment') not null comment '신고 대상 유형',
    skill_log_report_target_id     bigint unsigned not null comment '신고 대상 ID',
    constraint fk_skill_log_report foreign key (id) references tbl_report (id)
) comment '기술 블로그 리포트 서브타입';

