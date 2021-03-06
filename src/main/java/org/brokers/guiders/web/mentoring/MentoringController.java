package org.brokers.guiders.web.mentoring;

import lombok.RequiredArgsConstructor;
import org.brokers.guiders.config.security.AuthUser;
import org.brokers.guiders.web.member.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mentoring")
@RequiredArgsConstructor
public class MentoringController {

    private final MentoringService mentoringService;

    @PostMapping("/answer")
    public String answer(MentoringDto.AnswerRequest request) {
        mentoringService.registerAnswer(request);
        return "redirect:/mentoring/" + request.getId();
    }

    @GetMapping("/{id}")
    public String questionAndAnswer(@PathVariable Long id, Model model) {
        MentoringDto.Response mentoring = mentoringService.getMentoring(id);
        model.addAttribute("mentoring", mentoring);
        return "mypage/mentoring";
    }

    @GetMapping("/list")
    public String mentoringList(@AuthUser Member member, Long guiderId, Model model) {
        MentoringDto.ListResponse mentoring = mentoringService.getMentoringList(member, guiderId);
        model.addAttribute("mentoringInfo", mentoring);
        return "mypage/mentoringList";
    }

    @PostMapping
    public ResponseEntity<?> question(@RequestBody MentoringDto.Request request,
                                            @AuthUser Member member) {
        mentoringService.question(request, member);
        return ResponseEntity.ok().build();
    }
}
