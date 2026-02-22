create view view_member_profile as
select
    m.id,
    m.member_id,
    m.member_name,
    m.member_email,
    m.member_phone,
    m.address_id,
    m.member_status,
    m.member_agree_privacy,
    m.member_agree_marketing,
    m.created_datetime,
    m.updated_datetime,
    i.individual_member_birth,
    i.individual_member_gender,
    i.individual_member_education,
    i.individual_member_point,
    i.individual_member_level,
    i.individual_member_post_count,
    i.individual_member_question_count
from tbl_member m
left join tbl_individual_member i on m.id = i.id;

select * from view_member_profile;
