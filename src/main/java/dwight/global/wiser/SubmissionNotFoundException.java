package dwight.global.wiser;

public class SubmissionNotFoundException extends RuntimeException {
    public SubmissionNotFoundException(int id) {
        super("Could not find Submission #"+id);
    }
}
