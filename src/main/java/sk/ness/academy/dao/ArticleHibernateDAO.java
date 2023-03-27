package sk.ness.academy.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import sk.ness.academy.domain.Article;

@Repository
public class ArticleHibernateDAO implements ArticleDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  public Article findByID(final Integer articleId) {
    return (Article) this.sessionFactory.getCurrentSession().get(Article.class, articleId);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Article> findAll() {
    Session session = this.sessionFactory.getCurrentSession();
    String sql = "SELECT id, author, create_timestamp as created, text, title FROM articles";
    SQLQuery query = session.createSQLQuery(sql).addScalar("id").addScalar("author").addScalar("created").addScalar("text").addScalar("title");
    query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
    List results = query.list();

    return results;
  }

  @Override
  public void persist(final Article article) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(article);
  }

  @Override
  public void deleteByID(final Integer articleId) {
    Article article = sessionFactory.getCurrentSession().load(Article.class, articleId);
    this.sessionFactory.getCurrentSession().delete(article);
  }

  @Override
  public List<Article> searchArticles(String searchText) {
    return this.sessionFactory.getCurrentSession().createSQLQuery(
                    "SELECT * FROM articles WHERE author LIKE concat(concat('%', :searchText), '%') OR title LIKE concat(concat('%', :searchText), '%') OR text LIKE concat(concat('%', :searchText), '%')")
            .setParameter("searchText", searchText)
            .addEntity(Article.class)
            .list();
  }

}
