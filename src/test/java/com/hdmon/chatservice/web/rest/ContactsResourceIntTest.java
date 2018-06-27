package com.hdmon.chatservice.web.rest;

import com.hdmon.chatservice.ChatserviceApp;
import com.hdmon.chatservice.config.SecurityBeanOverrideConfiguration;
import com.hdmon.chatservice.domain.FriendsEntity;
import com.hdmon.chatservice.domain.extents.extFriendContactEntity;
import com.hdmon.chatservice.domain.extents.extFriendMemberEntity;
import com.hdmon.chatservice.repository.FriendsRepository;
import com.hdmon.chatservice.service.FriendsService;
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
 * Test class for the ContactsResource REST controller.
 *
 * @see ContactsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatserviceApp.class, SecurityBeanOverrideConfiguration.class})
public class ContactsResourceIntTest {

    private static final String DEFAULT_SEQ_ID = "AAAAAAAAAA";
    private static final String UPDATED_SEQ_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_LOGIN = "BBBBBBBBBB";

    private static final List<extFriendContactEntity> DEFAULT_FRIEND_LISTS = new ArrayList<>();
    private static final List<extFriendContactEntity> UPDATED_FRIEND_LISTS = new ArrayList<>();

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

    private static final Long DEFAULT_LAST_MODIFIED_TIME = 1L;
    private static final Long UPDATED_LAST_MODIFIED_TIME = 2L;

    private static final Integer DEFAULT_REPORT_DAY = 1;
    private static final Integer UPDATED_REPORT_DAY = 2;

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private FriendsService friendsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restContactsMockMvc;

    private FriendsEntity contacts;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FriendsResource friendsResource = new FriendsResource(friendsService);
        this.restContactsMockMvc = MockMvcBuilders.standaloneSetup(friendsService)
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
    public static FriendsEntity createEntity() {
        FriendsEntity friends = new FriendsEntity()
            .ownerUsername(DEFAULT_OWNER_USERNAME)
            .friendLists(DEFAULT_FRIEND_LISTS)
            .createdUnixTime(DEFAULT_CREATED_UNIX_TIME)
            .lastModifiedUnixTime(DEFAULT_LAST_MODIFIED_TIME)
            .reportDay(DEFAULT_REPORT_DAY);
        return friends;
    }

    @Before
    public void initTest() {
        friendsRepository.deleteAll();
        contacts = createEntity();
    }

