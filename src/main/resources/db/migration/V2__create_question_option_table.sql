CREATE TABLE question_option (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    letter VARCHAR(1) NOT NULL,

    text TEXT NOT NULL,

    question_id BIGINT NOT NULL,

    CONSTRAINT fk_question_option
        FOREIGN KEY(question_id)
        REFERENCES question(id)

);