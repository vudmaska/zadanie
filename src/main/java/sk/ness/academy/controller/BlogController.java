package sk.ness.academy.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

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
    public void createComment(@RequestBody @PathVariable final Integer articleId, @RequestBody final String content) {
        this.articleService.createComment(articleId, content);
    }
}