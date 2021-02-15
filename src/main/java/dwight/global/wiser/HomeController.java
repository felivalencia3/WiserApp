package dwight.global.wiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    public SubmissionRepository submissionRepository;

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        ArrayList<Submission> submissions = (ArrayList) submissionRepository.findAll();
        model.put("submissions", submissionRepository.findAllByApprovedIsTrue());
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

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/auth")
    public String redirectLogIn() {
        return "login";
    }

    @PostMapping("/auth")
    public String processLogIn(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        if (username.equals("harambee_staff") && password.equals("dwight_global")) {
            return "redirect: /swagger-ui.html";
        } else {
            return "loginerror";
        }
    }
}
