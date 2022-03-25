package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import com.kakao.cafe.domain.dto.ArticleForm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MemoryArticleRepository implements ArticleRepository{
    private final List<Article> articleStore = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Article save(Article article) {
        for (int i = 0; i < articleStore.size(); i++) {
            if (articleStore.get(i) == null) {
                return store(article, (long)i);
            }
        }
        return store(article, (long) articleStore.size());
    }

    private Article store(Article article, Long id) {
        article.setId(id);
        articleStore.add(article);
        return article;
    }

    @Override
    public Optional<Article> findByIndex(Long id) throws IndexOutOfBoundsException{
        return Optional.ofNullable(articleStore.get(Math.toIntExact(id)));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articleStore);
    }

    public void clearStore() {
        articleStore.clear();
    }

    public int size() {
        return articleStore.size();
    }

    @Override
    public void update(Long id, ArticleForm articleForm) {
        return;
    }
}
