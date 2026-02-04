use try_catch;
create table tbl_qna(
id bigint unsigned auto_increment primary key,
individual_member_id bigint unsigned not null,
qna_title varchar(255) not null,
qna_content text not null,
qna_images json,
qna_view_count int default 0,
qna_like_count int default 0,
qna_comment_count int default 0,
qna_status enum('PUBLISHED', 'DELETED') default 'PUBLISHED',
qna_created_at timestamp default current_timestamp,
qna_updated_at timestamp default current_timestamp on update current_timestamp,
foreign key (individual_member_id) references tbl_individual_member(id) on delete cascade
);