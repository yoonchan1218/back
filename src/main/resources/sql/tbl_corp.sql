create table tbl_corp
(
    id                               bigint unsigned primary key comment '기업 ID (tbl_member.id)',
    corp_company_name                varchar(255) not null comment '기업명',
    corp_business_number             varchar(255)  not null unique comment '사업자등록번호',
    corp_ceo_name                    varchar(255) comment '대표자명',
    corp_kind_id                     bigint unsigned comment '업종 대카테고리',
    corp_kind_small_id               bigint unsigned comment '업종 소카테고리',
    corp_company_type                varchar(255) comment '기업형태',
    corp_company_size                varchar(255) comment '기업규모',
    corp_establishment_date          date comment '설립일',
    corp_website_url                 varchar(255) comment '홈페이지 URL',
    corp_fax                         varchar(255) comment 'fax 번호',
    corp_capital                     bigint comment '자본금 (원 단위)',
    corp_total_sales                 bigint comment '매출액 (원 단위)',
    corp_performance                 text comment '연혁 및 실적',
    corp_vision                      text comment '개요 및 비전',
    constraint fk_corp_member foreign key (id) references tbl_member (id)
) comment '매니저 && 기업';

select * from tbl_corp;
select * from tbl_address;






