-- 기존 뷰 재생성 시
drop view if exists view_participant;

create view view_participant as
(
select c.id,
       c.apply_id,
       c.challenger_status,
       c.created_datetime,
       c.updated_datetime,
       a.experience_program_id,
       a.member_id,
       m.member_name,
       m.member_email,
       m.member_phone,
       m.member_profile_file_id,
       im.individual_member_birth,
       im.individual_member_gender
from tbl_challenger c
join tbl_apply a on c.apply_id = a.id
join tbl_member m on a.member_id = m.id
join tbl_individual_member im on m.id = im.id
);

select * from view_participant;

create or replace view view_applicant as
(
select a.id, a.experience_program_id, a.member_id, a.apply_status,
       a.created_datetime, a.updated_datetime,
       m.member_name, m.member_email, m.member_phone,
       im.individual_member_birth, im.individual_member_gender, im.individual_member_education
from tbl_apply a
         join tbl_member m on a.member_id = m.id
         join tbl_individual_member im on m.id = im.id
    );

