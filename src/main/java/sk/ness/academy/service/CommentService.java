package sk.ness.academy.service;

import org.springframework.http.ResponseEntity;
import sk.ness.academy.domain.Comment;

import java.util.List;


public interface CommentService {

    void createComment(Integer articleId, Comment comment);

    List<Comment> readComments(Integer articleId);

    ResponseEntity deleteComment(Integer articleId, Integer commentId);
}
