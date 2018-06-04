package com.hdmon.chatservice.web.rest;

import com.hdmon.chatservice.ChatserviceApp;
import com.hdmon.chatservice.config.SecurityBeanOverrideConfiguration;
import com.hdmon.chatservice.domain.Fanpages;
import com.hdmon.chatservice.domain.enumeration.FanpageStatus;
import com.hdmon.chatservice.repository.FanpagesRepository;
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
import java.util.List;

import static com.hdmon.chatservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FanpagesResource REST controller.
 *
 * @see FanpagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatserviceApp.class, SecurityBeanOverrideConfiguration.class})
public class FanpagesResourceIntTest {

    private static final String DEFAULT_FANPAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FANPAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FAN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FAN_URL = "AAAAAAAAAA";
    private static final String UPDATED_FAN_URL = "BBBBBBBBBB";

    private static final String DEFAULT_FAN_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_FAN_ABOUT = "BBBBBBBBBB";

    private static final String DEFAULT_FAN_ICON = "AAAAAAAAAA";
    private static final String UPDATED_FAN_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_FAN_THUMBNAIL = "AAAAAAAAAA";
    private static final String UPDATED_FAN_THUMBNAIL = "BBBBBBBBBB";

    private static final FanpageStatus DEFAULT_FAN_STATUS = FanpageStatus.ACTIVE;
    private static final FanpageStatus UPDATED_FAN_STATUS = FanpageStatus.REPORT;

    private static final String DEFAULT_MEMBER_LIST = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_LIST = "BBBBBBBBBB";

    private static final Integer DEFAULT_MEMBER_COUNT = 1;
    private static final Integer UPDATED_MEMBER_COUNT = 2;

    private static final Long DEFAULT_OWNER_ID = 1L;
    private static final Long UPDATED_OWNER_ID = 2L;

    private static final String DEFAULT_OWNER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_LOGIN = "BBBBBBBBBB";

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
    private FanpagesRepository fanpagesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFanpagesMockMvc;

    private Fanpages fanpages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FanpagesResource fanpagesResource = new FanpagesResource(fanpagesRepository);
        this.restFanpagesMockMvc = MockMvcBuilders.standaloneSetup(fanpagesResource)
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
    public static Fanpages createEntity() {
        Fanpages fanpages = new Fanpages()
            .fanpageId(DEFAULT_FANPAGE_ID)
            .fanName(DEFAULT_FAN_NAME)
            .fanUrl(DEFAULT_FAN_URL)
            .fanAbout(DEFAULT_FAN_ABOUT)
            .fanIcon(DEFAULT_FAN_ICON)
            .fanThumbnail(DEFAULT_FAN_THUMBNAIL)
            .fanStatus(DEFAULT_FAN_STATUS)
            .memberList(DEFAULT_MEMBER_LIST)
            .memberCount(DEFAULT_MEMBER_COUNT)
            .ownerId(DEFAULT_OWNER_ID)
            .ownerLogin(DEFAULT_OWNER_LOGIN)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdUnixTime(DEFAULT_CREATED_UNIX_TIME)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedUnixTime(DEFAULT_LAST_MODIFIED_UNIX_TIME)
            .reportDay(DEFAULT_REPORT_DAY);
        return fanpages;
    }

    @Before
    public void initTest() {
        fanpagesRepository.deleteAll();
        fanpages = createEntity();
    }

