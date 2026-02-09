-- ============================================================
create table tbl_individual_member
(
    id                                bigint unsigned primary key comment '개인회원 ID (tbl_member.id)',
    individual_member_id              varchar(255) not null unique comment '로그인 ID',
    individual_member_password        varchar(255) not null comment '비밀번호',
    individual_member_birth           date         not null comment '생년월일',
    individual_member_gender          enum ('man','women')        not null comment '성별',
    individual_member_education       varchar(255) comment '최종학력',
    individual_member_point           int                         default 0 comment '보유 포인트',
    individual_member_level           int                         default 1 comment '회원 레벨',
    individual_member_post_count      int                         default 0 comment '작성한 글 수',
    individual_member_question_count  int                         default 0 comment '질문 수',
    individual_member_agree_marketing boolean                     default false comment '마케팅 수신 동의',
    constraint fk_individual_member foreign key (id) references tbl_member (id)
) comment '개인회원 서브타입';

