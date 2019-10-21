package link.myrecipes.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {
    @GetMapping("/health")
    public String health(Model model) {
        model.addAttribute("title", "Hello System");
        return "health";
    }
}