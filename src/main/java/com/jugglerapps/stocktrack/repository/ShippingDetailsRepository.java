package com.jugglerapps.stocktrack.repository;

import com.jugglerapps.stocktrack.domain.ShippingDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ShippingDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShippingDetailsRepository extends JpaRepository<ShippingDetails, Long> {

    @Query("select shippingDetails from ShippingDetails shippingDetails where shippingDetails.user.login = ?#{principal.username}")
    List<ShippingDetails> findByUserIsCurrentUser();

}
