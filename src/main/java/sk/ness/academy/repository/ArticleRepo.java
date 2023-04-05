package sk.ness.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;

import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Integer> {
    @Query(value = "SELECT author AS authorName, count(author) AS articleCount FROM articles GROUP BY author", nativeQuery = true)
    List<Object[]> findAllStats();

    @Query(value = "SELECT * FROM Articles a WHERE a.title LIKE %?1%"
            + " OR a.text LIKE %?1%"
            + " OR a.author LIKE %?1%", nativeQuery = true)
    public List<Article> search(String searchText);

    List<Article> findByTitleContainingOrTextContainingOrAuthorContainingAllIgnoreCase(String searchText, String searchText1, String searchText2);

}
