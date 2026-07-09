CREATE TABLE question (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    statement TEXT NOT NULL,

    correct_answer VARCHAR(10),

    user_answer VARCHAR(10),

    subject VARCHAR(100),

    created_at TIMESTAMP,

    status VARCHAR(30)

);