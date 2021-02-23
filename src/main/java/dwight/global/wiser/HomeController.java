package dwight.global.wiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class HomeController {
    public SubmissionRepository submissionRepository;
    private AmazonClient amazonClient;

    @Autowired
    HomeController(AmazonClient amazonClient, SubmissionRepository submissionRepository) {
        this.amazonClient = amazonClient;
        this.submissionRepository = submissionRepository;
    }

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("submissions", submissionRepository.findAllByApprovedIsTrue());
        return "home";
    }

    @PostMapping("/submit")
    public RedirectView receiveSubmission(@RequestParam(value = "title") String title, @RequestParam(value ="name") String name, @RequestParam(value = "subheading") String subheading, @RequestParam(value="content") String content, RedirectAttributes attributes) {
        Submission n = new Submission();
        n.setApproved(false);
        n.setSubheading(subheading);
        n.setContent(content);
        n.setUrl("../public/img/bg-masthead.jpg");
        n.setTitle(title);
        n.setArtist(name);
        submissionRepository.save(n);
        attributes.addFlashAttribute("id",n.getId());
        return new RedirectView("/image");
    }

    @PostMapping("/imageupload")
    @ResponseBody
    public RedirectView saveUpdateImage(@RequestParam(value = "image") MultipartFile image, @RequestParam(value="id") int id, RedirectAttributes attributes) {
        String url = this.amazonClient.uploadFile(image);
        Submission sub = submissionRepository.findById(id).orElseThrow(() -> new SubmissionNotFoundException(id));
        sub.setUrl(url);
        submissionRepository.save(sub);
        attributes.addFlashAttribute("message", "Your submission has been sent. It will be reviewed by editors and, once approved, appear on the home page");
        return new RedirectView("/");
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
    @GetMapping("/upload")
    public String uploadSubmission() {
        return "upload";
    }

    @GetMapping("/image")
    public String uploadImage(Model model) {
        return "imageupload";

    }
    @PostMapping("/api/contact")
    public String sendEmail(@RequestParam(name="name") String name, @RequestParam(name="email") String email, @RequestParam(name="phone") String phone, @RequestParam(name="msg") String message) {
        // This doesn't do anything yet
        return "Approved";
    }


}
