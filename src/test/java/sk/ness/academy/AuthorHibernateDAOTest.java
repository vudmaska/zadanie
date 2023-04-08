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
import sk.ness.academy.dao.AuthorDAO;
import sk.ness.academy.dao.AuthorHibernateDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.Author;
import sk.ness.academy.service.ArticleServiceImpl;
import sk.ness.academy.service.AuthorServiceImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, AuthorHibernateDAO.class})
@Transactional
public class AuthorHibernateDAOTest {

    @Mock
    private AuthorDAO authorDAO;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private List<Author> authors;

    private List<Article> articles;

   // private List<Article> mockedList;

    @BeforeEach
    private void init(){
        final Article article1 = new Article();
        article1.setAuthor("search");
        article1.setTitle("search");
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

        final Author author1 = new Author();
        author1.setName("Name");

        final Author author2 = new Author();
        author1.setName("Meno");

        authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);

    }

    @Test
    void testFindAll() {
        Mockito.when(authorDAO.findAll()).thenReturn(authors);

            final List<Author> articleList = authorService.findAll();

        Assertions.assertEquals(2, articleList.size());

    }

    @Test
    void testFindAllNull() {
        Mockito.when(authorDAO.findAll()).thenReturn(null);

        final List<Author> articleList = authorService.findAll();

       Assertions.assertNull(articleList);

    }
}
