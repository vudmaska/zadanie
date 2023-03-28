package sk.ness.academy.dao;

import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

import java.util.List;

public interface AuthorDAO {

  /** Returns all available {@link Author}s */
  List<Author> findAll();

  List<AuthorStats> listOfAuthorsAndCount();

}

