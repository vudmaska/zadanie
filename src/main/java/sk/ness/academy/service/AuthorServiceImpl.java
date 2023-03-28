package sk.ness.academy.service;

import org.springframework.stereotype.Service;
import sk.ness.academy.dao.AuthorDAO;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

  @Resource
  private AuthorDAO authorDAO;

  @Override
  public List<Author> findAll() {
    return this.authorDAO.findAll();
  }

  @Override
  public List<AuthorStats> listOfAuthorsAndCount() {
    return this.authorDAO.listOfAuthorsAndCount();
  }


}
