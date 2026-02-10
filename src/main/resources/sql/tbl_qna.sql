-- ============================================================
create table tbl_qna
(
    id                   bigint unsigned auto_increment primary key,
    individual_member_id bigint unsigned not null comment '작성자 ID',
    qna_title            varchar(255)    not null comment '제목',
    qna_content          text            not null comment '내용',
    qna_view_count       int                           default 0 comment '조회수',
    qna_status           enum ('published', 'deleted') default 'published' comment '상태',
    constraint fk_qna_member foreign key (individual_member_id) references tbl_individual_member (id)
);

select * from tbl_qna;

set FOREIGN_KEY_CHECKS = 1;

drop table tbl_qna;

