package io.myrecipes.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthController {
    @GetMapping("/health")
    public String health(Model model) {
        model.addAttribute("title", "Hello System");
        return "health";
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }
}