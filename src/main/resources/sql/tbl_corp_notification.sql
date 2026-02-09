-- ============================================================


create table tbl_corp_notification
(
    id                        bigint unsigned auto_increment primary key comment '알림 ID',
    corp_id                   bigint unsigned not null comment '수신자 (기업) ID',
    notification_type         enum ('apply_received', 'experience_apply_received', 'system')
                              not null comment '기업 알림 유형',
    notification_title        varchar(255) not null comment '제목',
    notification_content      text comment '내용',
    notification_related_type varchar(255) comment '연관 타입',
    notification_related_id   bigint unsigned comment '연관 대상 ID',
    notification_is_read      boolean  default false comment '읽음 여부',
    notification_receive_at   datetime default current_timestamp comment '수신 시각',
    created_datetime          datetime default current_timestamp,
    updated_datetime          datetime default current_timestamp on update current_timestamp,
    constraint fk_corp_notification_corp foreign key (corp_id) references tbl_corp (id)
) comment '기업 헤더 알림';

