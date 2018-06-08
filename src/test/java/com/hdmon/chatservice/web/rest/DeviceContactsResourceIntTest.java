package com.hdmon.chatservice.web.rest;

import com.hdmon.chatservice.ChatserviceApp;
import com.hdmon.chatservice.config.SecurityBeanOverrideConfiguration;
import com.hdmon.chatservice.domain.DeviceContactsEntity;
import com.hdmon.chatservice.domain.extents.extDeviceContactEntity;
import com.hdmon.chatservice.repository.DeviceContactsRepository;
import com.hdmon.chatservice.service.DeviceContactsService;
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
 * Test class for the DeviceContactsResource REST controller.
 *
 * @see DeviceContactsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatserviceApp.class, SecurityBeanOverrideConfiguration.class})
public class DeviceContactsResourceIntTest {

    private static final String DEFAULT_SEQ_ID = "AAAAAAAAAA";
    private static final String UPDATED_SEQ_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_OWNER_ID = 1L;
    private static final Long UPDATED_OWNER_ID = 2L;

    private static final String DEFAULT_OWNER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_LOGIN = "BBBBBBBBBB";

    private static final List<extDeviceContactEntity> DEFAULT_CONTACT_LISTS = new ArrayList<>();
    private static final List<extDeviceContactEntity> UPDATED_CONTACT_LISTS = new ArrayList<>();

    private static final Integer DEFAULT_CONTACT_COUNT = 1;
    private static final Integer UPDATED_CONTACT_COUNT = 2;

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
    private DeviceContactsRepository deviceContactsRepository;

    @Autowired
    private DeviceContactsService deviceContactsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restContactsMockMvc;

    private DeviceContactsEntity deviceContacts;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeviceContactsResource deviceContactsResource = new DeviceContactsResource(deviceContactsService);
        this.restContactsMockMvc = MockMvcBuilders.standaloneSetup(deviceContactsResource)
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
    public static DeviceContactsEntity createEntity() {
        DeviceContactsEntity deviceContacts = new DeviceContactsEntity()
            .seqId(DEFAULT_SEQ_ID)
            .ownerId(DEFAULT_OWNER_ID)
            .ownerLogin(DEFAULT_OWNER_LOGIN)
            .contactLists(DEFAULT_CONTACT_LISTS)
            .contactCount(DEFAULT_CONTACT_COUNT)
            .createdUnixTime(DEFAULT_CREATED_UNIX_TIME)
            .lastModifiedUnixTime(DEFAULT_LAST_MODIFIED_UNIX_TIME)
            .reportDay(DEFAULT_REPORT_DAY);
        return deviceContacts;
    }

    @Before
    public void initTest() {
        deviceContactsRepository.deleteAll();
        deviceContacts = createEntity();
    }

