package dwight.global.wiser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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


}
