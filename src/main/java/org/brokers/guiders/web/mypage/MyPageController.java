package org.brokers.guiders.web.mypage;

import lombok.RequiredArgsConstructor;
import org.brokers.guiders.config.security.UserCustom;
import org.brokers.guiders.web.essay.Essay;
import org.brokers.guiders.web.member.Guider;
import org.brokers.guiders.web.member.MemberService;
import org.brokers.guiders.web.mentoring.MentoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final MentoringService mentoringService;
    private final MemberService memberService;

    @GetMapping("/likeEssay")
    public String likeEssay(Authentication authentication, Model model) {
        List<Essay> list;
        if (authentication.isAuthenticated()) {
            UserCustom user = (UserCustom) authentication.getPrincipal();
            list = myPageService.getMyLikeEssay(user.getEmail());
            model.addAttribute("essayList", list);
        }
        return "mypage/likeEssay";
    }

    @GetMapping("/myGuider")
    public String myGuiders() {
        return "mypage/myGuider";
    }

    @GetMapping("myGuiders")
    public @ResponseBody
    ResponseEntity<List<Map<String, Object>>> myGuiderList(Authentication authentication) {
        List<Map<String, Object>> list = null;
        if (authentication.isAuthenticated()) {
            UserCustom user = (UserCustom) authentication.getPrincipal();
            list = myPageService.getMyGuiderList(user.getEmail());
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/likeEssay/{eno}")
    @ResponseBody
    public String getEssay(@PathVariable("eno") String eno) {
        return myPageService.getEssayContent(Integer.parseInt(eno));
    }

    @GetMapping("/guider/{email}")
    @ResponseBody
    public ResponseEntity<Guider> guider(@PathVariable String email) {
        return ResponseEntity.ok(memberService.selectByEmail(email, "guider"));
    }

    @GetMapping("/questions")
    public String questions(Authentication authentication, Model model) {
        List<Map<String, Object>> mentorings = null;
        if (authentication != null) {
            UserCustom user = (UserCustom) authentication.getPrincipal();
            mentorings = mentoringService.getMyQuestions(user.getEmail());
        }
        model.addAttribute("mentorings", mentorings);
        return "mypage/questions";
    }

    @GetMapping("/edit")
    public String edit(Model model, HttpSession session, Authentication authentication) {
        if (authentication != null) {
            UserCustom user = (UserCustom) authentication.getPrincipal();
            String type = "guider";
            if (user.getAuthorities().toString().equals("[ROLE_MEMBER]")) {
                type = "member";
            }
            Guider vo = memberService.selectByEmail(user.getEmail(), type);
            model.addAttribute("vo", vo);
        }

        return "mypage/edit";
    }

    @PostMapping("/edit")
    public String edit(Guider vo, Authentication authentication) {
        if (vo != null && authentication != null) {
            UserCustom user = (UserCustom) authentication.getPrincipal();
            vo.setPassword(vo.getPassword().trim());
            vo.setEmail(user.getEmail());

            memberService.modifyMember(vo);
        }
        return "redirect:/mypage/edit";
    }

}