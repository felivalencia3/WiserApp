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

    @PostMapping("/api/delete/{id}")
    @ApiOperation(value="Deletes an existing submission.")
    public String deleteSubmission(@PathVariable int id) {
        submissionRepository.deleteById(id);
        return "Deleted submission: " + id;
    }

    @PostMapping("/api/update")
    @ApiOperation(value="Updates an existing submission's URL.")
    public Submission updateSubmissionURL(@PathVariable int id, @RequestParam String newUrl) {
        Submission optionalSub = submissionRepository.findById(id).orElseThrow(() -> new SubmissionNotFoundException(id));
        optionalSub.setUrl(newUrl);
        return submissionRepository.findById(id).orElseThrow(() -> new SubmissionNotFoundException(id));
    }
    @GetMapping("/api/submission/{title}")
    @ApiOperation(value="Gets a submission after searching for its title.")
    public Submission getByTitle(@PathVariable String title) {
        return submissionRepository.findByTitle(title);
    }
    @GetMapping("/api/approve")
    @ApiOperation(value="Gets all of the currently approved submissions.")
    public Iterable<Submission> getApproved() {
        return submissionRepository.findAllByApprovedIsTrue();
    }

    @GetMapping("/api/id/{title}")
    @ApiOperation(value="Gets a submission's ID by searching for its title")
    public Iterable<Submission> getIdByTitle(@PathVariable String title) {
        return submissionRepository.findAllByTitle(title);
    }
    @PostMapping("/api/approve")
    @ApiOperation(value="Approves a post through its ID.")
    public String approvePost(@RequestParam Integer id) {
        submissionRepository.findById(id).orElseThrow(() -> new SubmissionNotFoundException(id)).setApproved(true);
        return "Approved.";
    }
}
