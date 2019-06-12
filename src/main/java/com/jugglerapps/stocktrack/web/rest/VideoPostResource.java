package com.jugglerapps.stocktrack.web.rest;

import com.jugglerapps.stocktrack.domain.VideoPost;
import com.jugglerapps.stocktrack.service.VideoPostService;
import com.jugglerapps.stocktrack.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jugglerapps.stocktrack.domain.VideoPost}.
 */
@RestController
@RequestMapping("/api")
public class VideoPostResource {

    private final Logger log = LoggerFactory.getLogger(VideoPostResource.class);

    private static final String ENTITY_NAME = "videoPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VideoPostService videoPostService;

    public VideoPostResource(VideoPostService videoPostService) {
        this.videoPostService = videoPostService;
    }

    /**
     * {@code POST  /video-posts} : Create a new videoPost.
     *
     * @param videoPost the videoPost to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new videoPost, or with status {@code 400 (Bad Request)} if the videoPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/video-posts")
    public ResponseEntity<VideoPost> createVideoPost(@Valid @RequestBody VideoPost videoPost) throws URISyntaxException {
        log.debug("REST request to save VideoPost : {}", videoPost);
        if (videoPost.getId() != null) {
            throw new BadRequestAlertException("A new videoPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VideoPost result = videoPostService.save(videoPost);
        return ResponseEntity.created(new URI("/api/video-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /video-posts} : Updates an existing videoPost.
     *
     * @param videoPost the videoPost to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videoPost,
     * or with status {@code 400 (Bad Request)} if the videoPost is not valid,
     * or with status {@code 500 (Internal Server Error)} if the videoPost couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/video-posts")
    public ResponseEntity<VideoPost> updateVideoPost(@Valid @RequestBody VideoPost videoPost) throws URISyntaxException {
        log.debug("REST request to update VideoPost : {}", videoPost);
        if (videoPost.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VideoPost result = videoPostService.save(videoPost);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, videoPost.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /video-posts} : get all the videoPosts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videoPosts in body.
     */
    @GetMapping("/video-posts")
    public ResponseEntity<List<VideoPost>> getAllVideoPosts(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of VideoPosts");
        Page<VideoPost> page = videoPostService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /video-posts/:id} : get the "id" videoPost.
     *
     * @param id the id of the videoPost to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the videoPost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/video-posts/{id}")
    public ResponseEntity<VideoPost> getVideoPost(@PathVariable Long id) {
        log.debug("REST request to get VideoPost : {}", id);
        Optional<VideoPost> videoPost = videoPostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(videoPost);
    }

    /**
     * {@code DELETE  /video-posts/:id} : delete the "id" videoPost.
     *
     * @param id the id of the videoPost to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/video-posts/{id}")
    public ResponseEntity<Void> deleteVideoPost(@PathVariable Long id) {
        log.debug("REST request to delete VideoPost : {}", id);
        videoPostService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
