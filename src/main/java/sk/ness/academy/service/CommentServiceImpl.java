package sk.ness.academy.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.domain.ResourceNotFoundException;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Resource
    private ArticleDAO articleDAO;

    public void createComment(Integer articleId, Comment comment) {

        Article article = this.articleDAO.findByID(articleId);
        List<Comment> comments = article.getComments();
        comments.add(comment);
        article.setComments(comments);
        this.articleDAO.persist(article);

    }

    public List<Comment> readComments(Integer articleId) {
        Article article = this.articleDAO.findByID(articleId);
        List<Comment> comments = article.getComments();
        if(comments.isEmpty()){
            throw new ResourceNotFoundException("This article has no comments");
        }
        return comments;
    }

    public ResponseEntity deleteComment(Integer articleId, Integer commentId) {
        Session session = this.sessionFactory.getCurrentSession();
        if (session.createSQLQuery("select * from comments where commentId = :commentId").setParameter("commentId", commentId).list().isEmpty()){
        //   throw new ResourceNotFoundException("Comment with id " + commentId + " not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Comment with id " + commentId + " not exist");
        } else {
            session.createSQLQuery("delete from comments where commentId = :commentId").setParameter("commentId", commentId).executeUpdate();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Comment has been successfully deleted.");
        }
    }
}
