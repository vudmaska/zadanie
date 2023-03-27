package sk.ness.academy.service;

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

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

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
  public void createComment(Integer articleId, Comment comment) {

    Article article = this.articleDAO.findByID(articleId);
    List<Comment> comments = article.getComments();
    comments.add(comment);
    article.setComments(comments);
    this.articleDAO.persist(article);

  }

  @Override
  public List<Comment> readComments(Integer articleId) {
    Article article = this.articleDAO.findByID(articleId);
    List<Comment> comments = article.getComments();
    return comments;
  }

  @Override
  public void deleteComment(Integer articleId, Integer commentId) {
    Article article = this.articleDAO.findByID(articleId);
    //List<Comment> comments = article.getComments();
    //comments.remove(commentId);
    //article.setComments(comments);
    //this.articleDAO.persist(article);

    Session session = this.sessionFactory.getCurrentSession();
    //session.persist(article);
    session.createSQLQuery("delete from comments where commentId = :commentId").setParameter("commentId", commentId).executeUpdate();
  }


}
