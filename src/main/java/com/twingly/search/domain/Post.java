package com.twingly.search.domain;

import com.twingly.search.DateAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.math.BigDecimal;

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
     *
     * @since 3.0.0
     */
    private String text;
    /**
     * ISO two letter language code for the language that the post was written in.
     */
    private String languageCode;
    /**
     * the time, in UTC, when the post was published at
     *
     * @since 3.0.0
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date publishedAt;
    /**
     * the time, in UTC, when the post was indexed at by Twingly
     *
     * @since 3.0.0
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date indexedAt;
    /**
     * the blog URL
     */
    private String blogUrl;
    /**
     * name of the blog
     */
    private String blogName;

    /**
     * The ID of the blog
     *
     * @since 3.0.0
     */
    private String blogId;
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
     * The blog post ID
     *
     * @since 3.0.0
     */
    private String id;

    /**
     * The blog post author
     *
     * @since 3.0.0
     */
    private String author;

    /**
     * ISO two letter location code for the language that the post was written in.
     *
     * @since 3.0.0
     */
    private String locationCode;

    /**
     * Number of links found in other blog posts
     *
     * @since 3.0.0
     */
    private int inlinksCount;
    /**
     * the time, in UTC, when the post was reindexed at by Twingly
     *
     * @since 3.0.0
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date reindexedAt;
    /**
     * All links from the blog post to other resources
     *
     * @since 3.0.0
     */
    @XmlElementWrapper(name = "links")
    @XmlElement(name = "link")
    private List<String> links;

    /**
     * Image URLs from the posts
     *
     * @since 3.0.0
     */
    @XmlElementWrapper(name = "images")
    @XmlElement(name = "image")
    private List<String> images;

    /**
     * Geographical coordinates from blog post
     *
     * @since 3.0.0
     */
    @XmlElement
    private Coordinate coordinates;
    /**
     * Content type of post. The only supported now is "Blog"
     *
     * @deprecated since 3.0.0
     */
    @XmlAttribute(name = "contentType")
    @Deprecated
    private ContentType contentType;

    /**
     * Instantiates a new Post.
     */
    public Post() {
    }

    /**
     * Gets content type.
     *
     * @return the content type
     * @deprecated since 3.0.0
     */
    @Deprecated
    public ContentType getContentType() {
        return contentType;
    }

    /**
     * Sets content type.
     *
     * @param contentType the content type
     * @deprecated since 3.0.0
     */
    @Deprecated
    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    /**
     * Gets blog id
     *
     * @return the blog id
     */
    public String getBlogId() {
        return blogId;
    }

    /**
     * Sets blog id
     *
     * @param blogId the blog id
     */
    public void setBlogId(String blogId) {
        this.blogId = blogId;
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
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
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
     * Gets publishedAt.
     *
     * @return the publishedAt
     */
    public Date getPublishedAt() {
        return publishedAt;
    }

    /**
     * Sets publishedAt.
     *
     * @param publishedAt the publishedAt
     */
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    /**
     * Gets indexedAt.
     *
     * @return the indexedAt
     */
    public Date getIndexedAt() {
        return indexedAt;
    }

    /**
     * Sets indexedAt.
     *
     * @param indexedAt the indexedAt
     */
    public void setIndexedAt(Date indexedAt) {
        this.indexedAt = indexedAt;
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
        if (tags == null) {
            tags = new ArrayList<>();
        }
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

    /**
     * Gets id
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets author
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets author
     *
     * @param author the author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets location code
     *
     * @return the location code
     */
    public String getLocationCode() {
        return locationCode;
    }

    /**
     * Sets location code
     *
     * @param locationCode the location code
     */
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    /**
     * Gets inlinks count
     *
     * @return the inlinks count
     */
    public int getInlinksCount() {
        return inlinksCount;
    }

    /**
     * Sets inlinks count
     *
     * @param inlinksCount the inlinks count
     */
    public void setInlinksCount(int inlinksCount) {
        this.inlinksCount = inlinksCount;
    }

    /**
     * Gets re-indexed at date
     *
     * @return the re-indexed at date
     */
    public Date getReindexedAt() {
        return reindexedAt;
    }

    /**
     * Sets re-indexed at date
     *
     * @param reindexedAt the re-indexed at date
     */
    public void setReindexedAt(Date reindexedAt) {
        this.reindexedAt = reindexedAt;
    }

    /**
     * Gets links
     *
     * @return the links
     */
    public List<String> getLinks() {
        if (links == null) {
            links = new ArrayList<>();
        }
        return links;
    }

    /**
     * Sets links
     *
     * @param links the links
     */
    public void setLinks(List<String> links) {
        this.links = links;
    }

    /**
     * Gets images
     *
     * @return the images
     */
    public List<String> getImages() {
        if (images == null) {
            images = new ArrayList<>();
        }
        return images;
    }

    /**
     * Sets images
     *
     * @param images the images
     */
    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * Gets latitude
     *
     * @return the latitude
     */
    public BigDecimal getLatitude() {
        return coordinates.getLatitude();
    }

    /**
     * Sets latitude
     *
     * @param latitude the latitude
     */
    public void setLatitude(BigDecimal latitude) {
        this.coordinates.setLatitude(latitude);
    }

    /**
     * Gets longitude
     *
     * @return the longitude
     */
    public BigDecimal getLongitude() {
        return coordinates.getLongitude();
    }

    /**
     * Sets longitude
     *
     * @param longitude the longitude
     */
    public void setLongitude(BigDecimal longitude) {
        this.coordinates.setLongitude(longitude);
    }

    /**
     * Gets summary.
     *
     * @return the summary
     * @deprecated use getText() instead
     */
    @Deprecated
    public String getSummary() {
        return getText();
    }

    /**
     * Sets summary.
     *
     * @param summary the summary
     * @deprecated use setText() instead
     */
    @Deprecated
    public void setSummary(String summary) {
        setText(summary);
    }

    /**
     * Gets published.
     *
     * @return the published
     * @deprecated use getPublishedAt() instead
     */
    @Deprecated
    public Date getPublished() {
        return getPublishedAt();
    }

    /**
     * Sets published.
     *
     * @param published the published
     * @deprecated use setPublishedAt() instead
     */
    @Deprecated
    public void setPublished(Date published) {
        setPublishedAt(published);
    }

    /**
     * Gets indexed.
     *
     * @return the indexed
     * @deprecated use getIndexedAt() instead
     */
    @Deprecated
    public Date getIndexed() {
        return getIndexedAt();
    }

    /**
     * Sets indexed.
     *
     * @param indexed the indexed
     * @deprecated use setIndexedAt() instead
     */
    @Deprecated
    public void setIndexed(Date indexed) {
        setIndexedAt(indexed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return authority == post.authority &&
                blogRank == post.blogRank &&
                inlinksCount == post.inlinksCount &&
                Objects.equals(url, post.url) &&
                Objects.equals(title, post.title) &&
                Objects.equals(text, post.text) &&
                Objects.equals(languageCode, post.languageCode) &&
                Objects.equals(publishedAt, post.publishedAt) &&
                Objects.equals(indexedAt, post.indexedAt) &&
                Objects.equals(blogUrl, post.blogUrl) &&
                Objects.equals(blogName, post.blogName) &&
                Objects.equals(blogId, post.blogId) &&
                Objects.equals(tags, post.tags) &&
                Objects.equals(id, post.id) &&
                Objects.equals(author, post.author) &&
                Objects.equals(locationCode, post.locationCode) &&
                Objects.equals(reindexedAt, post.reindexedAt) &&
                Objects.equals(links, post.links) &&
                Objects.equals(images, post.images) &&
                Objects.equals(getLatitude(), post.getLatitude()) &&
                Objects.equals(getLongitude(), post.getLongitude()) &&
                contentType == post.contentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, title, text, languageCode, publishedAt, indexedAt, blogUrl, blogName, blogId, authority, blogRank, tags, id, author, locationCode, inlinksCount, reindexedAt, links, images, coordinates, contentType);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Post{");
        sb.append("url='").append(url).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", languageCode='").append(languageCode).append('\'');
        sb.append(", publishedAt=").append(publishedAt);
        sb.append(", indexedAt=").append(indexedAt);
        sb.append(", blogUrl='").append(blogUrl).append('\'');
        sb.append(", blogName='").append(blogName).append('\'');
        sb.append(", blogId='").append(blogId).append('\'');
        sb.append(", authority=").append(authority);
        sb.append(", blogRank=").append(blogRank);
        sb.append(", tags=").append(tags);
        sb.append(", id='").append(id).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", locationCode='").append(locationCode).append('\'');
        sb.append(", inlinksCount=").append(inlinksCount);
        sb.append(", reindexedAt=").append(reindexedAt);
        sb.append(", links=").append(links);
        sb.append(", images=").append(images);
        sb.append(", latitude=").append(getLatitude());
        sb.append(", longitude=").append(getLongitude());
        sb.append(", contentType=").append(contentType);
        sb.append('}');
        return sb.toString();
    }
}
