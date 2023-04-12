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
import sk.ness.academy.dao.AuthorDAO;
import sk.ness.academy.dao.AuthorHibernateDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.service.ArticleServiceImpl;
import sk.ness.academy.service.AuthorServiceImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, AuthorHibernateDAO.class})
@Transactional
@Sql({"/initdb.sql"})
public class AuthorHibernateDAOTest {

    @Autowired
    private AuthorDAO authorDAO;

    @Test
    void testFindAll() {

        final List<Author> articleList = authorDAO.findAll();

        Assertions.assertEquals(5, articleList.size());

    }

    @Test
    void testListOfAuthorsAndCount() {
        final List<AuthorStats> articleList = authorDAO.listOfAuthorsAndCount();

        Assertions.assertEquals(5, articleList.size());
    }


}
