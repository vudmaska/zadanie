package sk.ness.academy.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exceptions.NoContentException;
import sk.ness.academy.exceptions.ResourceNotFoundException;

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

    @Resource
    private CommentDAO commentDAO;

    public void createComment(Integer articleId, Comment comment) {
        this.commentDAO.createComment(articleId, comment);
    }

    public List<Comment> readComments(Integer articleId) {

        return this.commentDAO.readComments(articleId);
    }

    public ResponseEntity deleteComment(Integer articleId, Integer commentId) {
       return this.commentDAO.deleteComment(articleId, commentId);
    }
}
