package sk.ness.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.exceptions.NoContentException;
import sk.ness.academy.exceptions.ResourceNotFoundException;
import sk.ness.academy.repository.ArticleProjection;
import sk.ness.academy.repository.ArticleRepo;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleRepo articleRepository;

    @GetMapping("/articles")
    public List<ArticleProjection> getAllPosts() {
        return articleRepository.findAllProjectedBy();
    }

    @PostMapping("/articles")
    public Article createPost(@Valid @RequestBody Article article) {
        return articleRepository.save(article);
    }

    @GetMapping("/articles/{articleId}")
    public ResponseEntity getArticle(@Valid @PathVariable Integer articleId){

        if(this.articleRepository.findById(articleId).isEmpty()) {
            throw new ResourceNotFoundException("Article with the id " + articleId + " not found.");
        }
        return ResponseEntity.ok(this.articleRepository.findById(articleId));
    }

    @PutMapping("/articles/{articleId}")
    public Article updateArticle(@PathVariable Integer articleId, @Valid @RequestBody Article articleRequest) {
        return articleRepository.findById(articleId).map(article -> {
            article.setTitle(articleRequest.getTitle());
            article.setText(articleRequest.getText());
            article.setAuthor(articleRequest.getAuthor());
            return articleRepository.save(article);
        }).orElseThrow(() -> new ResourceNotFoundException("Article with the id " + articleId + " not found"));
    }


    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable Integer articleId) {
        return articleRepository.findById(articleId).map(article -> {
            articleRepository.delete(article);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Article with the id " + articleId + " not found"));
    }

    @GetMapping("/author/stats")
    public List<AuthorStats> listOfAuthorsAndCount(){
        if (articleRepository.findAllStats().isEmpty()){
            throw new NoContentException("There are no authors/articles.");
        }
        return articleRepository.findAllStats();
    }

    @GetMapping("/articles/search/{searchText}")
    public List<Article> searchKeyword(@PathVariable String searchText){
        if (articleRepository.findByTitleContainingOrTextContainingOrAuthorContainingAllIgnoreCase(searchText,searchText,searchText).isEmpty()){
            throw new NoContentException("Nothing found.");
        }
        return articleRepository.findByTitleContainingOrTextContainingOrAuthorContainingAllIgnoreCase(searchText, searchText, searchText);
    }



}
