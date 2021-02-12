package dwight.global.wiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    public SubmissionRepository submissionRepository;

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        ArrayList<Submission> submissions = (ArrayList) submissionRepository.findAll();
        model.put("submissions", submissionRepository.findAll());
        return "home";
    }

    @GetMapping("/post/{title}")
    public String getSubmission(Map<String, Object> model, @PathVariable String title) {
        Submission s = submissionRepository.findByTitle(title);
        model.put("title", s.getTitle());
        model.put("artist", s.getArtist());
        model.put("subheading", s.getSubheading());
        model.put("url", s.getUrl());
        model.put("content", s.getContent());
        return "post";
    }
    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }
    @GetMapping("/issues")
    public String issueList() {
        return "issues";
    }
}
