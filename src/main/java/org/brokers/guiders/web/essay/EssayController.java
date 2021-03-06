package org.brokers.guiders.web.essay;

import lombok.RequiredArgsConstructor;
import org.brokers.guiders.config.security.AuthUser;
import org.brokers.guiders.web.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/essay")
@RequiredArgsConstructor
public class EssayController {

    private final EssayService essayService;

    @GetMapping("/write")
    public String writeEssay() {
        return "essay/write";
    }

    @PostMapping("/write")
    public String writeEssay(EssayDto.Request request, @AuthUser Member member) {
        Long id = essayService.writeEssay(request, member);
        return "redirect:/essay/detail/" + id;
    }

    @GetMapping("/list")
    public String goEssayListPage(Model model,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "") String keyword) {
        Page<EssayDto.Response> essayPage = essayService.getEssayList(page - 1, keyword);
        model.addAttribute("essayPage", essayPage);
        return "essay/list";
    }

    @GetMapping("/detail/{id}")
    public String readEssay(@PathVariable Long id, Model model, @AuthUser Member member) {
        EssayDto.DetailResponse essay = essayService.getEssay(id);
        if (member != null) {
            boolean confirmLike = member.getLikeEssayList().stream().anyMatch((likeEssay) -> likeEssay.getId().equals(id));
            model.addAttribute("userInfo", member);
            model.addAttribute("confirmLike", confirmLike);
        }
        model.addAttribute("essay", essay);
        return "essay/post";
    }

    @GetMapping("/modifyForm/{id}")
    public String goModifyForm(@PathVariable Long id) {
        return "essay/modify";
    }

    @PostMapping("/modify/{id}")
    public String modifyEssay(@PathVariable Long id, EssayDto.Request request, @AuthUser Member member) {
        essayService.modifyEssay(id, request, member);
        return "redirect:/essay/detail/" + id;
    }

    @GetMapping("/delete/{id}")
    public String removeEssay(@PathVariable Long id, @AuthUser Member member) {
        essayService.removeEssay(id, member);
        return "essay/list";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEssay(@PathVariable Long id) {
        EssayDto.DetailResponse essay = essayService.getEssay(id);
        return ResponseEntity.ok(essay);
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<?> addLikeCount(@PathVariable Long id, @AuthUser Member member) {
        return ResponseEntity.ok(essayService.toggleLikeEssay(id, member));
    }

}
