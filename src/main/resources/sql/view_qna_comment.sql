-- 기존 뷰 재생성 시
drop view if exists view_qna_comment;

create view view_qna_comment as
(
select c.id, c.qna_id, c.individual_member_id, m.member_name,
       c.qna_comment_parent, c.qna_comment_content,
       c.created_datetime, c.updated_datetime
from tbl_qna_comment c
left join tbl_member m on c.individual_member_id = m.id
);

select * from view_qna_comment;