    @Test
    public void createFanpages() throws Exception {
        int databaseSizeBeforeCreate = fanpagesRepository.findAll().size();

        // Create the Fanpages
        restFanpagesMockMvc.perform(post("/api/fanpages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fanpages)))
            .andExpect(status().isCreated());

        // Validate the Fanpages in the database
        List<Fanpages> fanpagesList = fanpagesRepository.findAll();
        assertThat(fanpagesList).hasSize(databaseSizeBeforeCreate + 1);
        Fanpages testFanpages = fanpagesList.get(fanpagesList.size() - 1);
        assertThat(testFanpages.getFanpageId()).isEqualTo(DEFAULT_FANPAGE_ID);
        assertThat(testFanpages.getFanName()).isEqualTo(DEFAULT_FAN_NAME);
        assertThat(testFanpages.getFanUrl()).isEqualTo(DEFAULT_FAN_URL);
        assertThat(testFanpages.getFanAbout()).isEqualTo(DEFAULT_FAN_ABOUT);
        assertThat(testFanpages.getFanIcon()).isEqualTo(DEFAULT_FAN_ICON);
        assertThat(testFanpages.getFanThumbnail()).isEqualTo(DEFAULT_FAN_THUMBNAIL);
        assertThat(testFanpages.getFanStatus()).isEqualTo(DEFAULT_FAN_STATUS);
        assertThat(testFanpages.getMemberList()).isEqualTo(DEFAULT_MEMBER_LIST);
        assertThat(testFanpages.getMemberCount()).isEqualTo(DEFAULT_MEMBER_COUNT);
        assertThat(testFanpages.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testFanpages.getOwnerLogin()).isEqualTo(DEFAULT_OWNER_LOGIN);
        assertThat(testFanpages.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFanpages.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFanpages.getCreatedUnixTime()).isEqualTo(DEFAULT_CREATED_UNIX_TIME);
        assertThat(testFanpages.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testFanpages.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testFanpages.getLastModifiedUnixTime()).isEqualTo(DEFAULT_LAST_MODIFIED_UNIX_TIME);
        assertThat(testFanpages.getReportDay()).isEqualTo(DEFAULT_REPORT_DAY);
    }

    @Test
    public void createFanpagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fanpagesRepository.findAll().size();

        // Create the Fanpages with an existing ID
        fanpages.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFanpagesMockMvc.perform(post("/api/fanpages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fanpages)))
            .andExpect(status().isBadRequest());

        // Validate the Fanpages in the database
        List<Fanpages> fanpagesList = fanpagesRepository.findAll();
        assertThat(fanpagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllFanpages() throws Exception {
        // Initialize the database
        fanpagesRepository.save(fanpages);

        // Get all the fanpagesList
        restFanpagesMockMvc.perform(get("/api/fanpages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fanpages.getId())))
            .andExpect(jsonPath("$.[*].fanpageId").value(hasItem(DEFAULT_FANPAGE_ID.toString())))
            .andExpect(jsonPath("$.[*].fanName").value(hasItem(DEFAULT_FAN_NAME.toString())))
            .andExpect(jsonPath("$.[*].fanUrl").value(hasItem(DEFAULT_FAN_URL.toString())))
            .andExpect(jsonPath("$.[*].fanAbout").value(hasItem(DEFAULT_FAN_ABOUT.toString())))
            .andExpect(jsonPath("$.[*].fanIcon").value(hasItem(DEFAULT_FAN_ICON.toString())))
            .andExpect(jsonPath("$.[*].fanThumbnail").value(hasItem(DEFAULT_FAN_THUMBNAIL.toString())))
            .andExpect(jsonPath("$.[*].fanStatus").value(hasItem(DEFAULT_FAN_STATUS.toString())))
            .andExpect(jsonPath("$.[*].memberList").value(hasItem(DEFAULT_MEMBER_LIST.toString())))
            .andExpect(jsonPath("$.[*].memberCount").value(hasItem(DEFAULT_MEMBER_COUNT)))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID.intValue())))
            .andExpect(jsonPath("$.[*].ownerLogin").value(hasItem(DEFAULT_OWNER_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdUnixTime").value(hasItem(DEFAULT_CREATED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedUnixTime").value(hasItem(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].reportDay").value(hasItem(DEFAULT_REPORT_DAY)));
    }

    @Test
    public void getFanpages() throws Exception {
        // Initialize the database
        fanpagesRepository.save(fanpages);

        // Get the fanpages
        restFanpagesMockMvc.perform(get("/api/fanpages/{id}", fanpages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fanpages.getId()))
            .andExpect(jsonPath("$.fanpageId").value(DEFAULT_FANPAGE_ID.toString()))
            .andExpect(jsonPath("$.fanName").value(DEFAULT_FAN_NAME.toString()))
            .andExpect(jsonPath("$.fanUrl").value(DEFAULT_FAN_URL.toString()))
            .andExpect(jsonPath("$.fanAbout").value(DEFAULT_FAN_ABOUT.toString()))
            .andExpect(jsonPath("$.fanIcon").value(DEFAULT_FAN_ICON.toString()))
            .andExpect(jsonPath("$.fanThumbnail").value(DEFAULT_FAN_THUMBNAIL.toString()))
            .andExpect(jsonPath("$.fanStatus").value(DEFAULT_FAN_STATUS.toString()))
            .andExpect(jsonPath("$.memberList").value(DEFAULT_MEMBER_LIST.toString()))
            .andExpect(jsonPath("$.memberCount").value(DEFAULT_MEMBER_COUNT))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID.intValue()))
            .andExpect(jsonPath("$.ownerLogin").value(DEFAULT_OWNER_LOGIN.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdUnixTime").value(DEFAULT_CREATED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedUnixTime").value(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.reportDay").value(DEFAULT_REPORT_DAY));
    }

    @Test
    public void getNonExistingFanpages() throws Exception {
        // Get the fanpages
        restFanpagesMockMvc.perform(get("/api/fanpages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFanpages() throws Exception {
        // Initialize the database
        fanpagesRepository.save(fanpages);
        int databaseSizeBeforeUpdate = fanpagesRepository.findAll().size();

        // Update the fanpages
        Fanpages updatedFanpages = fanpagesRepository.findOne(fanpages.getId());
        updatedFanpages
            .fanpageId(UPDATED_FANPAGE_ID)
            .fanName(UPDATED_FAN_NAME)
            .fanUrl(UPDATED_FAN_URL)
            .fanAbout(UPDATED_FAN_ABOUT)
            .fanIcon(UPDATED_FAN_ICON)
            .fanThumbnail(UPDATED_FAN_THUMBNAIL)
            .fanStatus(UPDATED_FAN_STATUS)
            .memberList(UPDATED_MEMBER_LIST)
            .memberCount(UPDATED_MEMBER_COUNT)
            .ownerId(UPDATED_OWNER_ID)
            .ownerLogin(UPDATED_OWNER_LOGIN)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .createdUnixTime(UPDATED_CREATED_UNIX_TIME)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedUnixTime(UPDATED_LAST_MODIFIED_UNIX_TIME)
            .reportDay(UPDATED_REPORT_DAY);

        restFanpagesMockMvc.perform(put("/api/fanpages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFanpages)))
            .andExpect(status().isOk());

        // Validate the Fanpages in the database
        List<Fanpages> fanpagesList = fanpagesRepository.findAll();
        assertThat(fanpagesList).hasSize(databaseSizeBeforeUpdate);
        Fanpages testFanpages = fanpagesList.get(fanpagesList.size() - 1);
        assertThat(testFanpages.getFanpageId()).isEqualTo(UPDATED_FANPAGE_ID);
        assertThat(testFanpages.getFanName()).isEqualTo(UPDATED_FAN_NAME);
        assertThat(testFanpages.getFanUrl()).isEqualTo(UPDATED_FAN_URL);
        assertThat(testFanpages.getFanAbout()).isEqualTo(UPDATED_FAN_ABOUT);
        assertThat(testFanpages.getFanIcon()).isEqualTo(UPDATED_FAN_ICON);
        assertThat(testFanpages.getFanThumbnail()).isEqualTo(UPDATED_FAN_THUMBNAIL);
        assertThat(testFanpages.getFanStatus()).isEqualTo(UPDATED_FAN_STATUS);
        assertThat(testFanpages.getMemberList()).isEqualTo(UPDATED_MEMBER_LIST);
        assertThat(testFanpages.getMemberCount()).isEqualTo(UPDATED_MEMBER_COUNT);
        assertThat(testFanpages.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testFanpages.getOwnerLogin()).isEqualTo(UPDATED_OWNER_LOGIN);
        assertThat(testFanpages.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFanpages.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFanpages.getCreatedUnixTime()).isEqualTo(UPDATED_CREATED_UNIX_TIME);
        assertThat(testFanpages.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testFanpages.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testFanpages.getLastModifiedUnixTime()).isEqualTo(UPDATED_LAST_MODIFIED_UNIX_TIME);
        assertThat(testFanpages.getReportDay()).isEqualTo(UPDATED_REPORT_DAY);
    }

    @Test
    public void updateNonExistingFanpages() throws Exception {
        int databaseSizeBeforeUpdate = fanpagesRepository.findAll().size();

        // Create the Fanpages

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFanpagesMockMvc.perform(put("/api/fanpages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fanpages)))
            .andExpect(status().isCreated());

        // Validate the Fanpages in the database
        List<Fanpages> fanpagesList = fanpagesRepository.findAll();
        assertThat(fanpagesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFanpages() throws Exception {
        // Initialize the database
        fanpagesRepository.save(fanpages);
        int databaseSizeBeforeDelete = fanpagesRepository.findAll().size();

        // Get the fanpages
        restFanpagesMockMvc.perform(delete("/api/fanpages/{id}", fanpages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fanpages> fanpagesList = fanpagesRepository.findAll();
        assertThat(fanpagesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fanpages.class);
        Fanpages fanpages1 = new Fanpages();
        fanpages1.setId("id1");
        Fanpages fanpages2 = new Fanpages();
        fanpages2.setId(fanpages1.getId());
        assertThat(fanpages1).isEqualTo(fanpages2);
        fanpages2.setId("id2");
        assertThat(fanpages1).isNotEqualTo(fanpages2);
        fanpages1.setId(null);
        assertThat(fanpages1).isNotEqualTo(fanpages2);
    }
}