    @Test
    public void createContacts() throws Exception {
        int databaseSizeBeforeCreate = friendsRepository.findAll().size();

        // Create the Contacts
        restContactsMockMvc.perform(post("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contacts)))
            .andExpect(status().isCreated());

        // Validate the Contacts in the database
        List<FriendsEntity> contactsList = friendsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeCreate + 1);
        FriendsEntity testContacts = contactsList.get(contactsList.size() - 1);
        assertThat(testContacts.getOwnerUsername()).isEqualTo(DEFAULT_OWNER_USERNAME);
        assertThat(testContacts.getFriendLists()).isEqualTo(DEFAULT_FRIEND_LISTS);
        assertThat(testContacts.getCreatedTime()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testContacts.getLastModifiedTime()).isEqualTo(DEFAULT_LAST_MODIFIED_TIME);
        assertThat(testContacts.getReportDay()).isEqualTo(DEFAULT_REPORT_DAY);
    }

    @Test
    public void createContactsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = friendsRepository.findAll().size();

        // Create the Friends with an existing ID
        contacts.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactsMockMvc.perform(post("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contacts)))
            .andExpect(status().isBadRequest());

        // Validate the Contacts in the database
        List<FriendsEntity> contactsList = friendsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllContacts() throws Exception {
        // Initialize the database
        friendsRepository.save(contacts);

        // Get all the contactsList
        restContactsMockMvc.perform(get("/api/friends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contacts.getId())))
            .andExpect(jsonPath("$.[*].ownerUsername").value(hasItem(DEFAULT_OWNER_USERNAME)))
            .andExpect(jsonPath("$.[*].friendLists").value(hasItem(DEFAULT_FRIEND_LISTS.toString())))
            .andExpect(jsonPath("$.[*].friendCount").value(hasItem(DEFAULT_FRIEND_COUNT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdUnixTime").value(hasItem(DEFAULT_CREATED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedUnixTime").value(hasItem(DEFAULT_LAST_MODIFIED_TIME.intValue())))
            .andExpect(jsonPath("$.[*].reportDay").value(hasItem(DEFAULT_REPORT_DAY)));
    }

    @Test
    public void getContacts() throws Exception {
        // Initialize the database
        friendsRepository.save(contacts);

        // Get the contacts
        restContactsMockMvc.perform(get("/api/friends/{id}", contacts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contacts.getId()))
            .andExpect(jsonPath("$.seqId").value(DEFAULT_SEQ_ID.toString()))
            .andExpect(jsonPath("$.ownerUsername").value(DEFAULT_OWNER_USERNAME))
            .andExpect(jsonPath("$.ownerLogin").value(DEFAULT_OWNER_LOGIN.toString()))
            .andExpect(jsonPath("$.friendLists").value(DEFAULT_FRIEND_LISTS.toString()))
            .andExpect(jsonPath("$.friendCount").value(DEFAULT_FRIEND_COUNT))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdUnixTime").value(DEFAULT_CREATED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedUnixTime").value(DEFAULT_LAST_MODIFIED_TIME.intValue()))
            .andExpect(jsonPath("$.reportDay").value(DEFAULT_REPORT_DAY));
    }

    @Test
    public void getNonExistingContacts() throws Exception {
        // Get the contacts
        restContactsMockMvc.perform(get("/api/friends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateContacts() throws Exception {
        // Initialize the database
        friendsRepository.save(contacts);
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();

        // Update the contacts
        FriendsEntity updatedContacts = friendsRepository.findOne(contacts.getId());
        updatedContacts
            .ownerUsername(UPDATED_OWNER_USERNAME)
            .friendLists(UPDATED_FRIEND_LISTS)
            .createdUnixTime(UPDATED_CREATED_UNIX_TIME)
            .lastModifiedUnixTime(UPDATED_LAST_MODIFIED_TIME)
            .reportDay(UPDATED_REPORT_DAY);

        restContactsMockMvc.perform(put("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContacts)))
            .andExpect(status().isOk());

        // Validate the Contacts in the database
        List<FriendsEntity> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        FriendsEntity testContacts = friendsList.get(friendsList.size() - 1);
        assertThat(testContacts.getOwnerUsername()).isEqualTo(UPDATED_OWNER_USERNAME);
        assertThat(testContacts.getFriendLists()).isEqualTo(UPDATED_FRIEND_LISTS);
        assertThat(testContacts.getCreatedTime()).isEqualTo(UPDATED_CREATED_UNIX_TIME);
        assertThat(testContacts.getLastModifiedTime()).isEqualTo(UPDATED_LAST_MODIFIED_TIME);
        assertThat(testContacts.getReportDay()).isEqualTo(UPDATED_REPORT_DAY);
    }

    @Test
    public void updateNonExistingContacts() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();

        // Create the Contacts

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContactsMockMvc.perform(put("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contacts)))
            .andExpect(status().isCreated());

        // Validate the Contacts in the database
        List<FriendsEntity> contactsList = friendsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteContacts() throws Exception {
        // Initialize the database
        friendsRepository.save(contacts);
        int databaseSizeBeforeDelete = friendsRepository.findAll().size();

        // Get the contacts
        restContactsMockMvc.perform(delete("/api/friends/{id}", contacts.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FriendsEntity> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FriendsEntity.class);
        FriendsEntity contacts1 = new FriendsEntity();
        contacts1.setId("id1");
        FriendsEntity contacts2 = new FriendsEntity();
        contacts2.setId(contacts1.getId());
        assertThat(contacts1).isEqualTo(contacts2);
        contacts2.setId("id2");
        assertThat(contacts1).isNotEqualTo(contacts2);
        contacts1.setId(null);
        assertThat(contacts1).isNotEqualTo(contacts2);
    }
}
