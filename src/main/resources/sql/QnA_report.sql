create table tbl_qna_report
(
    id                       bigint unsigned auto_increment primary key,
    qna_report_target_type   enum('QNA', 'COMMENT') not null,
    qna_report_target_id     bigint unsigned not null,
    individual_member_id     bigint unsigned not null,
    qna_report_reason_code   int not null,
    qna_report_reason_detail text,
    qna_report_status        enum('PENDING', 'REVIEWING', 'PROCESSED', 'REJECTED') default 'PENDING',
    qna_report_created_at    timestamp default current_timestamp,
    qna_report_processed_at  timestamp null,
    foreign key (individual_member_id) references tbl_individual_member (id) on delete cascade
);