package sk.ness.academy.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;

@RestController
public class BlogController {

    @Resource
    private ArticleService articleService;

    @Resource
    private AuthorService authorService;

    @Resource
    private ArticleDAO articleDAO;

    // ~~ Article
    @RequestMapping(value = "articles", method = RequestMethod.GET)
    public List<Article> getAllArticles() {
        return this.articleService.findAll();
    }

    @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
    public Article getArticle(@PathVariable final Integer articleId) {
        return this.articleService.findByID(articleId);
    }

    @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
    public List<Article> searchArticle(@PathVariable final String searchText) {
        throw new UnsupportedOperationException("Full text search not implemented.");
    }

    @RequestMapping(value = "articles", method = RequestMethod.PUT)
    public void addArticle(@RequestBody final Article article) {
        this.articleService.createArticle(article);
    }

    // ~~ Author
    @RequestMapping(value = "authors", method = RequestMethod.GET)
    public List<Author> getAllAuthors() {
        return this.authorService.findAll();
    }

    @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
    public List<AuthorStats> authorStats() {
        throw new UnsupportedOperationException("Author statistics not implemented.");
    }

    // ~~ Comments

    @PutMapping(value = "articles/{articleId}")
    public void createComment(@RequestBody @PathVariable final Integer articleId, @RequestBody final Comment comment) {
         this.articleService.createComment(articleId, comment);
    }

    @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.GET)
    public List<Comment> getComments(@PathVariable final Integer articleId) {
        return this.articleService.readComments(articleId);
    }

    @DeleteMapping(value = "articles/{articleId}/comments")
    void deleteComment(@PathVariable final Integer articleId, @RequestBody final Integer commentId) {
        this.articleService.deleteComment(articleId, commentId);
    }
}