package com.hdmon.chatservice.web.rest;

import com.hdmon.chatservice.ChatserviceApp;
import com.hdmon.chatservice.config.SecurityBeanOverrideConfiguration;
import com.hdmon.chatservice.domain.PushNotifications;
import com.hdmon.chatservice.domain.enumeration.PushNotificationStatus;
import com.hdmon.chatservice.domain.extents.extPushNotificationToMemberLists;
import com.hdmon.chatservice.repository.PushNotificationsRepository;
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
 * Test class for the PushNotificationsResource REST controller.
 *
 * @see PushNotificationsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatserviceApp.class, SecurityBeanOverrideConfiguration.class})
public class PushNotificationsResourceIntTest {

    private static final String DEFAULT_SEQ_ID = "AAAAAAAAAA";
    private static final String UPDATED_SEQ_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_LINK_DETAIL = "BBBBBBBBBB";

    private static final PushNotificationStatus DEFAULT_STATUS = PushNotificationStatus.INIT;
    private static final PushNotificationStatus UPDATED_STATUS = PushNotificationStatus.SENDING;

    private static final List<extPushNotificationToMemberLists> DEFAULT_TO_MEMBER_LIST = new ArrayList<>();
    private static final List<extPushNotificationToMemberLists> UPDATED_TO_MEMBER_LIST = new ArrayList<>();

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
    private PushNotificationsRepository pushNotificationsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPushNotificationsMockMvc;

    private PushNotifications pushNotifications;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PushNotificationsResource pushNotificationsResource = new PushNotificationsResource(pushNotificationsRepository);
        this.restPushNotificationsMockMvc = MockMvcBuilders.standaloneSetup(pushNotificationsResource)
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
    public static PushNotifications createEntity() {
        PushNotifications pushNotifications = new PushNotifications()
            .seqId(DEFAULT_SEQ_ID)
            .name(DEFAULT_NAME)
            .message(DEFAULT_MESSAGE)
            .linkDetail(DEFAULT_LINK_DETAIL)
            .status(DEFAULT_STATUS)
            .toMemberList(DEFAULT_TO_MEMBER_LIST)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdUnixTime(DEFAULT_CREATED_UNIX_TIME)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedUnixTime(DEFAULT_LAST_MODIFIED_UNIX_TIME)
            .reportDay(DEFAULT_REPORT_DAY);
        return pushNotifications;
    }

    @Before
    public void initTest() {
        pushNotificationsRepository.deleteAll();
        pushNotifications = createEntity();
    }

