package com.kakao.cafe.controller;

import com.kakao.cafe.domain.dto.ArticleForm;
import com.kakao.cafe.domain.dto.LoginForm;
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
            return "user/login";
        }
        ArticleForm articleForm = articleService.findOneArticle(index);
        model.addAttribute("article", articleForm);
        return "qna/show";
    }

    @GetMapping("/articles/{id}/update")
    public String createUpdateForm(@PathVariable("id") int id, Model model, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        ArticleForm articleForm = articleService.findOneArticle(id);

        model.addAttribute("article", articleForm);
        if (value == null) {
            return "qna/show";
        }

        articleService.validateArticle(id, (LoginForm)value);
        return "qna/updateForm";
    }

    @PutMapping("/articles/{id}/update")
    public String updateArticle(@PathVariable("id") int id, Model model, HttpSession session, @Valid ArticleForm updateForm) {
        Object value = session.getAttribute("sessionedUser");
        if (value == null) {
            return "index";
        }
        articleService.update(id, updateForm);
        ArticleForm articleForm = articleService.findOneArticle(id);
        model.addAttribute("article", articleForm);
        return "qna/show";
    }

}
