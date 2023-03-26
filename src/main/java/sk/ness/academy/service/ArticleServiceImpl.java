package sk.ness.academy.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

  @Resource
  private ArticleDAO articleDAO;

  @Override
  public Article findByID(final Integer articleId) {
	  return this.articleDAO.findByID(articleId);
  }

  @Override
  public List<Article> findAll() {
	  return this.articleDAO.findAll();
  }

  @Override
  public void createArticle(final Article article) {
	  this.articleDAO.persist(article);
  }

  @Override
  public void ingestArticles(final String jsonArticles) {
    throw new UnsupportedOperationException("Article ingesting not implemented.");
  }

  @Override
  public void createComment(Integer articleId, String content) {

    Article article = this.articleDAO.findByID(articleId);
    Comment comment = new Comment();
    comment.setContent(content);
    List<Comment> comments = article.getCommentList();
    comments.add(comment);
    article.setCommentList(comments);
    this.articleDAO.persist(article);

  }


}
