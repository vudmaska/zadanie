package sk.ness.academy.dao;

import org.springframework.http.ResponseEntity;
import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentDAO {
    void createComment(Integer articleId, Comment comment);

    List<Comment> readComments(Integer articleId);

    ResponseEntity deleteComment(Integer articleId, Integer commentId);
}
