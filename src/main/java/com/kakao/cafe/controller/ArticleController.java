package com.kakao.cafe.controller;

import com.kakao.cafe.domain.dto.ArticleForm;
import com.kakao.cafe.domain.dto.LoginedUser;
import com.kakao.cafe.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private Logger log = LoggerFactory.getLogger(ArticleController.class);

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/questions")
    public String quest(HttpSession session) {
        Object value = session.getAttribute("SessionedUser");
        if (value == null){
            return "redirect:/login";
        }
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuest(@Valid ArticleForm articleForm) {
        System.out.println("createQuest()");
        articleService.post(articleForm);
        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String detail(@PathVariable("index") Long id, Model model, HttpSession session) {
        if (session.getAttribute("SessionedUser") == null){
            return "user/login";
        }
        ArticleForm articleForm = articleService.findOneArticle(id);
        model.addAttribute("article", articleForm);
        return "qna/show";
    }

    @GetMapping("/articles/{id}/update")
    public String createUpdateForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        Object value = session.getAttribute("SessionedUser");
        ArticleForm articleForm = articleService.findOneArticle(id);

        model.addAttribute("article", articleForm);
        if (value == null) {
            return "qna/show";
        }

        articleService.validateArticle(id, (LoginedUser)value);
        return "qna/updateForm";
    }

    @PutMapping("/articles/{id}/update")
    public String updateArticle(@PathVariable("id") Long id, HttpSession session, @Valid ArticleForm updateForm) {
        Object value = session.getAttribute("SessionedUser");
        if (value == null) {
            return "index";
        }
        articleService.update(id, updateForm);
        return "redirect:/articles/{id}";
    }
}
