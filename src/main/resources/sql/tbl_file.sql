-- ============================================================

create table tbl_file
(
    id               bigint unsigned auto_increment primary key comment '파일 ID',
    file_target_type varchar(50) not null comment '파일 대상 유형',
    file_target_id   bigint unsigned not null comment '파일 대상 ID',
    file_name        varchar(255) not null comment '파일명',
    file_path        varchar(500) not null comment '파일 경로',
    file_size        int comment '파일 크기 (byte)',
    created_datetime datetime default current_timestamp,
    updated_datetime datetime default current_timestamp
) comment '파일 정보 [1차 정규화]';