    @Test
    public void createContacts() throws Exception {
        int databaseSizeBeforeCreate = deviceContactsRepository.findAll().size();

        // Create the DeviceContacts
        restContactsMockMvc.perform(post("/api/deviceContacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceContacts)))
            .andExpect(status().isCreated());

        // Validate the DeviceContacts in the database
        List<DeviceContactsEntity> deviceContactsList = deviceContactsRepository.findAll();
        assertThat(deviceContactsList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceContactsEntity testContacts = deviceContactsList.get(deviceContactsList.size() - 1);
        assertThat(testContacts.getSeqId()).isEqualTo(DEFAULT_SEQ_ID);
        assertThat(testContacts.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testContacts.getOwnerLogin()).isEqualTo(DEFAULT_OWNER_LOGIN);
        assertThat(testContacts.getContactLists()).isEqualTo(DEFAULT_CONTACT_LISTS);
        assertThat(testContacts.getContactCount()).isEqualTo(DEFAULT_CONTACT_COUNT);
        assertThat(testContacts.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testContacts.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testContacts.getCreatedUnixTime()).isEqualTo(DEFAULT_CREATED_UNIX_TIME);
        assertThat(testContacts.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testContacts.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testContacts.getLastModifiedUnixTime()).isEqualTo(DEFAULT_LAST_MODIFIED_UNIX_TIME);
        assertThat(testContacts.getReportDay()).isEqualTo(DEFAULT_REPORT_DAY);
    }

    @Test
    public void createContactsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceContactsRepository.findAll().size();

        // Create the DeviceContacts with an existing ID
        deviceContacts.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactsMockMvc.perform(post("/api/deviceContacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceContacts)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceContacts in the database
        List<DeviceContactsEntity> deviceContactsList = deviceContactsRepository.findAll();
        assertThat(deviceContactsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllContacts() throws Exception {
        // Initialize the database
        deviceContactsRepository.save(deviceContacts);

        // Get all the deviceContactsList
        restContactsMockMvc.perform(get("/api/deviceContacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceContacts.getId())))
            .andExpect(jsonPath("$.[*].seqId").value(hasItem(DEFAULT_SEQ_ID.toString())))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID.intValue())))
            .andExpect(jsonPath("$.[*].ownerLogin").value(hasItem(DEFAULT_OWNER_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].contactLists").value(hasItem(DEFAULT_CONTACT_LISTS.toString())))
            .andExpect(jsonPath("$.[*].contactCount").value(hasItem(DEFAULT_CONTACT_COUNT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdUnixTime").value(hasItem(DEFAULT_CREATED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedUnixTime").value(hasItem(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].reportDay").value(hasItem(DEFAULT_REPORT_DAY)));
    }

    @Test
    public void getContacts() throws Exception {
        // Initialize the database
        deviceContactsRepository.save(deviceContacts);

        // Get the deviceContacts
        restContactsMockMvc.perform(get("/api/deviceContacts/{id}", deviceContacts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deviceContacts.getId()))
            .andExpect(jsonPath("$.seqId").value(DEFAULT_SEQ_ID.toString()))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID.intValue()))
            .andExpect(jsonPath("$.ownerLogin").value(DEFAULT_OWNER_LOGIN.toString()))
            .andExpect(jsonPath("$.contactLists").value(DEFAULT_CONTACT_LISTS.toString()))
            .andExpect(jsonPath("$.contactCount").value(DEFAULT_CONTACT_COUNT))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdUnixTime").value(DEFAULT_CREATED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedUnixTime").value(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.reportDay").value(DEFAULT_REPORT_DAY));
    }

    @Test
    public void getNonExistingContacts() throws Exception {
        // Get the deviceContacts
        restContactsMockMvc.perform(get("/api/deviceContacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateContacts() throws Exception {
        // Initialize the database
        deviceContactsRepository.save(deviceContacts);
        int databaseSizeBeforeUpdate = deviceContactsRepository.findAll().size();

        // Update the deviceContacts
        DeviceContactsEntity updatedContacts = deviceContactsRepository.findOne(deviceContacts.getId());
        updatedContacts
            .seqId(UPDATED_SEQ_ID)
            .ownerId(UPDATED_OWNER_ID)
            .ownerLogin(UPDATED_OWNER_LOGIN)
            .contactLists(UPDATED_CONTACT_LISTS)
            .contactCount(UPDATED_CONTACT_COUNT)
            .createdUnixTime(UPDATED_CREATED_UNIX_TIME)
            .lastModifiedUnixTime(UPDATED_LAST_MODIFIED_UNIX_TIME)
            .reportDay(UPDATED_REPORT_DAY);

        restContactsMockMvc.perform(put("/api/deviceContacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContacts)))
            .andExpect(status().isOk());

        // Validate the DeviceContacts in the database
        List<DeviceContactsEntity> deviceContactsList = deviceContactsRepository.findAll();
        assertThat(deviceContactsList).hasSize(databaseSizeBeforeUpdate);
        DeviceContactsEntity testContacts = deviceContactsList.get(deviceContactsList.size() - 1);
        assertThat(testContacts.getSeqId()).isEqualTo(UPDATED_SEQ_ID);
        assertThat(testContacts.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testContacts.getOwnerLogin()).isEqualTo(UPDATED_OWNER_LOGIN);
        assertThat(testContacts.getContactLists()).isEqualTo(UPDATED_CONTACT_LISTS);
        assertThat(testContacts.getContactCount()).isEqualTo(UPDATED_CONTACT_COUNT);
        assertThat(testContacts.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testContacts.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testContacts.getCreatedUnixTime()).isEqualTo(UPDATED_CREATED_UNIX_TIME);
        assertThat(testContacts.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testContacts.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testContacts.getLastModifiedUnixTime()).isEqualTo(UPDATED_LAST_MODIFIED_UNIX_TIME);
        assertThat(testContacts.getReportDay()).isEqualTo(UPDATED_REPORT_DAY);
    }

    @Test
    public void updateNonExistingContacts() throws Exception {
        int databaseSizeBeforeUpdate = deviceContactsRepository.findAll().size();

        // Create the DeviceContacts

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContactsMockMvc.perform(put("/api/deviceContacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceContacts)))
            .andExpect(status().isCreated());

        // Validate the DeviceContacts in the database
        List<DeviceContactsEntity> deviceContactsList = deviceContactsRepository.findAll();
        assertThat(deviceContactsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteContacts() throws Exception {
        // Initialize the database
        deviceContactsRepository.save(deviceContacts);
        int databaseSizeBeforeDelete = deviceContactsRepository.findAll().size();

        // Get the deviceContacts
        restContactsMockMvc.perform(delete("/api/deviceContacts/{id}", deviceContacts.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeviceContactsEntity> deviceContactsList = deviceContactsRepository.findAll();
        assertThat(deviceContactsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceContactsEntity.class);
        DeviceContactsEntity deviceContacts1 = new DeviceContactsEntity();
        deviceContacts1.setId("id1");
        DeviceContactsEntity deviceContacts2 = new DeviceContactsEntity();
        deviceContacts2.setId(deviceContacts1.getId());
        assertThat(deviceContacts1).isEqualTo(deviceContacts2);
        deviceContacts2.setId("id2");
        assertThat(deviceContacts1).isNotEqualTo(deviceContacts2);
        deviceContacts1.setId(null);
        assertThat(deviceContacts1).isNotEqualTo(deviceContacts2);
    }
}
