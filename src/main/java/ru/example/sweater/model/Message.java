package ru.example.sweater.model;


import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    public Message(){}

    public Message(String text, String tag, User author)
    {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public Integer getId()
    {
        return id;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public String getAuthorUserName()
    {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor(User author)
    {
        this.author = author;
    }
}
