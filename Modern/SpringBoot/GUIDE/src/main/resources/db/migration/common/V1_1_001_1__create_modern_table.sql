CREATE TABLE modern_table
(
    `id`               int       NOT NULL AUTO_INCREMENT COMMENT '필드 ID',
    `created_by`       int       NOT NULL COMMENT '최초생성자 ID',
    `created_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초생성일시',
    `updated_by`       int       NOT NULL COMMENT '최종수정자 ID',
    `updated_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종수정일시',
    PRIMARY KEY (id)
)