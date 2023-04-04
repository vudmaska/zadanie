package sk.ness.academy.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {
    List<Comment> findByArticleId(Integer articleId);
    Optional<Comment> findByCommentIdAndArticleId(Integer commentId, Integer articleId);
}
