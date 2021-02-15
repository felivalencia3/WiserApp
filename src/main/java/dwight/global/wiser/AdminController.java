package dwight.global.wiser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    SubmissionRepository submissionRepository;

    @GetMapping("/api/all")
    public Iterable<Submission> getAll() {
        return submissionRepository.findAll();
    }

    @PostMapping("/api/contact")
    public String sendEmail(@RequestParam(name="name") String name, @RequestParam(name="email") String email, @RequestParam(name="phone") String phone, @RequestParam(name="msg") String message) {
        System.out.println("name = " + name);
        System.out.println("email = " + email);
        System.out.println("phone = " + phone);
        System.out.println("message = " + message);
        return "Approved";
    }

    @PostMapping("/api/add")
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
    public String deleteSubmission(@PathVariable int id) {
        submissionRepository.deleteById(id);
        return "Deleted submission: " + id;
    }

    @PostMapping("/api/update")
    public Submission updateSubmissionURL(@PathVariable int id, @RequestParam String newUrl) {
        Submission optionalSub = submissionRepository.findById(id).orElseThrow(() -> new SubmissionNotFoundException(id));
        optionalSub.setUrl(newUrl);
        return submissionRepository.findById(id).orElseThrow(() -> new SubmissionNotFoundException(id));
    }
    @GetMapping("/api/submission/{title}")
    public Submission getByTitle(@PathVariable String title) {
        return submissionRepository.findByTitle(title);
    }
    @GetMapping("/api/approved")
    public Iterable<Submission> getApproved() {
        return submissionRepository.findAllByApprovedIsTrue();
    }
}
