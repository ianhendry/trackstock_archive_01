package com.jugglerapps.stocktrack.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Watchlist.
 */
@Entity
@Table(name = "watchlist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Watchlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "watchlist_name", nullable = false)
    private String watchlistName;

    @Column(name = "watchlist_description")
    private String watchlistDescription;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Column(name = "date_inactive")
    private LocalDate dateInactive;

    @Column(name = "watchlist_inactive")
    private Boolean watchlistInactive;

    @OneToMany(mappedBy = "watchlist")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "watchlist_instrument",
               joinColumns = @JoinColumn(name = "watchlist_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "instrument_id", referencedColumnName = "id"))
    private Set<Instrument> instruments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWatchlistName() {
        return watchlistName;
    }

    public Watchlist watchlistName(String watchlistName) {
        this.watchlistName = watchlistName;
        return this;
    }

    public void setWatchlistName(String watchlistName) {
        this.watchlistName = watchlistName;
    }

    public String getWatchlistDescription() {
        return watchlistDescription;
    }

    public Watchlist watchlistDescription(String watchlistDescription) {
        this.watchlistDescription = watchlistDescription;
        return this;
    }

    public void setWatchlistDescription(String watchlistDescription) {
        this.watchlistDescription = watchlistDescription;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Watchlist dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateInactive() {
        return dateInactive;
    }

    public Watchlist dateInactive(LocalDate dateInactive) {
        this.dateInactive = dateInactive;
        return this;
    }

    public void setDateInactive(LocalDate dateInactive) {
        this.dateInactive = dateInactive;
    }

    public Boolean isWatchlistInactive() {
        return watchlistInactive;
    }

    public Watchlist watchlistInactive(Boolean watchlistInactive) {
        this.watchlistInactive = watchlistInactive;
        return this;
    }

    public void setWatchlistInactive(Boolean watchlistInactive) {
        this.watchlistInactive = watchlistInactive;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Watchlist comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Watchlist addComment(Comment comment) {
        this.comments.add(comment);
        comment.setWatchlist(this);
        return this;
    }

    public Watchlist removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setWatchlist(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public Watchlist instruments(Set<Instrument> instruments) {
        this.instruments = instruments;
        return this;
    }

    public Watchlist addInstrument(Instrument instrument) {
        this.instruments.add(instrument);
        instrument.getWatchlists().add(this);
        return this;
    }

    public Watchlist removeInstrument(Instrument instrument) {
        this.instruments.remove(instrument);
        instrument.getWatchlists().remove(this);
        return this;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Watchlist)) {
            return false;
        }
        return id != null && id.equals(((Watchlist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Watchlist{" +
            "id=" + getId() +
            ", watchlistName='" + getWatchlistName() + "'" +
            ", watchlistDescription='" + getWatchlistDescription() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateInactive='" + getDateInactive() + "'" +
            ", watchlistInactive='" + isWatchlistInactive() + "'" +
            "}";
    }
}
