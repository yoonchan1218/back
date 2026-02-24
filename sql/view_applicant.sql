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
