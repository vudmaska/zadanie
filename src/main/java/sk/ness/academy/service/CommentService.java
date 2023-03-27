package sk.ness.academy.service;

import sk.ness.academy.domain.Comment;

import java.util.List;


public interface CommentService {

    void createComment(Integer articleId, Comment comment);

    List<Comment> readComments(Integer articleId);

    void deleteComment(Integer articleId, Integer commentId);
}
