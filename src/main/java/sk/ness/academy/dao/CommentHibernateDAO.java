package sk.ness.academy.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exceptions.NoContentException;
import sk.ness.academy.exceptions.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CommentHibernateDAO implements CommentDAO{

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Resource
    private ArticleDAO articleDAO;

    @Override
    public void createComment(Integer articleId, Comment comment) {

        Article article = this.articleDAO.findByID(articleId);
        if (article == null){
            throw new ResourceNotFoundException("Article with the id " + articleId + " not found.");
        }
        List<Comment> comments = article.getComments();
        comments.add(comment);
        article.setComments(comments);
        this.articleDAO.persist(article);

    }

    @Override
    public List<Comment> readComments(Integer articleId) {
        Article article = this.articleDAO.findByID(articleId);
        if (article == null){
            throw new ResourceNotFoundException("Article with the id " + articleId + " not found.");
        }
        List<Comment> comments = article.getComments();
        if(comments.isEmpty()){
            throw new NoContentException("This article has no comments");
        }
        return comments;
    }

    @Override
    public ResponseEntity deleteComment(Integer articleId, Integer commentId) {
        Session session = this.sessionFactory.getCurrentSession();
        if (session.createSQLQuery("select * from comments where commentId = :commentId").setParameter("commentId", commentId).list().isEmpty()){
            throw new ResourceNotFoundException("Comment with id " + commentId + " does not exist");
            //    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            //            .body("Comment with id " + commentId + " does not exist");
        } else {
            session.createSQLQuery("delete from comments where commentId = :commentId").setParameter("commentId", commentId).executeUpdate();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Comment has been successfully deleted.");
        }
    }
}
