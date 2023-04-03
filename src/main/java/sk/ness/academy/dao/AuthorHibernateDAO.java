package sk.ness.academy.dao;

import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.NoContentException;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class AuthorHibernateDAO implements AuthorDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  @Override
  public List<Author> findAll() {
    List<Author> allAuthors = this.sessionFactory.getCurrentSession().createSQLQuery("select distinct a.author as name from articles a ")
        .addScalar("name", StringType.INSTANCE)
        .setResultTransformer(new AliasToBeanResultTransformer(Author.class)).list();
    if (allAuthors.isEmpty()){
      throw new NoContentException("No authors found.");
    } else {
      return allAuthors;
    }
  }

  @Override
  public List<AuthorStats> listOfAuthorsAndCount() {
    List<Author> allAuthors = findAll();
    if (allAuthors.isEmpty()) {
      throw new NoContentException("No authors found.");
    } else {
      return this.sessionFactory.getCurrentSession().createSQLQuery("select author as authorName, count(author) as articleCount from articles group by author")
              .addScalar("authorName", StringType.INSTANCE)
              .addScalar("articleCount", IntegerType.INSTANCE)
              .setResultTransformer(new AliasToBeanResultTransformer(AuthorStats.class)).list();
    }
  }

}

