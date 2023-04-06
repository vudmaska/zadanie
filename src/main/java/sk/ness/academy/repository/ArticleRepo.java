package sk.ness.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.AuthorStats;

import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Integer> {
    @Query("select new sk.ness.academy.dto.AuthorStats(a.author as authorName,count(a.author) as articleCount) from Article a group by a.author")
    List<AuthorStats> findAllStats();

    List<Article> findByTitleContainingOrTextContainingOrAuthorContainingAllIgnoreCase(String searchText, String searchText1, String searchText2);

    @Query("SELECT new sk.ness.academy.domain.Article(a.id, a.title, a.text, a.author, a.createTimestamp) from Article a")
    List<Article> findAll();



   // @Query("SELECT new sk.ness.academy.domain.Article(a.id, a.title, a.text, a.author) FROM Article a")
   // List<Article> findAllProjectedBy();



}
