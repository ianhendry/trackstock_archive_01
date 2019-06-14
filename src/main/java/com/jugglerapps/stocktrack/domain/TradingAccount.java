package com.jugglerapps.stocktrack.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TradingAccount.
 */
@Entity
@Table(name = "trading_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TradingAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "account_name", nullable = false)
    private String accountName;

    @NotNull
    @Column(name = "account_real", nullable = false)
    private Boolean accountReal;

    @NotNull
    @Column(name = "account_open_date", nullable = false)
    private LocalDate accountOpenDate;

    @NotNull
    @Column(name = "account_balance", precision = 21, scale = 2, nullable = false)
    private BigDecimal accountBalance;

    @Column(name = "account_close_date")
    private LocalDate accountCloseDate;

    @OneToMany(mappedBy = "tradingAccount")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Position> positions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("tradingAccounts")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public TradingAccount accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Boolean isAccountReal() {
        return accountReal;
    }

    public TradingAccount accountReal(Boolean accountReal) {
        this.accountReal = accountReal;
        return this;
    }

    public void setAccountReal(Boolean accountReal) {
        this.accountReal = accountReal;
    }

    public LocalDate getAccountOpenDate() {
        return accountOpenDate;
    }

    public TradingAccount accountOpenDate(LocalDate accountOpenDate) {
        this.accountOpenDate = accountOpenDate;
        return this;
    }

    public void setAccountOpenDate(LocalDate accountOpenDate) {
        this.accountOpenDate = accountOpenDate;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public TradingAccount accountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
        return this;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public LocalDate getAccountCloseDate() {
        return accountCloseDate;
    }

    public TradingAccount accountCloseDate(LocalDate accountCloseDate) {
        this.accountCloseDate = accountCloseDate;
        return this;
    }

    public void setAccountCloseDate(LocalDate accountCloseDate) {
        this.accountCloseDate = accountCloseDate;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public TradingAccount positions(Set<Position> positions) {
        this.positions = positions;
        return this;
    }

    public TradingAccount addPosition(Position position) {
        this.positions.add(position);
        position.setTradingAccount(this);
        return this;
    }

    public TradingAccount removePosition(Position position) {
        this.positions.remove(position);
        position.setTradingAccount(null);
        return this;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public User getUser() {
        return user;
    }

    public TradingAccount user(User user) {
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
        if (!(o instanceof TradingAccount)) {
            return false;
        }
        return id != null && id.equals(((TradingAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TradingAccount{" +
            "id=" + getId() +
            ", accountName='" + getAccountName() + "'" +
            ", accountReal='" + isAccountReal() + "'" +
            ", accountOpenDate='" + getAccountOpenDate() + "'" +
            ", accountBalance=" + getAccountBalance() +
            ", accountCloseDate='" + getAccountCloseDate() + "'" +
            "}";
    }
}
