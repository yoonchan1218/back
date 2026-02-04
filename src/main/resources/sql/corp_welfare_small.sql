create table tbl_corp_welfare_small
(
    id                     bigint unsigned auto_increment primary key,
    corp_welfare_parent_id bigint unsigned,
    corp_welfare_name      varchar(255) not null,
    corp_welfare_checked   boolean default false,
    foreign key (corp_welfare_parent_id) references tbl_corp_welfare (id)
);