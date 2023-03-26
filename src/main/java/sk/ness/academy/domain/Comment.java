package sk.ness.academy.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {
    @Id
    @Column(name = "commentId", unique = true, nullable = false, precision = 10, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column(name = "id")
    private Integer id;

    @Column
    private String content;

    //@ManyToOne
    //@JoinColumn(name = "id")
    //private Article article;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public String setContent(String content) {
        this.content = content;
        return content;
    }

    //public Article getArticle() {
    //    return article;
    //}

    //public void setArticle(Article article) {
    //    this.article = article;
   // }
}
