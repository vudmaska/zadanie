package sk.ness.academy.service;

import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

import java.util.List;

public interface AuthorService {

	  /** Returns all available {@link Author}s */
	  List<Author> findAll();

	  List<AuthorStats> listOfAuthorsAndCount();

	}
