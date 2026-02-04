create table tbl_individual_member(
    id bigint unsigned auto_increment primary key,
    individual_member_name varchar(255) not null,
    individual_member_id varchar(255) not null unique,
    individual_member_password varchar(255) not null,
    individual_member_birth date not null,
    individual_member_gender enum('MAN','WOMEN') default 'MAN',
    individual_member_email varchar(255) not null unique,
    individual_member_phone varchar(255) not null unique,
    individual_member_address varchar(255),
    individual_member_address_detail varchar(255),
    individual_member_zipcode varchar(255),
    individual_member_education varchar(255),
    individual_member_point int default 0,
    individual_member_level int default 1,
    individual_member_post_count int default 0,
    individual_member_question_count int default 0,
    individual_member_stauts enum('ACTIVE', 'INACTIVE') default 'ACTIVE',
    individual_member_agree_privacy BOOLEAN DEFAULT FALSE,
    individual_member_agree_marketing BOOLEAN DEFAULT FALSE,
    individual_member_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

