create table tbl_point_details
(
    id                   bigint unsigned auto_increment primary key comment '포인트 내역 ID',
    member_id bigint unsigned not null comment '개인회원 ID',
    point_type           enum ('earn', 'use', 'expire', 'purchase_cancel') not null comment '포인트 유형',
    point_amount         int not null comment '포인트 금액',
    payment_amount       int null comment '결제금액 (원)',
    expire_datetime      datetime null comment '유효기간',
    created_datetime     datetime default current_timestamp,
    updated_datetime     datetime default current_timestamp,
    constraint fk_point_details_member foreign key (member_id) references tbl_individual_member (id)
);

drop table tbl_point_details;
set FOREIGN_KEY_CHECKS =1;

select * from tbl_point_details;