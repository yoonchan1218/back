-- ============================================================
create table tbl_skill_log_tag
(
    id               bigint unsigned auto_increment primary key,
    skill_log_id     bigint unsigned not null comment '스킬로그 ID',
    tag_id           bigint unsigned not null comment '태그 ID',
    constraint fk_skill_log_tag_log foreign key (skill_log_id) references tbl_skill_log (id),
    constraint fk_skill_log_tag_tag foreign key (tag_id) references tbl_tag (id),
    unique key uk_skill_log_tag (skill_log_id, tag_id)
) comment '기술 블로그-태그 N:N';


select * from tbl_skill_log_tag;