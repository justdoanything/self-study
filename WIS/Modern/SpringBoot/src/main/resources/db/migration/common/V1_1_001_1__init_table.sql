CREATE TABLE search_keyword_history
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    keyword          TEXT NOT NULL,
    created_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX            keyword_history_index (keyword)
)