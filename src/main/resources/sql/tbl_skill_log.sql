-- ============================================================
create table tbl_skill_log
(
    id                            bigint unsigned auto_increment primary key comment '스킬로그 ID',
    member_id                     bigint unsigned not null comment '작성자 ID (FK: tbl_member)',
    experience_program_id         bigint unsigned comment '체험 프로그램 ID',
    skill_log_title               varchar(255)    not null comment '제목',
    skill_log_content             text            not null comment '내용',
    skill_log_view_count          int                           default 0 comment '조회수',
    skill_log_status              enum ('published', 'deleted') default 'published' comment '상태',
    created_datetime              datetime                      default current_timestamp,
    updated_datetime              datetime                      default current_timestamp,
    constraint fk_skill_log_member foreign key (member_id) references tbl_individual_member (id),
    constraint fk_skill_log_program foreign key (experience_program_id) references tbl_experience_program (id)
);


select * from tbl_skill_log;
set foreign_key_checks = 1;
drop table tbl_skill_log;
delete from tbl_skill_log;

select s.id, s.member_id, s.experience_program_id, s.skill_log_title, s.skill_log_content,
       s.skill_log_view_count, s.skill_log_status, s.created_datetime, s.updated_datetime,member_name
from tbl_member m join tbl_skill_log s
on m.id = s.member_id
where m.id = 9 and s.skill_log_status = 'published';

select * from tbl_tag  where skill_log_id = 30;
select * from view_skill_log_file  where skill_log_id = 30;


select count(distinct s.id)
from tbl_skill_log s
         left outer join tbl_tag t
                         on s.id = t.skill_log_id
where s.member_id = 9 and s.skill_log_status = 'published';