package dwight.global.wiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    public SubmissionRepository submissionRepository;

    @GetMapping("/")
    public String home(@RequestParam(name="name", defaultValue = "name", required = false) String name, Map<String, Object> model) {
        model.put("name", name);
        return "home";
        //return submissionRepository.findAll();
    }

    @PostMapping("/admin/add")
    public @ResponseBody
    String addNewSubmission(@RequestParam String artist, @RequestParam String title) {
        Submission n = new Submission();
        n.setArtist(artist);
        n.setTitle(title);
        submissionRepository.save(n);
        return "Saved";
    }
}
