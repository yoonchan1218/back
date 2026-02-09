create table tbl_individual_member
(
    id                                bigint unsigned primary key comment '개인회원 ID (tbl_member.id)',
    individual_member_education       varchar(255) comment '최종학력',
    individual_member_point           int                         default 0 comment '보유 포인트',
    individual_member_level           int                         default 1 comment '회원 레벨',
    individual_member_post_count      int                         default 0 comment '작성한 글 수',
    individual_member_question_count  int                         default 0 comment '질문 수',
    constraint fk_individual_member foreign key (id) references tbl_member (id)
) comment '개인회원 서브타입';

set FOREIGN_KEY_CHECKS = 1;
drop table tbl_individual_member;