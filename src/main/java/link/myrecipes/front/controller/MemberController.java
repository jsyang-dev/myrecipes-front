package link.myrecipes.front.controller;

import link.myrecipes.front.dto.User;
import link.myrecipes.front.dto.request.UserRequest;
import link.myrecipes.front.dto.security.UserSecurity;
import link.myrecipes.front.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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

        User user = this.memberService.createMember(userRequest);
        log.info("joined user = " + user.toString());
        return "redirect:/";
    }

    @GetMapping("/modify")
    public String modify(Model model, @AuthenticationPrincipal UserSecurity userSecurity) {
        int loginUserId = Optional.ofNullable(userSecurity).map(UserSecurity::getId).orElse(0);
        User user = this.memberService.readMember(loginUserId);
        model.addAttribute("userRequest", user.toRequestDTO());
        model.addAttribute("userId", user.getId());
        return "member/modify";
    }

    @PostMapping("/modify/{id}")
    public String modifyProcess(@PathVariable int id, @ModelAttribute @Valid UserRequest userRequest,
                                @AuthenticationPrincipal UserSecurity userSecurity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/modify";
        }

        int loginUserId = Optional.ofNullable(userSecurity).map(UserSecurity::getId).orElse(0);
        User user = this.memberService.updateMember(id, userRequest, loginUserId);
        log.info("updated user = " + user.toString());
        return "redirect:/";
    }
}
