package sk.ness.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.ResourceNotFoundException;
import sk.ness.academy.repository.ArticleRepo;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ArticleController {

    @Autowired
    private ArticleRepo articleRepository;

    @GetMapping("/articles")
    public List<Article> getAllPosts() {
        return articleRepository.findAll();
    }

    @PostMapping("/articles")
    public Article createPost(@Valid @RequestBody Article article) {
        return articleRepository.save(article);
    }

    @GetMapping("/articles/{articleId}")
    public Optional<Article> getArticle(@Valid @PathVariable Integer articleId){
        return articleRepository.findById(articleId);
    }

    @PutMapping("/articles/{articleId}")
    public Article updateArticle(@PathVariable Integer articleId, @Valid @RequestBody Article articleRequest) {
        return articleRepository.findById(articleId).map(article -> {
            article.setTitle(articleRequest.getTitle());
            article.setText(articleRequest.getText());
            article.setAuthor(articleRequest.getAuthor());
            return articleRepository.save(article);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + articleId + " not found"));
    }


    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable Integer articleId) {
        return articleRepository.findById(articleId).map(article -> {
            articleRepository.delete(article);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + articleId + " not found"));
    }

}
