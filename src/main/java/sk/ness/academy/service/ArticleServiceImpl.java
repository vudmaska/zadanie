package sk.ness.academy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.repository.ArticleRepo;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    @Autowired
    private ArticleRepo articleRepo;

    @Override
    public void ingestArticles(final String jsonArticles) {
        try {

            ObjectMapper mapper = new ObjectMapper();

            Article[] articles = mapper.readValue(Paths.get(jsonArticles).toFile(), Article[].class);

            for (Article article: articles) {
                this.articleRepo.save(article);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}