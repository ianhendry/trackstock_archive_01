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

import com.jugglerapps.stocktrack.domain.enumeration.FinancialDataSources;

/**
 * A Instrument.
 */
@Entity
@Table(name = "instrument")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Instrument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_provider")
    private FinancialDataSources dataProvider;

    @NotNull
    @Column(name = "instrument_ticker", nullable = false)
    private String instrumentTicker;

    @Column(name = "instrument_exchnage")
    private String instrumentExchnage;

    @Column(name = "instrument_description")
    private String instrumentDescription;

    @Column(name = "instrument_data_from")
    private String instrumentDataFrom;

    @Column(name = "instrument_active")
    private Boolean instrumentActive;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "date_inactive")
    private LocalDate dateInactive;

    @OneToOne(mappedBy = "instrument")
    @JsonIgnore
    private Position position;

    @OneToOne(mappedBy = "instrument")
    @JsonIgnore
    private Post post;

    @ManyToMany(mappedBy = "instruments")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Watchlist> watchlists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FinancialDataSources getDataProvider() {
        return dataProvider;
    }

    public Instrument dataProvider(FinancialDataSources dataProvider) {
        this.dataProvider = dataProvider;
        return this;
    }

    public void setDataProvider(FinancialDataSources dataProvider) {
        this.dataProvider = dataProvider;
    }

    public String getInstrumentTicker() {
        return instrumentTicker;
    }

    public Instrument instrumentTicker(String instrumentTicker) {
        this.instrumentTicker = instrumentTicker;
        return this;
    }

    public void setInstrumentTicker(String instrumentTicker) {
        this.instrumentTicker = instrumentTicker;
    }

    public String getInstrumentExchnage() {
        return instrumentExchnage;
    }

    public Instrument instrumentExchnage(String instrumentExchnage) {
        this.instrumentExchnage = instrumentExchnage;
        return this;
    }

    public void setInstrumentExchnage(String instrumentExchnage) {
        this.instrumentExchnage = instrumentExchnage;
    }

    public String getInstrumentDescription() {
        return instrumentDescription;
    }

    public Instrument instrumentDescription(String instrumentDescription) {
        this.instrumentDescription = instrumentDescription;
        return this;
    }

    public void setInstrumentDescription(String instrumentDescription) {
        this.instrumentDescription = instrumentDescription;
    }

    public String getInstrumentDataFrom() {
        return instrumentDataFrom;
    }

    public Instrument instrumentDataFrom(String instrumentDataFrom) {
        this.instrumentDataFrom = instrumentDataFrom;
        return this;
    }

    public void setInstrumentDataFrom(String instrumentDataFrom) {
        this.instrumentDataFrom = instrumentDataFrom;
    }

    public Boolean isInstrumentActive() {
        return instrumentActive;
    }

    public Instrument instrumentActive(Boolean instrumentActive) {
        this.instrumentActive = instrumentActive;
        return this;
    }

    public void setInstrumentActive(Boolean instrumentActive) {
        this.instrumentActive = instrumentActive;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public Instrument dateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateInactive() {
        return dateInactive;
    }

    public Instrument dateInactive(LocalDate dateInactive) {
        this.dateInactive = dateInactive;
        return this;
    }

    public void setDateInactive(LocalDate dateInactive) {
        this.dateInactive = dateInactive;
    }

    public Position getPosition() {
        return position;
    }

    public Instrument position(Position position) {
        this.position = position;
        return this;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Post getPost() {
        return post;
    }

    public Instrument post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Set<Watchlist> getWatchlists() {
        return watchlists;
    }

    public Instrument watchlists(Set<Watchlist> watchlists) {
        this.watchlists = watchlists;
        return this;
    }

    public Instrument addWatchlist(Watchlist watchlist) {
        this.watchlists.add(watchlist);
        watchlist.getInstruments().add(this);
        return this;
    }

    public Instrument removeWatchlist(Watchlist watchlist) {
        this.watchlists.remove(watchlist);
        watchlist.getInstruments().remove(this);
        return this;
    }

    public void setWatchlists(Set<Watchlist> watchlists) {
        this.watchlists = watchlists;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Instrument)) {
            return false;
        }
        return id != null && id.equals(((Instrument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Instrument{" +
            "id=" + getId() +
            ", dataProvider='" + getDataProvider() + "'" +
            ", instrumentTicker='" + getInstrumentTicker() + "'" +
            ", instrumentExchnage='" + getInstrumentExchnage() + "'" +
            ", instrumentDescription='" + getInstrumentDescription() + "'" +
            ", instrumentDataFrom='" + getInstrumentDataFrom() + "'" +
            ", instrumentActive='" + isInstrumentActive() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            ", dateInactive='" + getDateInactive() + "'" +
            "}";
    }
}
