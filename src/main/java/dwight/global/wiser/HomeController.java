package dwight.global.wiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
