package sk.ness.academy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import sk.ness.academy.config.TestDatabaseConfig;
import sk.ness.academy.dao.*;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exceptions.NoContentException;
import sk.ness.academy.exceptions.ResourceNotFoundException;
import sk.ness.academy.service.CommentServiceImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, CommentHibernateDAO.class, ArticleHibernateDAO.class})
@Transactional
@Sql({"/initdb.sql"})
public class CommentHibernateDAOTest {

    @Autowired
    private CommentDAO commentDAO;

    @Test
    void testReadComments() {

        final List<Comment> commentList = commentDAO.readComments(1);

        Assertions.assertEquals(1, commentList.size());
    }

    @Test
    void testReadCommentsException() {

        Assertions.assertThrows(NoContentException.class, () -> commentDAO.readComments(2));
    }

    @Test
    void testReadCommentsWrongArticleId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> commentDAO.readComments(33));
    }

    @Test
    void testCreateComment() {
        Comment comment = new Comment();
        comment.setCommentId(1);
        comment.setAuthor("Dominik");
        comment.setText("Text");

        Comment comment2 = new Comment();
        comment2.setCommentId(2);
        comment2.setAuthor("Dominik 2");
        comment2.setText("Text 2");

        commentDAO.createComment(4, comment);
        commentDAO.createComment(4, comment2);

        final List<Comment> commentList = commentDAO.readComments(4);

        Assertions.assertEquals(2, commentList.size());
        Assertions.assertEquals("Dominik", commentList.get(0).getAuthor());
        Assertions.assertEquals("Text", commentList.get(0).getText());

        Assertions.assertEquals("Dominik 2", commentList.get(1).getAuthor());
        Assertions.assertEquals("Text 2", commentList.get(1).getText());

    }

    @Test
    void testCreateCommentException() {
        Comment comment = new Comment();
        comment.setCommentId(1);
        comment.setAuthor("Dominik");
        comment.setText("Text");

        Assertions.assertThrows(ResourceNotFoundException.class, () -> commentDAO.createComment(100, comment));

    }

}
