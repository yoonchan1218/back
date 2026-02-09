create table tbl_address
(
    id               bigint unsigned auto_increment primary key comment '주소 ID',
    address_zipcode  varchar(10) not null comment '우편번호',
    address_province varchar(50) comment '시/도 (Province)',
    address_city     varchar(50) comment '시/군/구 (City)',
    address_district varchar(100) comment '읍/면/동 (District)',
    address_detail   varchar(255) comment '상세주소',
    created_datetime datetime default current_timestamp,
    updated_datetime datetime default current_timestamp on update current_timestamp
) comment '주소';



