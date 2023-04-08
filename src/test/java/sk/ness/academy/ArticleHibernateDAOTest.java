package sk.ness.academy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sk.ness.academy.config.TestDatabaseConfig;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.ArticleHibernateDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.exceptions.NoContentException;
import sk.ness.academy.service.ArticleServiceImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, ArticleHibernateDAO.class})
@Transactional
public class ArticleHibernateDAOTest {

    @Mock
    private ArticleDAO articleDAO;

    List<Article> mockedList;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private List<Article> articles;
    private Article article;

    @BeforeEach
    private void init() {
        final Article article1 = new Article();
        article1.setAuthor("Author");
        article1.setTitle("Title");
        article1.setText("search");
        article1.setId(1);
        //article1.setCreateTimestamp(new Date());

        final Article article2 = new Article();
        article2.setAuthor("Test");
        article2.setTitle("Test");
        article2.setText("Test");
        article2.setId(2);

        articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);

        mockedList = new ArrayList<>();
        mockedList.add(article1);

    }

    @Test
    void testFindAll() {
        Mockito.when(articleDAO.findAll()).thenReturn(articles);

            final List<Article> articleList = articleService.findAll();

        Assertions.assertEquals(2, articleList.size());
    }

    @Test
    void testFindAllEmpty() {
        Mockito.when(articleDAO.findAll()).thenReturn(new ArrayList<>());

        final List<Article> articleList = articleService.findAll();

        Assertions.assertTrue(articleList.isEmpty());
    }

    @Test
    void testFindById() {
        Mockito.when(articleDAO.findByID(2)).thenReturn(articles.get(1));

        Assertions.assertEquals(2, articles.get(1).getId());
        Assertions.assertEquals("Test", articles.get(1).getAuthor());
        Assertions.assertEquals("Test", articles.get(1).getTitle());
        Assertions.assertEquals("Test", articles.get(1).getText());
    }

    @Test
    void testFindByIdException() {
        Mockito.when(articleDAO.findByID(22)).thenThrow(NoContentException.class);

        Assertions.assertThrows(NoContentException.class, () -> articleDAO.findByID(22));
    }

    @Test
    void testSearchArticles() {
        Mockito.when(articleDAO.searchArticles("search")).thenReturn(mockedList);
        final List<Article> articleList = articleDAO.searchArticles("search");

        Assertions.assertEquals(1, articleList.size());
        Assertions.assertEquals("Author", articleList.get(0).getAuthor());
        Assertions.assertEquals("Title", articleList.get(0).getTitle());
        Assertions.assertEquals("search", articleList.get(0).getText());
    }

    @Test
    void testSearchArticlesException() {
        Mockito.when(articleDAO.searchArticles("random")).thenThrow(NoContentException.class);

        Assertions.assertThrows(NoContentException.class, () -> articleDAO.searchArticles("random"));
    }
}
