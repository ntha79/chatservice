package com.hdmon.chatservice.web.rest;

import com.hdmon.chatservice.ChatserviceApp;
import com.hdmon.chatservice.config.SecurityBeanOverrideConfiguration;
import com.hdmon.chatservice.domain.ServiceSettings;
import com.hdmon.chatservice.domain.extents.extServiceSettingValues;
import com.hdmon.chatservice.repository.ServiceSettingsRepository;
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
 * Test class for the ServiceSettingsResource REST controller.
 *
 * @see ServiceSettingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatserviceApp.class, SecurityBeanOverrideConfiguration.class})
public class ServiceSettingsResourceIntTest {

    private static final String DEFAULT_SEQ_ID = "AAAAAAAAAA";
    private static final String UPDATED_SEQ_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final extServiceSettingValues DEFAULT_VALUES = new extServiceSettingValues();
    private static final extServiceSettingValues UPDATED_VALUES = new extServiceSettingValues();

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

    @Autowired
    private ServiceSettingsRepository serviceSettingsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restServiceSettingsMockMvc;

    private ServiceSettings serviceSettings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceSettingsResource serviceSettingsResource = new ServiceSettingsResource(serviceSettingsRepository);
        this.restServiceSettingsMockMvc = MockMvcBuilders.standaloneSetup(serviceSettingsResource)
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
    public static ServiceSettings createEntity() {
        ServiceSettings serviceSettings = new ServiceSettings()
            .seqId(DEFAULT_SEQ_ID)
            .code(DEFAULT_CODE)
            .values(DEFAULT_VALUES)
            .createdUnixTime(DEFAULT_CREATED_UNIX_TIME)
            .lastModifiedUnixTime(DEFAULT_LAST_MODIFIED_UNIX_TIME);
        return serviceSettings;
    }

    @Before
    public void initTest() {
        serviceSettingsRepository.deleteAll();
        serviceSettings = createEntity();
    }

