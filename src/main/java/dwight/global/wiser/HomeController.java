package dwight.global.wiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    public SubmissionRepository submissionRepository;

    @GetMapping("/")
    public String home(@RequestParam(name="name", defaultValue = "daniel", required = false) String name, Map<String, Object> model) {
        ArrayList<Submission> submissions = (ArrayList) submissionRepository.findAll();
        model.put("submissions",submissionRepository.findAll());
        for (int i = 0; i < submissions.size(); i++) {
            Submission s = submissions.get(i);
            model.put("artist", s.getArtist());
            model.put("title", s.getTitle());
            model.put("url", s.getUrl());
        }
        return "home";
    }

    @GetMapping("/api/all")
    public ArrayList<Submission> testDB(){
        return (ArrayList) submissionRepository.findAll();
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
