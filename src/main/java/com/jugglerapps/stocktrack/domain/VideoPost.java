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
 * A VideoPost.
 */
@Entity
@Table(name = "video_post")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VideoPost implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties("videoPosts")
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

    public VideoPost postTitle(String postTitle) {
        this.postTitle = postTitle;
        return this;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    public VideoPost postBody(String postBody) {
        this.postBody = postBody;
        return this;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public VideoPost dateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateApproved() {
        return dateApproved;
    }

    public VideoPost dateApproved(LocalDate dateApproved) {
        this.dateApproved = dateApproved;
        return this;
    }

    public void setDateApproved(LocalDate dateApproved) {
        this.dateApproved = dateApproved;
    }

    public byte[] getMedia1() {
        return media1;
    }

    public VideoPost media1(byte[] media1) {
        this.media1 = media1;
        return this;
    }

    public void setMedia1(byte[] media1) {
        this.media1 = media1;
    }

    public String getMedia1ContentType() {
        return media1ContentType;
    }

    public VideoPost media1ContentType(String media1ContentType) {
        this.media1ContentType = media1ContentType;
        return this;
    }

    public void setMedia1ContentType(String media1ContentType) {
        this.media1ContentType = media1ContentType;
    }

    public User getUser() {
        return user;
    }

    public VideoPost user(User user) {
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
        if (!(o instanceof VideoPost)) {
            return false;
        }
        return id != null && id.equals(((VideoPost) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VideoPost{" +
            "id=" + getId() +
            ", postTitle='" + getPostTitle() + "'" +
            ", postBody='" + getPostBody() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            ", dateApproved='" + getDateApproved() + "'" +
            ", media1='" + getMedia1() + "'" +
            ", media1ContentType='" + getMedia1ContentType() + "'" +
            "}";
    }
}
