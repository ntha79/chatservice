package com.hdmon.chatservice.web.rest;

import com.hdmon.chatservice.ChatserviceApp;
import com.hdmon.chatservice.config.SecurityBeanOverrideConfiguration;
import com.hdmon.chatservice.domain.Friends;
import com.hdmon.chatservice.domain.extents.extFriendMemberLists;
import com.hdmon.chatservice.repository.FriendsRepository;
import com.hdmon.chatservice.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.hdmon.chatservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FriendsResource REST controller.
 *
 * @see FriendsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatserviceApp.class, SecurityBeanOverrideConfiguration.class})
public class FriendsResourceIntTest {

    private static final String DEFAULT_SEQ_ID = "AAAAAAAAAA";
    private static final String UPDATED_SEQ_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_OWNER_ID = 1L;
    private static final Long UPDATED_OWNER_ID = 2L;

    private static final String DEFAULT_OWNER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_LOGIN = "BBBBBBBBBB";

    private static final List<extFriendMemberLists> DEFAULT_FRIEND_LISTS = new ArrayList<>();
    private static final List<extFriendMemberLists> UPDATED_FRIEND_LISTS = new ArrayList<>();

    private static final Integer DEFAULT_FRIEND_COUNT = 1;
    private static final Integer UPDATED_FRIEND_COUNT = 2;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_UNIX_TIME = 1L;
    private static final Long UPDATED_CREATED_UNIX_TIME = 2L;

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_LAST_MODIFIED_UNIX_TIME = 1L;
    private static final Long UPDATED_LAST_MODIFIED_UNIX_TIME = 2L;

    private static final Integer DEFAULT_REPORT_DAY = 1;
    private static final Integer UPDATED_REPORT_DAY = 2;

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFriendsMockMvc;

