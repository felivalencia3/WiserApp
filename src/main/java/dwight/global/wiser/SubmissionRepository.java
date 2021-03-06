package dwight.global.wiser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SubmissionRepository extends CrudRepository<Submission, Integer> {
    Submission findByTitle(String title);
    Submission deleteSubmissionById(int id);
    Iterable<Submission> findAllByApprovedIsTrue();
    Iterable<Submission> findAllByApprovedIsFalse();
}
