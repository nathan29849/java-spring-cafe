package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import com.kakao.cafe.domain.dto.ArticleForm;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);

    Optional<Article> findByIndex(Long id);

    List<Article> findAll();

    void update(Long id, ArticleForm articleForm);
}
