package com.kakao.cafe.controller;

import com.kakao.cafe.domain.dto.ArticleForm;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/questions")
    public String quest(HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value == null){
            return "redirect:/login";
        }
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuest(@Valid ArticleForm articleForm) {
        articleService.post(articleForm);
        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String detail(@PathVariable("index") int index, Model model, HttpSession session) {
        if (session.getAttribute("sessionedUser") == null){
            return "redirect:/login";
        }
        ArticleForm articleForm = articleService.findOneArticle(index);
        model.addAttribute("article", articleForm);
        return "qna/show";
    }

    @GetMapping("/articles/{index}/update")
    public String update(@PathVariable("index") int index, Model model, HttpSession session) {
        Object value  = session.getAttribute("sessionedUser");
        if (value != null) {
            ArticleForm articleForm = articleService.validateArticle(index, value);
            model.addAttribute("article", articleForm);
            return "redirect:";
        }
        return "redirect:/articles/{index}";
    }

}
