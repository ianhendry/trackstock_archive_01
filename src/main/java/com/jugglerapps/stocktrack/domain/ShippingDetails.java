package com.jugglerapps.stocktrack.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ShippingDetails.
 */
@Entity
@Table(name = "shipping_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShippingDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "address_3")
    private String address3;

    @Column(name = "address_4")
    private String address4;

    @Column(name = "address_5")
    private String address5;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "date_inactive")
    private LocalDate dateInactive;

    @Lob
    @Column(name = "user_picture")
    private byte[] userPicture;

    @Column(name = "user_picture_content_type")
    private String userPictureContentType;

    @ManyToOne
    @JsonIgnoreProperties("shippingDetails")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public ShippingDetails userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public ShippingDetails email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public ShippingDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public ShippingDetails address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public ShippingDetails address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public ShippingDetails address3(String address3) {
        this.address3 = address3;
        return this;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public ShippingDetails address4(String address4) {
        this.address4 = address4;
        return this;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return address5;
    }

    public ShippingDetails address5(String address5) {
        this.address5 = address5;
        return this;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public ShippingDetails dateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateInactive() {
        return dateInactive;
    }

    public ShippingDetails dateInactive(LocalDate dateInactive) {
        this.dateInactive = dateInactive;
        return this;
    }

    public void setDateInactive(LocalDate dateInactive) {
        this.dateInactive = dateInactive;
    }

    public byte[] getUserPicture() {
        return userPicture;
    }

    public ShippingDetails userPicture(byte[] userPicture) {
        this.userPicture = userPicture;
        return this;
    }

    public void setUserPicture(byte[] userPicture) {
        this.userPicture = userPicture;
    }

    public String getUserPictureContentType() {
        return userPictureContentType;
    }

    public ShippingDetails userPictureContentType(String userPictureContentType) {
        this.userPictureContentType = userPictureContentType;
        return this;
    }

    public void setUserPictureContentType(String userPictureContentType) {
        this.userPictureContentType = userPictureContentType;
    }

    public User getUser() {
        return user;
    }

    public ShippingDetails user(User user) {
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
        if (!(o instanceof ShippingDetails)) {
            return false;
        }
        return id != null && id.equals(((ShippingDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ShippingDetails{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", address3='" + getAddress3() + "'" +
            ", address4='" + getAddress4() + "'" +
            ", address5='" + getAddress5() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            ", dateInactive='" + getDateInactive() + "'" +
            ", userPicture='" + getUserPicture() + "'" +
            ", userPictureContentType='" + getUserPictureContentType() + "'" +
            "}";
    }
}