    @Test
    public void createServiceSettings() throws Exception {
        int databaseSizeBeforeCreate = serviceSettingsRepository.findAll().size();

        // Create the ServiceSettings
        restServiceSettingsMockMvc.perform(post("/api/service-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSettings)))
            .andExpect(status().isCreated());

        // Validate the ServiceSettings in the database
        List<ServiceSettings> serviceSettingsList = serviceSettingsRepository.findAll();
        assertThat(serviceSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceSettings testServiceSettings = serviceSettingsList.get(serviceSettingsList.size() - 1);
        assertThat(testServiceSettings.getSeqId()).isEqualTo(DEFAULT_SEQ_ID);
        assertThat(testServiceSettings.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testServiceSettings.getValues()).isEqualTo(DEFAULT_VALUES);
        assertThat(testServiceSettings.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceSettings.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testServiceSettings.getCreatedUnixTime()).isEqualTo(DEFAULT_CREATED_UNIX_TIME);
        assertThat(testServiceSettings.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testServiceSettings.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testServiceSettings.getLastModifiedUnixTime()).isEqualTo(DEFAULT_LAST_MODIFIED_UNIX_TIME);
    }

    @Test
    public void createServiceSettingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceSettingsRepository.findAll().size();

        // Create the ServiceSettings with an existing ID
        serviceSettings.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceSettingsMockMvc.perform(post("/api/service-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSettings)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceSettings in the database
        List<ServiceSettings> serviceSettingsList = serviceSettingsRepository.findAll();
        assertThat(serviceSettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllServiceSettings() throws Exception {
        // Initialize the database
        serviceSettingsRepository.save(serviceSettings);

        // Get all the serviceSettingsList
        restServiceSettingsMockMvc.perform(get("/api/service-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceSettings.getId())))
            .andExpect(jsonPath("$.[*].seqId").value(hasItem(DEFAULT_SEQ_ID.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].values").value(hasItem(DEFAULT_VALUES.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdUnixTime").value(hasItem(DEFAULT_CREATED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedUnixTime").value(hasItem(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue())));
    }

    @Test
    public void getServiceSettings() throws Exception {
        // Initialize the database
        serviceSettingsRepository.save(serviceSettings);

        // Get the serviceSettings
        restServiceSettingsMockMvc.perform(get("/api/service-settings/{id}", serviceSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceSettings.getId()))
            .andExpect(jsonPath("$.seqId").value(DEFAULT_SEQ_ID.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.values").value(DEFAULT_VALUES.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdUnixTime").value(DEFAULT_CREATED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedUnixTime").value(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue()));
    }

    @Test
    public void getNonExistingServiceSettings() throws Exception {
        // Get the serviceSettings
        restServiceSettingsMockMvc.perform(get("/api/service-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateServiceSettings() throws Exception {
        // Initialize the database
        serviceSettingsRepository.save(serviceSettings);
        int databaseSizeBeforeUpdate = serviceSettingsRepository.findAll().size();

        // Update the serviceSettings
        ServiceSettings updatedServiceSettings = serviceSettingsRepository.findOne(serviceSettings.getId());
        updatedServiceSettings
            .seqId(UPDATED_SEQ_ID)
            .code(UPDATED_CODE)
            .values(UPDATED_VALUES)
            .createdUnixTime(UPDATED_CREATED_UNIX_TIME)
            .lastModifiedUnixTime(UPDATED_LAST_MODIFIED_UNIX_TIME);

        restServiceSettingsMockMvc.perform(put("/api/service-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceSettings)))
            .andExpect(status().isOk());

        // Validate the ServiceSettings in the database
        List<ServiceSettings> serviceSettingsList = serviceSettingsRepository.findAll();
        assertThat(serviceSettingsList).hasSize(databaseSizeBeforeUpdate);
        ServiceSettings testServiceSettings = serviceSettingsList.get(serviceSettingsList.size() - 1);
        assertThat(testServiceSettings.getSeqId()).isEqualTo(UPDATED_SEQ_ID);
        assertThat(testServiceSettings.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testServiceSettings.getValues()).isEqualTo(UPDATED_VALUES);
        assertThat(testServiceSettings.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceSettings.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testServiceSettings.getCreatedUnixTime()).isEqualTo(UPDATED_CREATED_UNIX_TIME);
        assertThat(testServiceSettings.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testServiceSettings.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testServiceSettings.getLastModifiedUnixTime()).isEqualTo(UPDATED_LAST_MODIFIED_UNIX_TIME);
    }

    @Test
    public void updateNonExistingServiceSettings() throws Exception {
        int databaseSizeBeforeUpdate = serviceSettingsRepository.findAll().size();

        // Create the ServiceSettings

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServiceSettingsMockMvc.perform(put("/api/service-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSettings)))
            .andExpect(status().isCreated());

        // Validate the ServiceSettings in the database
        List<ServiceSettings> serviceSettingsList = serviceSettingsRepository.findAll();
        assertThat(serviceSettingsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteServiceSettings() throws Exception {
        // Initialize the database
        serviceSettingsRepository.save(serviceSettings);
        int databaseSizeBeforeDelete = serviceSettingsRepository.findAll().size();

        // Get the serviceSettings
        restServiceSettingsMockMvc.perform(delete("/api/service-settings/{id}", serviceSettings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceSettings> serviceSettingsList = serviceSettingsRepository.findAll();
        assertThat(serviceSettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceSettings.class);
        ServiceSettings serviceSettings1 = new ServiceSettings();
        serviceSettings1.setId("id1");
        ServiceSettings serviceSettings2 = new ServiceSettings();
        serviceSettings2.setId(serviceSettings1.getId());
        assertThat(serviceSettings1).isEqualTo(serviceSettings2);
        serviceSettings2.setId("id2");
        assertThat(serviceSettings1).isNotEqualTo(serviceSettings2);
        serviceSettings1.setId(null);
        assertThat(serviceSettings1).isNotEqualTo(serviceSettings2);
    }
}
