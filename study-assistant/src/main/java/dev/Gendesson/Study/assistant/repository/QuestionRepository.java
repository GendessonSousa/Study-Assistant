package dev.Gendesson.Study.assistant.repository;

import dev.Gendesson.Study.assistant.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
