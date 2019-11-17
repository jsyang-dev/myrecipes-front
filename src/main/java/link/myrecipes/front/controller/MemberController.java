package link.myrecipes.front.controller;

import link.myrecipes.front.dto.User;
import link.myrecipes.front.dto.request.UserRequest;
import link.myrecipes.front.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "member/register";
    }

    @PostMapping("/register")
    public String registerProcess(@ModelAttribute @Valid UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/register";
        }

        User user = memberService.createMember(userRequest);
        log.info("joined user = " + user.toString());
        return "redirect:/";
    }

    @GetMapping("/modify")
    public String modify(Model model) {
        User user = this.memberService.readMember();
        model.addAttribute("userRequest", user.toRequestDTO());
        model.addAttribute("userId", user.getId());
        return "member/modify";
    }

    @PostMapping("/modify/{id}")
    public String modifyProcess(@PathVariable int id, @ModelAttribute @Valid UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/modify";
        }

        User user = memberService.updateMember(id, userRequest);
        log.info("updated user = " + user.toString());
        return "redirect:/";
    }
}