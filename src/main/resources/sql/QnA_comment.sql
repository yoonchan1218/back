create table tbl_qna_comment
(
    id                     bigint unsigned auto_increment primary key,
    qna_id                 bigint unsigned not null,
    individual_member_id   bigint unsigned not null,
    qna_comment_parent     bigint unsigned,
    qna_comment_content    text            not null,
    qna_comment_images     json,
    qna_comment_like_count int       default 0,
    qna_comment_created_at timestamp default current_timestamp,
    qna_comment_updated_at timestamp default current_timestamp on update current_timestamp,

    foreign key (qna_id) references tbl_qna (id) on delete cascade,
    foreign key (individual_member_id) references tbl_individual_member (id) on delete cascade,
    foreign key (qna_comment_parent) references tbl_qna_comment (id) on delete cascade
);