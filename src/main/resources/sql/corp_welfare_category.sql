create table tbl_corp_welfare
(
    id                   bigint unsigned auto_increment primary key,
    corp_welfare_name    varchar(255) not null,
    corp_welfare_checked boolean default false
);