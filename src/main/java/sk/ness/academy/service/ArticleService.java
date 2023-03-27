package sk.ness.academy.service;

import sk.ness.academy.domain.Article;

import java.util.List;

public interface ArticleService {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId);

	  /** Returns all available {@link Article}s */
	  List<Article> findAll();

	  /** Creates new {@link Article} */
	  void createArticle(Article article);

	  /** Creates new {@link Article}s by ingesting all articles from json */
	  void ingestArticles(String jsonArticles);

	  void deleteByID(Integer articleId);

	  List<Article> searchArticles(String searchText);

}
