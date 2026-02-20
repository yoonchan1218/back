set FOREIGN_KEY_CHECKS = 0;
drop table if exists tbl_qna_comment_likes;
drop table if exists tbl_qna_comment;
set FOREIGN_KEY_CHECKS = 1;

create table tbl_qna_comment
(
    id                   bigint unsigned auto_increment primary key,
    qna_id               bigint unsigned not null comment 'QnA ID',
    individual_member_id bigint unsigned not null comment '작성자 회원 ID',
    qna_comment_parent   bigint unsigned                  comment '부모 댓글 ID (대댓글용)',
    qna_comment_content  text            not null          comment '댓글 내용',
    created_datetime     datetime default current_timestamp,
    updated_datetime     datetime default current_timestamp on update current_timestamp,
    constraint fk_qna_comment_qna    foreign key (qna_id)               references tbl_qna (id) on delete cascade,
    constraint fk_qna_comment_member foreign key (individual_member_id)  references tbl_individual_member (id),
    constraint fk_qna_comment_parent foreign key (qna_comment_parent)    references tbl_qna_comment (id) on delete cascade,
    unique key uk_qna_member_comment (qna_id, individual_member_id, qna_comment_parent)
) comment 'QnA 댓글';
