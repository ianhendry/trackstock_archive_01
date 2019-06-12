package com.jugglerapps.stocktrack.service.impl;

import com.jugglerapps.stocktrack.service.VideoPostService;
import com.jugglerapps.stocktrack.domain.VideoPost;
import com.jugglerapps.stocktrack.repository.VideoPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link VideoPost}.
 */
@Service
@Transactional
public class VideoPostServiceImpl implements VideoPostService {

    private final Logger log = LoggerFactory.getLogger(VideoPostServiceImpl.class);

    private final VideoPostRepository videoPostRepository;

    public VideoPostServiceImpl(VideoPostRepository videoPostRepository) {
        this.videoPostRepository = videoPostRepository;
    }

    /**
     * Save a videoPost.
     *
     * @param videoPost the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VideoPost save(VideoPost videoPost) {
        log.debug("Request to save VideoPost : {}", videoPost);
        return videoPostRepository.save(videoPost);
    }

    /**
     * Get all the videoPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VideoPost> findAll(Pageable pageable) {
        log.debug("Request to get all VideoPosts");
        return videoPostRepository.findAll(pageable);
    }


    /**
     * Get one videoPost by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VideoPost> findOne(Long id) {
        log.debug("Request to get VideoPost : {}", id);
        return videoPostRepository.findById(id);
    }

    /**
     * Delete the videoPost by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VideoPost : {}", id);
        videoPostRepository.deleteById(id);
    }
}
