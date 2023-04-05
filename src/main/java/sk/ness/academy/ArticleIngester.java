package sk.ness.academy;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import sk.ness.academy.config.DatabaseConfig;
import sk.ness.academy.service.ArticleService;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "sk.ness.academy", excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AssignmentApplication.class) })
@Import(DatabaseConfig.class)
public class ArticleIngester {

  public static void main(final String[] args) {
    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ArticleIngester.class)) {
      context.registerShutdownHook();

      // Load file with articles and ingest

      final ArticleService articleService = context.getBean(ArticleService.class);
      articleService.ingestArticles("C:\\Akademia\\Java\\Academy\\zadanie\\zadanie_pokusy\\articles_to_ingest.txt");

    }
  }
}
