package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.dto.ArticleForm;
import com.kakao.cafe.domain.dto.LoginForm;
import com.kakao.cafe.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void post(ArticleForm articleForm) {
        Article article = articleForm.createArticle();
        articleRepository.save(article);
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public ArticleForm findOneArticle(Long id) {
        Article article = articleRepository.findByIndex(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        ArticleForm articleForm = new ArticleForm(article.getTitle(), article.getWriter(), article.getContents(), article.getDateTime());
        articleForm.setId(article.getId());
        return articleForm;
    }


    public void validateArticle(Long id, LoginForm value) {
        ArticleForm articleForm = findOneArticle(id);
        System.out.println("articleForm.getWriter() = " + articleForm.getWriter());
        System.out.println("value.getName() = " + value.getName());
        if (articleForm.getWriter().equals(value.getName())) {
            return;
        }
        throw new IllegalStateException("유저의 게시물이 아닙니다.");
    }


    public void update(Long id, ArticleForm updateForm) {
        articleRepository.update(id, updateForm);
    }
}
