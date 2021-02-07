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

    @PostMapping("/api/add")
    public String addNewSubmission(@RequestParam String artist, @RequestParam String title, @RequestParam String url) {
        Submission n = new Submission();
        n.setArtist(artist);
        n.setTitle(title);
        n.setUrl(url);
        submissionRepository.save(n);
        return "Saved";
    }

    @GetMapping("/api/delete_all")
    public void deleteAll(){
        submissionRepository.deleteAll();
        System.out.print(submissionRepository.findAll());
    }
}
