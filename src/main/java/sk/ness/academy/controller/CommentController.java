package sk.ness.academy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exceptions.NoContentException;
import sk.ness.academy.exceptions.ResourceNotFoundException;
import sk.ness.academy.repository.ArticleRepo;
import sk.ness.academy.repository.CommentRepo;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ArticleRepo articleRepo;

    @GetMapping("/articles/{articleId}/comments")
    public List<Comment> getAllCommentsByArticleId(@PathVariable (value = "articleId") Integer articleId) {
        if (commentRepo.findByArticleId(articleId).isEmpty()){
            throw new NoContentException("Article has no comments.");
        }
        return commentRepo.findByArticleId(articleId);
    }

    @PostMapping("/articles/{articleId}/comments")
    public Comment createComment(@PathVariable (value = "articleId") Integer articleId,
                                 @Valid @RequestBody Comment comment) {
        return articleRepo.findById(articleId).map(article -> {
            comment.setArticle(article);
            return commentRepo.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("Article with the id " + articleId + " not found"));
    }

    @PutMapping("/articles/{articleId}/comments/{commentId}")
    public Comment updateComment(@PathVariable (value = "articleId") Integer articleId,
                                 @PathVariable (value = "commentId") Integer commentId,
                                 @Valid @RequestBody Comment commentRequest) {
        if(!articleRepo.existsById(articleId)) {
            throw new ResourceNotFoundException("Article with the id " + articleId + " not found");
        }

        return commentRepo.findById(commentId).map(comment -> {
            comment.setContent(commentRequest.getContent());
            return commentRepo.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
    }

    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "articleId") Integer articleId,
                                           @PathVariable (value = "commentId") Integer commentId) {
        return commentRepo.findByCommentIdAndArticleId(commentId, articleId).map(comment -> {
            commentRepo.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and postId " + articleId));
    }
}