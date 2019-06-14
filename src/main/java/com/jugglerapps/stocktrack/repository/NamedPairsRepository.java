package com.jugglerapps.stocktrack.repository;

import com.jugglerapps.stocktrack.domain.NamedPairs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the NamedPairs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NamedPairsRepository extends JpaRepository<NamedPairs, Long> {

    @Query("select namedPairs from NamedPairs namedPairs where namedPairs.user.login = ?#{principal.username}")
    List<NamedPairs> findByUserIsCurrentUser();

}
