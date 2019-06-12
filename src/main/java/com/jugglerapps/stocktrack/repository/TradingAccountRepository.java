package com.jugglerapps.stocktrack.repository;

import com.jugglerapps.stocktrack.domain.TradingAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the TradingAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TradingAccountRepository extends JpaRepository<TradingAccount, Long> {

    @Query("select tradingAccount from TradingAccount tradingAccount where tradingAccount.user.login = ?#{principal.username}")
    List<TradingAccount> findByUserIsCurrentUser();

}
