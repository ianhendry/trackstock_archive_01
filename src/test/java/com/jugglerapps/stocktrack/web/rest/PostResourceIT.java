package com.jugglerapps.stocktrack.web.rest;

import com.jugglerapps.stocktrack.TrackstockApp;
import com.jugglerapps.stocktrack.domain.Post;
import com.jugglerapps.stocktrack.repository.PostRepository;
import com.jugglerapps.stocktrack.service.PostService;
import com.jugglerapps.stocktrack.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.jugglerapps.stocktrack.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PostResource} REST controller.
 */
@SpringBootTest(classes = TrackstockApp.class)
public class PostResourceIT {

    private static final String DEFAULT_POST_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_POST_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_POST_BODY = "AAAAAAAAAA";
    private static final String UPDATED_POST_BODY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ADDED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ADDED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_APPROVED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_APPROVED = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_MEDIA_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIA_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIA_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIA_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_MEDIA_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIA_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIA_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIA_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_MEDIA_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIA_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIA_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIA_3_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_MEDIA_4 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIA_4 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIA_4_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIA_4_CONTENT_TYPE = "image/png";

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPostMockMvc;

    private Post post;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PostResource postResource = new PostResource(postService);
        this.restPostMockMvc = MockMvcBuilders.standaloneSetup(postResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createEntity(EntityManager em) {
        Post post = new Post()
            .postTitle(DEFAULT_POST_TITLE)
            .postBody(DEFAULT_POST_BODY)
            .dateAdded(DEFAULT_DATE_ADDED)
            .dateApproved(DEFAULT_DATE_APPROVED)
            .media1(DEFAULT_MEDIA_1)
            .media1ContentType(DEFAULT_MEDIA_1_CONTENT_TYPE)
            .media2(DEFAULT_MEDIA_2)
            .media2ContentType(DEFAULT_MEDIA_2_CONTENT_TYPE)
            .media3(DEFAULT_MEDIA_3)
            .media3ContentType(DEFAULT_MEDIA_3_CONTENT_TYPE)
            .media4(DEFAULT_MEDIA_4)
            .media4ContentType(DEFAULT_MEDIA_4_CONTENT_TYPE);
        return post;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createUpdatedEntity(EntityManager em) {
        Post post = new Post()
            .postTitle(UPDATED_POST_TITLE)
            .postBody(UPDATED_POST_BODY)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateApproved(UPDATED_DATE_APPROVED)
            .media1(UPDATED_MEDIA_1)
            .media1ContentType(UPDATED_MEDIA_1_CONTENT_TYPE)
            .media2(UPDATED_MEDIA_2)
            .media2ContentType(UPDATED_MEDIA_2_CONTENT_TYPE)
            .media3(UPDATED_MEDIA_3)
            .media3ContentType(UPDATED_MEDIA_3_CONTENT_TYPE)
            .media4(UPDATED_MEDIA_4)
            .media4ContentType(UPDATED_MEDIA_4_CONTENT_TYPE);
        return post;
    }

    @BeforeEach
    public void initTest() {
        post = createEntity(em);
    }

    @Test
    @Transactional
    public void createPost() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();

        // Create the Post
        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(post)))
            .andExpect(status().isCreated());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate + 1);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getPostTitle()).isEqualTo(DEFAULT_POST_TITLE);
        assertThat(testPost.getPostBody()).isEqualTo(DEFAULT_POST_BODY);
        assertThat(testPost.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testPost.getDateApproved()).isEqualTo(DEFAULT_DATE_APPROVED);
        assertThat(testPost.getMedia1()).isEqualTo(DEFAULT_MEDIA_1);
        assertThat(testPost.getMedia1ContentType()).isEqualTo(DEFAULT_MEDIA_1_CONTENT_TYPE);
        assertThat(testPost.getMedia2()).isEqualTo(DEFAULT_MEDIA_2);
        assertThat(testPost.getMedia2ContentType()).isEqualTo(DEFAULT_MEDIA_2_CONTENT_TYPE);
        assertThat(testPost.getMedia3()).isEqualTo(DEFAULT_MEDIA_3);
        assertThat(testPost.getMedia3ContentType()).isEqualTo(DEFAULT_MEDIA_3_CONTENT_TYPE);
        assertThat(testPost.getMedia4()).isEqualTo(DEFAULT_MEDIA_4);
        assertThat(testPost.getMedia4ContentType()).isEqualTo(DEFAULT_MEDIA_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();

        // Create the Post with an existing ID
        post.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(post)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPostTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setPostTitle(null);

        // Create the Post, which fails.

        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(post)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateAddedIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setDateAdded(null);

        // Create the Post, which fails.

        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(post)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPosts() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList
        restPostMockMvc.perform(get("/api/posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
            .andExpect(jsonPath("$.[*].postTitle").value(hasItem(DEFAULT_POST_TITLE.toString())))
            .andExpect(jsonPath("$.[*].postBody").value(hasItem(DEFAULT_POST_BODY.toString())))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].dateApproved").value(hasItem(DEFAULT_DATE_APPROVED.toString())))
            .andExpect(jsonPath("$.[*].media1ContentType").value(hasItem(DEFAULT_MEDIA_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].media1").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIA_1))))
            .andExpect(jsonPath("$.[*].media2ContentType").value(hasItem(DEFAULT_MEDIA_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].media2").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIA_2))))
            .andExpect(jsonPath("$.[*].media3ContentType").value(hasItem(DEFAULT_MEDIA_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].media3").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIA_3))))
            .andExpect(jsonPath("$.[*].media4ContentType").value(hasItem(DEFAULT_MEDIA_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].media4").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIA_4))));
    }
    
    @Test
    @Transactional
    public void getPost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", post.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(post.getId().intValue()))
            .andExpect(jsonPath("$.postTitle").value(DEFAULT_POST_TITLE.toString()))
            .andExpect(jsonPath("$.postBody").value(DEFAULT_POST_BODY.toString()))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED.toString()))
            .andExpect(jsonPath("$.dateApproved").value(DEFAULT_DATE_APPROVED.toString()))
            .andExpect(jsonPath("$.media1ContentType").value(DEFAULT_MEDIA_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.media1").value(Base64Utils.encodeToString(DEFAULT_MEDIA_1)))
            .andExpect(jsonPath("$.media2ContentType").value(DEFAULT_MEDIA_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.media2").value(Base64Utils.encodeToString(DEFAULT_MEDIA_2)))
            .andExpect(jsonPath("$.media3ContentType").value(DEFAULT_MEDIA_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.media3").value(Base64Utils.encodeToString(DEFAULT_MEDIA_3)))
            .andExpect(jsonPath("$.media4ContentType").value(DEFAULT_MEDIA_4_CONTENT_TYPE))
            .andExpect(jsonPath("$.media4").value(Base64Utils.encodeToString(DEFAULT_MEDIA_4)));
    }

    @Test
    @Transactional
    public void getNonExistingPost() throws Exception {
        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePost() throws Exception {
        // Initialize the database
        postService.save(post);

        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Update the post
        Post updatedPost = postRepository.findById(post.getId()).get();
        // Disconnect from session so that the updates on updatedPost are not directly saved in db
        em.detach(updatedPost);
        updatedPost
            .postTitle(UPDATED_POST_TITLE)
            .postBody(UPDATED_POST_BODY)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateApproved(UPDATED_DATE_APPROVED)
            .media1(UPDATED_MEDIA_1)
            .media1ContentType(UPDATED_MEDIA_1_CONTENT_TYPE)
            .media2(UPDATED_MEDIA_2)
            .media2ContentType(UPDATED_MEDIA_2_CONTENT_TYPE)
            .media3(UPDATED_MEDIA_3)
            .media3ContentType(UPDATED_MEDIA_3_CONTENT_TYPE)
            .media4(UPDATED_MEDIA_4)
            .media4ContentType(UPDATED_MEDIA_4_CONTENT_TYPE);

        restPostMockMvc.perform(put("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPost)))
            .andExpect(status().isOk());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getPostTitle()).isEqualTo(UPDATED_POST_TITLE);
        assertThat(testPost.getPostBody()).isEqualTo(UPDATED_POST_BODY);
        assertThat(testPost.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testPost.getDateApproved()).isEqualTo(UPDATED_DATE_APPROVED);
        assertThat(testPost.getMedia1()).isEqualTo(UPDATED_MEDIA_1);
        assertThat(testPost.getMedia1ContentType()).isEqualTo(UPDATED_MEDIA_1_CONTENT_TYPE);
        assertThat(testPost.getMedia2()).isEqualTo(UPDATED_MEDIA_2);
        assertThat(testPost.getMedia2ContentType()).isEqualTo(UPDATED_MEDIA_2_CONTENT_TYPE);
        assertThat(testPost.getMedia3()).isEqualTo(UPDATED_MEDIA_3);
        assertThat(testPost.getMedia3ContentType()).isEqualTo(UPDATED_MEDIA_3_CONTENT_TYPE);
        assertThat(testPost.getMedia4()).isEqualTo(UPDATED_MEDIA_4);
        assertThat(testPost.getMedia4ContentType()).isEqualTo(UPDATED_MEDIA_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Create the Post

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMockMvc.perform(put("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(post)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePost() throws Exception {
        // Initialize the database
        postService.save(post);

        int databaseSizeBeforeDelete = postRepository.findAll().size();

        // Delete the post
        restPostMockMvc.perform(delete("/api/posts/{id}", post.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Post.class);
        Post post1 = new Post();
        post1.setId(1L);
        Post post2 = new Post();
        post2.setId(post1.getId());
        assertThat(post1).isEqualTo(post2);
        post2.setId(2L);
        assertThat(post1).isNotEqualTo(post2);
        post1.setId(null);
        assertThat(post1).isNotEqualTo(post2);
    }
}
