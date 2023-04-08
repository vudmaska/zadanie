package sk.ness.academy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exceptions.ResourceNotFoundException;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.CommentService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
public class BlogController {

    @Resource
    private ArticleService articleService;

    @Resource
    private AuthorService authorService;

    @Resource
    private ArticleDAO articleDAO;

    @Resource
    private CommentService commentService;

    // ~~ Article
    @RequestMapping(value = "articles", method = RequestMethod.GET)
    public List<Article> getAllArticles() {
        return this.articleService.findAll();
    }

    @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
    public ResponseEntity getArticle(@PathVariable final Integer articleId) throws ResourceNotFoundException {
        if(this.articleService.findByID(articleId) == null) {
            throw new ResourceNotFoundException("Article with the id " + articleId + " not found.");
        }
        return ResponseEntity.ok(this.articleService.findByID(articleId));
    }

    @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
    public List<Article> searchArticle(@PathVariable final String searchText) throws ResourceNotFoundException{

        if (this.articleService.searchArticles(searchText).size() == 0){
            throw new ResourceNotFoundException("Nothing was found for query: " + searchText);
        }
        return this.articleService.searchArticles(searchText);
    }

    @PutMapping(value = "articles")
    public ResponseEntity addArticle(@Valid @RequestBody(required=false) final Article article, Errors errors) {
        if (article == null) {
            System.out.println("EMPTY");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("ERROR: Request body is empty.");
        }
        if (errors.hasFieldErrors()) {
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < errors.getErrorCount(); i++) {
                FieldError fieldError = errors.getFieldErrors().get(i);
                message.append("ERROR: ").append(fieldError.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(message);
        }
            this.articleService.createArticle(article);
            return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/remove/{articleId}")
    public void deleteArticleById(@PathVariable("articleId") int articleId) {
        if(this.articleService.findByID(articleId) == null) {
            throw new ResourceNotFoundException("Article with the id " + articleId + " not found.");
        }
        articleService.deleteByID(articleId);
    }

    // ~~ Author
    @RequestMapping(value = "authors", method = RequestMethod.GET)
    public List<Author> getAllAuthors() {
        return this.authorService.findAll();
    }

    @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
    public List<AuthorStats> authorStats() {
        return this.authorService.listOfAuthorsAndCount();
    }

    // ~~ Comments

    @PutMapping(value = "articles/{articleId}")
    public ResponseEntity createComment(@RequestBody @PathVariable final Integer articleId, @Valid @RequestBody final Comment comment, Errors errors) {
        if (errors.hasFieldErrors()) {
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < errors.getErrorCount(); i++) {
                FieldError fieldError = errors.getFieldErrors().get(i);
                message.append("ERROR: ").append(fieldError.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(message);
        }
         this.commentService.createComment(articleId, comment);
         return ResponseEntity.status(HttpStatus.CREATED)
                .body("Comment has been successfully created.");
    }

    @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.GET)
    public List<Comment> getComments(@PathVariable final Integer articleId) {
        return this.commentService.readComments(articleId);
    }

    @DeleteMapping(value = "articles/{articleId}/comments")
    void deleteComment(@PathVariable final Integer articleId, @RequestBody final Integer commentId) {
        this.commentService.deleteComment(articleId, commentId);
    }
}