    @Test
    public void createPushNotifications() throws Exception {
        int databaseSizeBeforeCreate = pushNotificationsRepository.findAll().size();

        // Create the PushNotifications
        restPushNotificationsMockMvc.perform(post("/api/push-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pushNotifications)))
            .andExpect(status().isCreated());

        // Validate the PushNotifications in the database
        List<PushNotifications> pushNotificationsList = pushNotificationsRepository.findAll();
        assertThat(pushNotificationsList).hasSize(databaseSizeBeforeCreate + 1);
        PushNotifications testPushNotifications = pushNotificationsList.get(pushNotificationsList.size() - 1);
        assertThat(testPushNotifications.getSeqId()).isEqualTo(DEFAULT_SEQ_ID);
        assertThat(testPushNotifications.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPushNotifications.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testPushNotifications.getLinkDetail()).isEqualTo(DEFAULT_LINK_DETAIL);
        assertThat(testPushNotifications.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPushNotifications.getToMemberList()).isEqualTo(DEFAULT_TO_MEMBER_LIST);
        assertThat(testPushNotifications.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPushNotifications.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPushNotifications.getCreatedUnixTime()).isEqualTo(DEFAULT_CREATED_UNIX_TIME);
        assertThat(testPushNotifications.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPushNotifications.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testPushNotifications.getLastModifiedUnixTime()).isEqualTo(DEFAULT_LAST_MODIFIED_UNIX_TIME);
        assertThat(testPushNotifications.getReportDay()).isEqualTo(DEFAULT_REPORT_DAY);
    }

    @Test
    public void createPushNotificationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pushNotificationsRepository.findAll().size();

        // Create the PushNotifications with an existing ID
        pushNotifications.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPushNotificationsMockMvc.perform(post("/api/push-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pushNotifications)))
            .andExpect(status().isBadRequest());

        // Validate the PushNotifications in the database
        List<PushNotifications> pushNotificationsList = pushNotificationsRepository.findAll();
        assertThat(pushNotificationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPushNotifications() throws Exception {
        // Initialize the database
        pushNotificationsRepository.save(pushNotifications);

        // Get all the pushNotificationsList
        restPushNotificationsMockMvc.perform(get("/api/push-notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pushNotifications.getId())))
            .andExpect(jsonPath("$.[*].seqId").value(hasItem(DEFAULT_SEQ_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].linkDetail").value(hasItem(DEFAULT_LINK_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].toMemberList").value(hasItem(DEFAULT_TO_MEMBER_LIST.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdUnixTime").value(hasItem(DEFAULT_CREATED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedUnixTime").value(hasItem(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].reportDay").value(hasItem(DEFAULT_REPORT_DAY)));
    }

    @Test
    public void getPushNotifications() throws Exception {
        // Initialize the database
        pushNotificationsRepository.save(pushNotifications);

        // Get the pushNotifications
        restPushNotificationsMockMvc.perform(get("/api/push-notifications/{id}", pushNotifications.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pushNotifications.getId()))
            .andExpect(jsonPath("$.seqId").value(DEFAULT_SEQ_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.linkDetail").value(DEFAULT_LINK_DETAIL.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.toMemberList").value(DEFAULT_TO_MEMBER_LIST.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdUnixTime").value(DEFAULT_CREATED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedUnixTime").value(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.reportDay").value(DEFAULT_REPORT_DAY));
    }

    @Test
    public void getNonExistingPushNotifications() throws Exception {
        // Get the pushNotifications
        restPushNotificationsMockMvc.perform(get("/api/push-notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePushNotifications() throws Exception {
        // Initialize the database
        pushNotificationsRepository.save(pushNotifications);
        int databaseSizeBeforeUpdate = pushNotificationsRepository.findAll().size();

        // Update the pushNotifications
        PushNotifications updatedPushNotifications = pushNotificationsRepository.findOne(pushNotifications.getId());
        updatedPushNotifications
            .seqId(UPDATED_SEQ_ID)
            .name(UPDATED_NAME)
            .message(UPDATED_MESSAGE)
            .linkDetail(UPDATED_LINK_DETAIL)
            .status(UPDATED_STATUS)
            .toMemberList(UPDATED_TO_MEMBER_LIST)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .createdUnixTime(UPDATED_CREATED_UNIX_TIME)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedUnixTime(UPDATED_LAST_MODIFIED_UNIX_TIME)
            .reportDay(UPDATED_REPORT_DAY);

        restPushNotificationsMockMvc.perform(put("/api/push-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPushNotifications)))
            .andExpect(status().isOk());

        // Validate the PushNotifications in the database
        List<PushNotifications> pushNotificationsList = pushNotificationsRepository.findAll();
        assertThat(pushNotificationsList).hasSize(databaseSizeBeforeUpdate);
        PushNotifications testPushNotifications = pushNotificationsList.get(pushNotificationsList.size() - 1);
        assertThat(testPushNotifications.getSeqId()).isEqualTo(UPDATED_SEQ_ID);
        assertThat(testPushNotifications.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPushNotifications.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testPushNotifications.getLinkDetail()).isEqualTo(UPDATED_LINK_DETAIL);
        assertThat(testPushNotifications.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPushNotifications.getToMemberList()).isEqualTo(UPDATED_TO_MEMBER_LIST);
        assertThat(testPushNotifications.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPushNotifications.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPushNotifications.getCreatedUnixTime()).isEqualTo(UPDATED_CREATED_UNIX_TIME);
        assertThat(testPushNotifications.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPushNotifications.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testPushNotifications.getLastModifiedUnixTime()).isEqualTo(UPDATED_LAST_MODIFIED_UNIX_TIME);
        assertThat(testPushNotifications.getReportDay()).isEqualTo(UPDATED_REPORT_DAY);
    }

    @Test
    public void updateNonExistingPushNotifications() throws Exception {
        int databaseSizeBeforeUpdate = pushNotificationsRepository.findAll().size();

        // Create the PushNotifications

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPushNotificationsMockMvc.perform(put("/api/push-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pushNotifications)))
            .andExpect(status().isCreated());

        // Validate the PushNotifications in the database
        List<PushNotifications> pushNotificationsList = pushNotificationsRepository.findAll();
        assertThat(pushNotificationsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePushNotifications() throws Exception {
        // Initialize the database
        pushNotificationsRepository.save(pushNotifications);
        int databaseSizeBeforeDelete = pushNotificationsRepository.findAll().size();

        // Get the pushNotifications
        restPushNotificationsMockMvc.perform(delete("/api/push-notifications/{id}", pushNotifications.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PushNotifications> pushNotificationsList = pushNotificationsRepository.findAll();
        assertThat(pushNotificationsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PushNotifications.class);
        PushNotifications pushNotifications1 = new PushNotifications();
        pushNotifications1.setId("id1");
        PushNotifications pushNotifications2 = new PushNotifications();
        pushNotifications2.setId(pushNotifications1.getId());
        assertThat(pushNotifications1).isEqualTo(pushNotifications2);
        pushNotifications2.setId("id2");
        assertThat(pushNotifications1).isNotEqualTo(pushNotifications2);
        pushNotifications1.setId(null);
        assertThat(pushNotifications1).isNotEqualTo(pushNotifications2);
    }
}
