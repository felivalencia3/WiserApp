package dwight.global.wiser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    Submission findByTitle(String title);

}
