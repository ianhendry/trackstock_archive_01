package com.jugglerapps.stocktrack.service;

import com.jugglerapps.stocktrack.domain.VideoPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link VideoPost}.
 */
public interface VideoPostService {

    /**
     * Save a videoPost.
     *
     * @param videoPost the entity to save.
     * @return the persisted entity.
     */
    VideoPost save(VideoPost videoPost);

    /**
     * Get all the videoPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VideoPost> findAll(Pageable pageable);


    /**
     * Get the "id" videoPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VideoPost> findOne(Long id);

    /**
     * Delete the "id" videoPost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
