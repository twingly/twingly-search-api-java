package com.twingly.search;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

/**
 * This class represents Post entity from TwinglySearch API response.
 *
 * @see <a href="https://developer.twingly.com/resources/search/#response">Response documentation</a>
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
     * @see <a href="https://developer.twingly.com/resources/ranking/#authority">Authority</a>
     */
    private int authority;
    /**
     * the rank of the blog, based on authority and language.
     *
     * @see <a href="https://developer.twingly.com/resources/ranking/#blogrank">Blogrank</a>
     */
    private int blogRank;
    /**
     * Tags, related to current post
     */
    @XmlElementWrapper(name = "tags")
    @XmlElement(name = "tag")
    private List<String> tags;
    /**
     * Content type of post. The only supported now is "Blog"
     */
    @XmlAttribute(name = "contentType")
    private String contentType;

    /**
     * Instantiates a new Post.
     */
    public Post() {
    }

    /**
     * Gets content type.
     *
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets content type.
     *
     * @param contentType the content type
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets summary.
     *
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets summary.
     *
     * @param summary the summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Gets language code.
     *
     * @return the language code
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets language code.
     *
     * @param languageCode the language code
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Gets published.
     *
     * @return the published
     */
    public Date getPublished() {
        return published;
    }

    /**
     * Sets published.
     *
     * @param published the published
     */
    public void setPublished(Date published) {
        this.published = published;
    }

    /**
     * Gets indexed.
     *
     * @return the indexed
     */
    public Date getIndexed() {
        return indexed;
    }

    /**
     * Sets indexed.
     *
     * @param indexed the indexed
     */
    public void setIndexed(Date indexed) {
        this.indexed = indexed;
    }

    /**
     * Gets blog url.
     *
     * @return the blog url
     */
    public String getBlogUrl() {
        return blogUrl;
    }

    /**
     * Sets blog url.
     *
     * @param blogUrl the blog url
     */
    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    /**
     * Gets blog name.
     *
     * @return the blog name
     */
    public String getBlogName() {
        return blogName;
    }

    /**
     * Sets blog name.
     *
     * @param blogName the blog name
     */
    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    /**
     * Gets authority.
     *
     * @return the authority
     */
    public int getAuthority() {
        return authority;
    }

    /**
     * Sets authority.
     *
     * @param authority the authority
     */
    public void setAuthority(int authority) {
        this.authority = authority;
    }

    /**
     * Gets blog rank.
     *
     * @return the blog rank
     */
    public int getBlogRank() {
        return blogRank;
    }

    /**
     * Sets blog rank.
     *
     * @param blogRank the blog rank
     */
    public void setBlogRank(int blogRank) {
        this.blogRank = blogRank;
    }

    /**
     * Gets tags.
     *
     * @return the tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Sets tags.
     *
     * @param tags the tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
