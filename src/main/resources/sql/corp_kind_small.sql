create table tbl_corp_kind_small(
    id bigint unsigned auto_increment primary key,
    corp_kind_parent_id  bigint unsigned,
    corp_kind_small_name    varchar(255) not null,
    corp_kind_small_checked boolean default false,
    foreign key (corp_kind_parent_id) references tbl_corp_kind (id) on delete cascade
);
