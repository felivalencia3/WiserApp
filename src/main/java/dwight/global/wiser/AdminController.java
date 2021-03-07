package dwight.global.wiser;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(value="Editors Dashboard", description = "For Editors and Staff. Operations pertaining to submission Upload and Approval")
public class AdminController {
    @Autowired
    SubmissionRepository submissionRepository;

    @GetMapping("/api/all")
    @ApiOperation(value="Returns all submissions, approved or not.")
    public Iterable<Submission> getAll() {
        return submissionRepository.findAll();
    }

    @PostMapping("/api/approve/{title}")
    @ApiOperation(value="Approves a Submission from its title")
    public String approveByTitle(@PathVariable String title) {
        Submission sub = this.submissionRepository.findByTitle(title);
        boolean opposite = true;
        if(sub.isApproved()==true) {
            opposite = false;
        }
        sub.setApproved(opposite);
        submissionRepository.save(sub);
        return "Approved";
    }

    @GetMapping("/api/notapproved")
    @ApiOperation(value="Returns submissions that have not yet been approved")
    public Iterable<Submission> notApproved() {
        return submissionRepository.findAllByApprovedIsFalse();
    }

    @PostMapping("/api/add")
    @ApiOperation(value="Adds a submission.")
    public String addNewSubmission(@RequestParam String artist, @RequestParam String title, @RequestParam String url,
    @RequestParam String subheading, @RequestParam String content) {
        Submission n = new Submission();
        n.setArtist(artist);
        n.setTitle(title);
        n.setUrl(url);
        n.setContent(content);
        n.setSubheading(subheading);
        submissionRepository.save(n);
        return "Saved";
    }

    @PutMapping("/api/update/{id}")
    @ApiOperation(value="Updates an existing submission's URL.")
    public Submission updateSubmissionURL(@PathVariable int id, @RequestParam String newUrl) {
        Submission optionalSub = submissionRepository.findById(id).orElseThrow(() -> new SubmissionNotFoundException(id));
        optionalSub.setUrl(newUrl);
        submissionRepository.save(optionalSub);
        return submissionRepository.findById(id).orElseThrow(() -> new SubmissionNotFoundException(id));
    }
    @GetMapping("/api/submission/{title}")
    @ApiOperation(value="Gets a submission from its title.")
    public Submission getByTitle(@PathVariable String title) {
        return submissionRepository.findByTitle(title);
    }

    @GetMapping("/api/approve")
    @ApiOperation(value="Gets all of the currently approved submissions.")
    public Iterable<Submission> getApproved() {
        return submissionRepository.findAllByApprovedIsTrue();
    }

    @PostMapping("/api/approve")
    @ApiOperation(value="Approves a post through its ID.")
    public String approvePost(@RequestParam Integer id) {
        Submission approved = submissionRepository.findById(id).orElseThrow(() -> new SubmissionNotFoundException(id));
        approved.setApproved(true);
        submissionRepository.save(approved);
        return "Approved.";
    }

}
