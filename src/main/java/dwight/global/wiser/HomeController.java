package dwight.global.wiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @Autowired
    public SubmissionRepository submissionRepository;

    @GetMapping("/")
    public @ResponseBody Iterable<Submission> home() {
        return submissionRepository.findAll();
    }

    @PostMapping("/admin/add")
    public @ResponseBody String addNewSubmission(@RequestParam String artist, @RequestParam String title) {
        Submission n = new Submission();
        n.setArtist(artist);
        n.setTitle(title);
        submissionRepository.save(n);
        return "Saved";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
