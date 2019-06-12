package com.jugglerapps.stocktrack.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_body")
    private String postBody;

    @NotNull
    @Column(name = "date_added", nullable = false)
    private LocalDate dateAdded;

    @Column(name = "date_approved")
    private LocalDate dateApproved;

    @Lob
    @Column(name = "media_1")
    private byte[] media1;

    @Column(name = "media_1_content_type")
    private String media1ContentType;

    @Lob
    @Column(name = "media_2")
    private byte[] media2;

    @Column(name = "media_2_content_type")
    private String media2ContentType;

    @Lob
    @Column(name = "media_3")
    private byte[] media3;

    @Column(name = "media_3_content_type")
    private String media3ContentType;

    @Lob
    @Column(name = "media_4")
    private byte[] media4;

    @Column(name = "media_4_content_type")
    private String media4ContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private Instrument instrument;

    @OneToOne
    @JoinColumn(unique = true)
    private Comment comment;

    @ManyToOne
    @JsonIgnoreProperties("posts")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public Post postTitle(String postTitle) {
        this.postTitle = postTitle;
        return this;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    public Post postBody(String postBody) {
        this.postBody = postBody;
        return this;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public Post dateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateApproved() {
        return dateApproved;
    }

    public Post dateApproved(LocalDate dateApproved) {
        this.dateApproved = dateApproved;
        return this;
    }

    public void setDateApproved(LocalDate dateApproved) {
        this.dateApproved = dateApproved;
    }

    public byte[] getMedia1() {
        return media1;
    }

    public Post media1(byte[] media1) {
        this.media1 = media1;
        return this;
    }

    public void setMedia1(byte[] media1) {
        this.media1 = media1;
    }

    public String getMedia1ContentType() {
        return media1ContentType;
    }

    public Post media1ContentType(String media1ContentType) {
        this.media1ContentType = media1ContentType;
        return this;
    }

    public void setMedia1ContentType(String media1ContentType) {
        this.media1ContentType = media1ContentType;
    }

    public byte[] getMedia2() {
        return media2;
    }

    public Post media2(byte[] media2) {
        this.media2 = media2;
        return this;
    }

    public void setMedia2(byte[] media2) {
        this.media2 = media2;
    }

    public String getMedia2ContentType() {
        return media2ContentType;
    }

    public Post media2ContentType(String media2ContentType) {
        this.media2ContentType = media2ContentType;
        return this;
    }

    public void setMedia2ContentType(String media2ContentType) {
        this.media2ContentType = media2ContentType;
    }

    public byte[] getMedia3() {
        return media3;
    }

    public Post media3(byte[] media3) {
        this.media3 = media3;
        return this;
    }

    public void setMedia3(byte[] media3) {
        this.media3 = media3;
    }

    public String getMedia3ContentType() {
        return media3ContentType;
    }

    public Post media3ContentType(String media3ContentType) {
        this.media3ContentType = media3ContentType;
        return this;
    }

    public void setMedia3ContentType(String media3ContentType) {
        this.media3ContentType = media3ContentType;
    }

    public byte[] getMedia4() {
        return media4;
    }

    public Post media4(byte[] media4) {
        this.media4 = media4;
        return this;
    }

    public void setMedia4(byte[] media4) {
        this.media4 = media4;
    }

    public String getMedia4ContentType() {
        return media4ContentType;
    }

    public Post media4ContentType(String media4ContentType) {
        this.media4ContentType = media4ContentType;
        return this;
    }

    public void setMedia4ContentType(String media4ContentType) {
        this.media4ContentType = media4ContentType;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Post instrument(Instrument instrument) {
        this.instrument = instrument;
        return this;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public Comment getComment() {
        return comment;
    }

    public Post comment(Comment comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public Post user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", postTitle='" + getPostTitle() + "'" +
            ", postBody='" + getPostBody() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            ", dateApproved='" + getDateApproved() + "'" +
            ", media1='" + getMedia1() + "'" +
            ", media1ContentType='" + getMedia1ContentType() + "'" +
            ", media2='" + getMedia2() + "'" +
            ", media2ContentType='" + getMedia2ContentType() + "'" +
            ", media3='" + getMedia3() + "'" +
            ", media3ContentType='" + getMedia3ContentType() + "'" +
            ", media4='" + getMedia4() + "'" +
            ", media4ContentType='" + getMedia4ContentType() + "'" +
            "}";
    }
}
