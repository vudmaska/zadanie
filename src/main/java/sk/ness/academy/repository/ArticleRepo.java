package sk.ness.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Integer> {
}
