package sk.ness.academy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import sk.ness.academy.config.TestDatabaseConfig;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.ArticleHibernateDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exceptions.NoContentException;
import sk.ness.academy.exceptions.ResourceNotFoundException;
import sk.ness.academy.service.ArticleServiceImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, ArticleHibernateDAO.class})
@Transactional
@Sql({"/initdb.sql"})
public class ArticleHibernateDAOTest {
    @Autowired
    ArticleDAO articleDAO;

    @Test
    void testFindById(){
        final Article article = articleDAO.findByID(1);
        Assertions.assertEquals(1, article.getId());
        Assertions.assertEquals("Author", article.getAuthor());
        Assertions.assertEquals("Title 1", article.getTitle());
        Assertions.assertEquals("Text 1", article.getText());
    }

    @Test
    void testFindByIdException() {
        Assertions.assertThrowsExactly(ResourceNotFoundException.class, () -> articleDAO.findByID(22));

    }

    @Test
    void testSearchArticles(){
        List<Article> foundArticles = articleDAO.searchArticles("Dominik");
        Assertions.assertEquals(1, foundArticles.size());
    }

    @Test
    void testSearchArticlesException() {
        Assertions.assertThrowsExactly(NoContentException.class, () -> articleDAO.searchArticles("random"));
    }

    @Test
    void testFindAll() {
        List<Article> foundArticles = articleDAO.findAll();

        Assertions.assertEquals(5, foundArticles.size());
    }

}
