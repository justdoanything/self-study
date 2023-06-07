CREATE TABLE search_keyword_history
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    keyword          VARCHAR(1000) NOT NULL,
    created_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)