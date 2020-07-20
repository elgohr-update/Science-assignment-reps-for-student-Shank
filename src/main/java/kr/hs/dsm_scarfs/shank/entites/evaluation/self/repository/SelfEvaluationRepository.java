package kr.hs.dsm_scarfs.shank.entites.evaluation.self.repository;

import kr.hs.dsm_scarfs.shank.entites.evaluation.self.SelfEvaluation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SelfEvaluationRepository extends CrudRepository<SelfEvaluation, Integer> {
    Optional<SelfEvaluation> findByHomeworkIdAndStudentId(Integer homeworkId, Integer studentId);
    boolean existsByHomeworkIdAndStudentId(Integer homeworkId, Integer studentId);
}