    private Friends friends;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FriendsResource friendsResource = new FriendsResource(friendsRepository);
        this.restFriendsMockMvc = MockMvcBuilders.standaloneSetup(friendsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Friends createEntity() {
        Friends friends = new Friends()
            .seqId(DEFAULT_SEQ_ID)
            .ownerId(DEFAULT_OWNER_ID)
            .ownerLogin(DEFAULT_OWNER_LOGIN)
            .friendLists(DEFAULT_FRIEND_LISTS)
            .friendCount(DEFAULT_FRIEND_COUNT)
            .createdUnixTime(DEFAULT_CREATED_UNIX_TIME)
            .lastModifiedUnixTime(DEFAULT_LAST_MODIFIED_UNIX_TIME)
            .reportDay(DEFAULT_REPORT_DAY);
        return friends;
    }

    @Before
    public void initTest() {
        friendsRepository.deleteAll();
        friends = createEntity();
    }

    @Test
    public void createFriends() throws Exception {
        int databaseSizeBeforeCreate = friendsRepository.findAll().size();

        // Create the Friends
        restFriendsMockMvc.perform(post("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friends)))
            .andExpect(status().isCreated());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeCreate + 1);
        Friends testFriends = friendsList.get(friendsList.size() - 1);
        assertThat(testFriends.getSeqId()).isEqualTo(DEFAULT_SEQ_ID);
        assertThat(testFriends.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testFriends.getOwnerLogin()).isEqualTo(DEFAULT_OWNER_LOGIN);
        assertThat(testFriends.getFriendLists()).isEqualTo(DEFAULT_FRIEND_LISTS);
        assertThat(testFriends.getFriendCount()).isEqualTo(DEFAULT_FRIEND_COUNT);
        assertThat(testFriends.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFriends.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFriends.getCreatedUnixTime()).isEqualTo(DEFAULT_CREATED_UNIX_TIME);
        assertThat(testFriends.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testFriends.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testFriends.getLastModifiedUnixTime()).isEqualTo(DEFAULT_LAST_MODIFIED_UNIX_TIME);
        assertThat(testFriends.getReportDay()).isEqualTo(DEFAULT_REPORT_DAY);
    }

    @Test
    public void createFriendsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = friendsRepository.findAll().size();

        // Create the Friends with an existing ID
        friends.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFriendsMockMvc.perform(post("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friends)))
            .andExpect(status().isBadRequest());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllFriends() throws Exception {
        // Initialize the database
        friendsRepository.save(friends);

        // Get all the friendsList
        restFriendsMockMvc.perform(get("/api/friends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friends.getId())))
            .andExpect(jsonPath("$.[*].seqId").value(hasItem(DEFAULT_SEQ_ID.toString())))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID.intValue())))
            .andExpect(jsonPath("$.[*].ownerLogin").value(hasItem(DEFAULT_OWNER_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].friendLists").value(hasItem(DEFAULT_FRIEND_LISTS.toString())))
            .andExpect(jsonPath("$.[*].friendCount").value(hasItem(DEFAULT_FRIEND_COUNT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdUnixTime").value(hasItem(DEFAULT_CREATED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedUnixTime").value(hasItem(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].reportDay").value(hasItem(DEFAULT_REPORT_DAY)));
    }

    @Test
    public void getFriends() throws Exception {
        // Initialize the database
        friendsRepository.save(friends);

        // Get the friends
        restFriendsMockMvc.perform(get("/api/friends/{id}", friends.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(friends.getId()))
            .andExpect(jsonPath("$.seqId").value(DEFAULT_SEQ_ID.toString()))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID.intValue()))
            .andExpect(jsonPath("$.ownerLogin").value(DEFAULT_OWNER_LOGIN.toString()))
            .andExpect(jsonPath("$.friendLists").value(DEFAULT_FRIEND_LISTS.toString()))
            .andExpect(jsonPath("$.friendCount").value(DEFAULT_FRIEND_COUNT))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdUnixTime").value(DEFAULT_CREATED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedUnixTime").value(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.reportDay").value(DEFAULT_REPORT_DAY));
    }

    @Test
    public void getNonExistingFriends() throws Exception {
        // Get the friends
        restFriendsMockMvc.perform(get("/api/friends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFriends() throws Exception {
        // Initialize the database
        friendsRepository.save(friends);
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();

        // Update the friends
        Friends updatedFriends = friendsRepository.findOne(friends.getId());
        updatedFriends
            .seqId(UPDATED_SEQ_ID)
            .ownerId(UPDATED_OWNER_ID)
            .ownerLogin(UPDATED_OWNER_LOGIN)
            .friendLists(UPDATED_FRIEND_LISTS)
            .friendCount(UPDATED_FRIEND_COUNT)
            .createdUnixTime(UPDATED_CREATED_UNIX_TIME)
            .lastModifiedUnixTime(UPDATED_LAST_MODIFIED_UNIX_TIME)
            .reportDay(UPDATED_REPORT_DAY);

        restFriendsMockMvc.perform(put("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFriends)))
            .andExpect(status().isOk());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        Friends testFriends = friendsList.get(friendsList.size() - 1);
        assertThat(testFriends.getSeqId()).isEqualTo(UPDATED_SEQ_ID);
        assertThat(testFriends.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testFriends.getOwnerLogin()).isEqualTo(UPDATED_OWNER_LOGIN);
        assertThat(testFriends.getFriendLists()).isEqualTo(UPDATED_FRIEND_LISTS);
        assertThat(testFriends.getFriendCount()).isEqualTo(UPDATED_FRIEND_COUNT);
        assertThat(testFriends.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFriends.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFriends.getCreatedUnixTime()).isEqualTo(UPDATED_CREATED_UNIX_TIME);
        assertThat(testFriends.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testFriends.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testFriends.getLastModifiedUnixTime()).isEqualTo(UPDATED_LAST_MODIFIED_UNIX_TIME);
        assertThat(testFriends.getReportDay()).isEqualTo(UPDATED_REPORT_DAY);
    }

    @Test
    public void updateNonExistingFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();

        // Create the Friends

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFriendsMockMvc.perform(put("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friends)))
            .andExpect(status().isCreated());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFriends() throws Exception {
        // Initialize the database
        friendsRepository.save(friends);
        int databaseSizeBeforeDelete = friendsRepository.findAll().size();

        // Get the friends
        restFriendsMockMvc.perform(delete("/api/friends/{id}", friends.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Friends.class);
        Friends friends1 = new Friends();
        friends1.setId("id1");
        Friends friends2 = new Friends();
        friends2.setId(friends1.getId());
        assertThat(friends1).isEqualTo(friends2);
        friends2.setId("id2");
        assertThat(friends1).isNotEqualTo(friends2);
        friends1.setId(null);
        assertThat(friends1).isNotEqualTo(friends2);
    }
}
