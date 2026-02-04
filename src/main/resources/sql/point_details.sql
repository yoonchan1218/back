create table tbl_point_details
(
    id                   bigint unsigned auto_increment primary key comment '포인트 내역 ID',
    individual_member_id bigint unsigned                not null comment '개인회원 ID',
    point_type           enum ('EARN', 'USE', 'EXPIRE') not null comment '포인트 유형',
    point_amount         int                            not null comment '포인트 금액 (양수: 적립, 음수: 차감)',
    point_balance_after  int                            not null comment '거래 후 잔액',
    point_expire_date    datetime comment '포인트 만료일',
    point_is_expired     boolean  default false comment '만료 여부',
    point_created_at     datetime default current_timestamp comment '거래일시',
    point_cancel_checked boolean  default false comment '구매 취소',
    foreign key (individual_member_id) references tbl_individual_member (id) on delete cascade
);