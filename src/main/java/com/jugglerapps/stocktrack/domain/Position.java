package com.jugglerapps.stocktrack.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Position.
 */
@Entity
@Table(name = "position")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "position_trade_plan", nullable = false)
    private String positionTradePlan;

    @NotNull
    @Column(name = "position_open_date", nullable = false)
    private LocalDate positionOpenDate;

    @NotNull
    @Column(name = "position_open_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal positionOpenPrice;

    @Column(name = "position_close_date")
    private LocalDate positionCloseDate;

    @Column(name = "position_close_price", precision = 21, scale = 2)
    private BigDecimal positionClosePrice;

    @Column(name = "positio_win_loss")
    private Boolean positioWinLoss;

    @Column(name = "position_profit_amount", precision = 21, scale = 2)
    private BigDecimal positionProfitAmount;

    @Column(name = "position_closing_thought")
    private String positionClosingThought;

    @OneToOne
    @JoinColumn(unique = true)
    private Instrument instrument;

    @ManyToOne
    @JsonIgnoreProperties("positions")
    private TradingAccount tradingAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionTradePlan() {
        return positionTradePlan;
    }

    public Position positionTradePlan(String positionTradePlan) {
        this.positionTradePlan = positionTradePlan;
        return this;
    }

    public void setPositionTradePlan(String positionTradePlan) {
        this.positionTradePlan = positionTradePlan;
    }

    public LocalDate getPositionOpenDate() {
        return positionOpenDate;
    }

    public Position positionOpenDate(LocalDate positionOpenDate) {
        this.positionOpenDate = positionOpenDate;
        return this;
    }

    public void setPositionOpenDate(LocalDate positionOpenDate) {
        this.positionOpenDate = positionOpenDate;
    }

    public BigDecimal getPositionOpenPrice() {
        return positionOpenPrice;
    }

    public Position positionOpenPrice(BigDecimal positionOpenPrice) {
        this.positionOpenPrice = positionOpenPrice;
        return this;
    }

    public void setPositionOpenPrice(BigDecimal positionOpenPrice) {
        this.positionOpenPrice = positionOpenPrice;
    }

    public LocalDate getPositionCloseDate() {
        return positionCloseDate;
    }

    public Position positionCloseDate(LocalDate positionCloseDate) {
        this.positionCloseDate = positionCloseDate;
        return this;
    }

    public void setPositionCloseDate(LocalDate positionCloseDate) {
        this.positionCloseDate = positionCloseDate;
    }

    public BigDecimal getPositionClosePrice() {
        return positionClosePrice;
    }

    public Position positionClosePrice(BigDecimal positionClosePrice) {
        this.positionClosePrice = positionClosePrice;
        return this;
    }

    public void setPositionClosePrice(BigDecimal positionClosePrice) {
        this.positionClosePrice = positionClosePrice;
    }

    public Boolean isPositioWinLoss() {
        return positioWinLoss;
    }

    public Position positioWinLoss(Boolean positioWinLoss) {
        this.positioWinLoss = positioWinLoss;
        return this;
    }

    public void setPositioWinLoss(Boolean positioWinLoss) {
        this.positioWinLoss = positioWinLoss;
    }

    public BigDecimal getPositionProfitAmount() {
        return positionProfitAmount;
    }

    public Position positionProfitAmount(BigDecimal positionProfitAmount) {
        this.positionProfitAmount = positionProfitAmount;
        return this;
    }

    public void setPositionProfitAmount(BigDecimal positionProfitAmount) {
        this.positionProfitAmount = positionProfitAmount;
    }

    public String getPositionClosingThought() {
        return positionClosingThought;
    }

    public Position positionClosingThought(String positionClosingThought) {
        this.positionClosingThought = positionClosingThought;
        return this;
    }

    public void setPositionClosingThought(String positionClosingThought) {
        this.positionClosingThought = positionClosingThought;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Position instrument(Instrument instrument) {
        this.instrument = instrument;
        return this;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public TradingAccount getTradingAccount() {
        return tradingAccount;
    }

    public Position tradingAccount(TradingAccount tradingAccount) {
        this.tradingAccount = tradingAccount;
        return this;
    }

    public void setTradingAccount(TradingAccount tradingAccount) {
        this.tradingAccount = tradingAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        return id != null && id.equals(((Position) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Position{" +
            "id=" + getId() +
            ", positionTradePlan='" + getPositionTradePlan() + "'" +
            ", positionOpenDate='" + getPositionOpenDate() + "'" +
            ", positionOpenPrice=" + getPositionOpenPrice() +
            ", positionCloseDate='" + getPositionCloseDate() + "'" +
            ", positionClosePrice=" + getPositionClosePrice() +
            ", positioWinLoss='" + isPositioWinLoss() + "'" +
            ", positionProfitAmount=" + getPositionProfitAmount() +
            ", positionClosingThought='" + getPositionClosingThought() + "'" +
            "}";
    }
}
