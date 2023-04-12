package sk.ness.academy.repository;

import java.util.Date;

public interface ArticleProjection {
    Integer getId();
    String getTitle();
    String getText();
    String getAuthor();
    Date getCreateTimestamp();

}
