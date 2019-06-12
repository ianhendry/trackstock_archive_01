package com.jugglerapps.stocktrack.repository;

import com.jugglerapps.stocktrack.domain.VideoPost;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the VideoPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoPostRepository extends JpaRepository<VideoPost, Long> {

    @Query("select videoPost from VideoPost videoPost where videoPost.user.login = ?#{principal.username}")
    List<VideoPost> findByUserIsCurrentUser();

}
