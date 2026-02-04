CREATE TABLE tbl_skill_log_comment
(
    id                        BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    skill_log_id              BIGINT UNSIGNED NOT NULL,
    individual_member_id      BIGINT UNSIGNED NOT NULL,
    skill_log_comment_parent  BIGINT UNSIGNED,
    skill_log_comment_content TEXT            NOT NULL,
    skill_log_comment_images  JSON,
    skill_log_like_count      INT       DEFAULT 0,
    created_at                TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at                TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (skill_log_id)
        REFERENCES tbl_skill_log (id) ON DELETE CASCADE,
    FOREIGN KEY (individual_member_id)
        REFERENCES tbl_individual_member (id) ON DELETE CASCADE,
    FOREIGN KEY (skill_log_comment_parent)
        REFERENCES tbl_skill_log_comment (id) ON DELETE CASCADE
);