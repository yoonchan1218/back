CREATE TABLE tbl_skill_log
(
    id                      BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    individual_member_id    BIGINT UNSIGNED NOT NULL,
    experience_program_id   BIGINT UNSIGNED NOT NULL,
    skill_log_comment_id    BIGINT UNSIGNED,
    skill_log_title         VARCHAR(255)    NOT NULL,
    skill_log_content       text           NOT NULL,
    skill_log_tags          VARCHAR(255),
    skill_log_thumbnail_url VARCHAR(255),
    skill_log_images        JSON,
    skill_log_attachments   JSON,
    skill_log_view_count    INT       DEFAULT 0,
    skill_log_like_count    INT       DEFAULT 0,
    skill_log_comment_count INT       DEFAULT 0,
    skill_log_scrap_count   INT       DEFAULT 0,
    skill_log_created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    skill_log_updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_skill_log_member
        FOREIGN KEY (individual_member_id) REFERENCES tbl_individual_member (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    FOREIGN KEY (experience_program_id) REFERENCES experience_program (experience_program_id)
);

