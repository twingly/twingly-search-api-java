package com.twingly.search;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

/**
 * A blog post
 */
@XmlRootElement(name = "post")
public class Post {
    /**
     * the post URL
     */
    private String url;
    /**
     * the post title
     */
    private String title;
    /**
     * the blog post text
     */
    private String summary;
    /**
     * ISO two letter language code for the language that the post was written in.
     */
    private String languageCode;
    /**
     * the time, in UTC, when the post was published
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date published;
    /**
     * the time, in UTC, when the post was indexed by Twingly
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date indexed;
    /**
     * the blog URL
     */
    private String blogUrl;
    /**
     * name of the blog
     */
    private String blogName;
    /**
     * the blog's authority/influence.
     *
     * @see "https://developer.twingly.com/resources/ranking/#authority"
     */
    private int authority;
    /**
     * blog_rank the rank of the blog, based on authority and language.
     *
     * @see "https://developer.twingly.com/resources/ranking/#blogrank"
     */
    private int blogRank;
    @XmlElementWrapper(name = "tags")
    @XmlElement(name = "tag")
    private List<String> tags;
    @XmlAttribute(name = "contentType")
    private String contentType;

    public Post() {
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Date getIndexed() {
        return indexed;
    }

    public void setIndexed(Date indexed) {
        this.indexed = indexed;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public int getBlogRank() {
        return blogRank;
    }

    public void setBlogRank(int blogRank) {
        this.blogRank = blogRank;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
