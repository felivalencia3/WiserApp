package dwight.global.wiser;

import javax.persistence.*;

@Entity
public class Submission {
    //@Id makes id unique and stores it as a primary key in the database
    //@GeneratedValue stores an automatically generated unique value
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String artist;

    private String title;

    //Subheadings can be optional
    @Basic(optional=true)
    private String subheading;

    //URLs are optional and have a default value
    @Basic(optional=true)
    private String url = "../public/img/bg-masthead.jpg";

    @Column(columnDefinition="text")
    @Basic(optional=true)
    private String content;

    @Basic(optional=true)
    private boolean approved;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }


}
