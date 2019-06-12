package com.jugglerapps.stocktrack.repository;

import com.jugglerapps.stocktrack.domain.Watchlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Watchlist entity.
 */
@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    @Query(value = "select distinct watchlist from Watchlist watchlist left join fetch watchlist.instruments",
        countQuery = "select count(distinct watchlist) from Watchlist watchlist")
    Page<Watchlist> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct watchlist from Watchlist watchlist left join fetch watchlist.instruments")
    List<Watchlist> findAllWithEagerRelationships();

    @Query("select watchlist from Watchlist watchlist left join fetch watchlist.instruments where watchlist.id =:id")
    Optional<Watchlist> findOneWithEagerRelationships(@Param("id") Long id);

}
