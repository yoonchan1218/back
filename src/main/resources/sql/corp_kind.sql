create table tbl_corp_kind
(
    id                bigint unsigned auto_increment primary key,
    corp_kind_name    varchar(255) not null,
    corp_kind_checked boolean default false
);