package sk.ness.academy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sk.ness.academy.config.TestDatabaseConfig;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exceptions.NoContentException;
import sk.ness.academy.exceptions.ResourceNotFoundException;
import sk.ness.academy.service.CommentServiceImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, Comment.class})
@Transactional
public class CommentServiceImplTest {

    @Mock
    private ArticleDAO articleDAO;

    @Mock
    private CommentServiceImpl commentService;

    private List<Comment> comments;

    private List<Article> articles;

    @BeforeEach
    private void init(){
        final Article article1 = new Article();

        final Comment comment = new Comment();
        comment.setCommentId(1);
        comment.setText("content");
        comment.setAuthor("Author");

        final Comment comment2 = new Comment();
        comment2.setCommentId(2);
        comment2.setText("content 2");
        comment2.setAuthor("Author 2");

        comments = new ArrayList<>();
        comments.add(comment);
        comments.add(comment);

        article1.setAuthor("search");
        article1.setTitle("search");
        article1.setText("search");
        article1.setId(1);
        article1.setComments(comments);

        final Article article2 = new Article();
        article2.setAuthor("Test");
        article2.setTitle("Test");
        article2.setText("Test");
        article2.setId(2);

        articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);

    }

    @Test
    void testReadComments() {
        Mockito.when(commentService.readComments(1)).thenReturn(articles.get(0).getComments());

           final List<Comment> commentList = commentService.readComments(1);

        Assertions.assertEquals(2, commentList.size());
    }

    @Test
    void testReadCommentsEmpty() {
        Mockito.when(commentService.readComments(2)).thenReturn(new ArrayList<>());

        final List<Comment> commentList = commentService.readComments(2);

        Assertions.assertTrue(commentList.isEmpty());
    }

    @Test
    void testReadCommentsWrongArticleId() {
        Mockito.when(commentService.readComments(3)).thenThrow(new ResourceNotFoundException("Article with the id 3 not found."));

        Assertions.assertThrows(ResourceNotFoundException.class, () -> commentService.readComments(3));
    }

    @Test
    void testReadCommentsEmptyException() {
        Mockito.when(commentService.readComments(2)).thenThrow(new NoContentException("This article has no comments."));

        Assertions.assertThrows(NoContentException.class, () -> commentService.readComments(2));
    }
}
