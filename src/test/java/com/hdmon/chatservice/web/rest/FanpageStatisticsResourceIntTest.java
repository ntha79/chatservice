package com.hdmon.chatservice.web.rest;

import com.hdmon.chatservice.ChatserviceApp;
import com.hdmon.chatservice.config.SecurityBeanOverrideConfiguration;
import com.hdmon.chatservice.domain.FanpageStatisticsEntity;
import com.hdmon.chatservice.domain.enumeration.CheckStatusEnum;
import com.hdmon.chatservice.repository.FanpageStatisticsRepository;
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

import java.util.List;

import static com.hdmon.chatservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FanpageStatisticsResource REST controller.
 *
 * @see FanpageStatisticsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatserviceApp.class, SecurityBeanOverrideConfiguration.class})
public class FanpageStatisticsResourceIntTest {

    private static final String DEFAULT_SEQ_ID = "AAAAAAAAAA";
    private static final String UPDATED_SEQ_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_DAY_COUNT = 1;
    private static final Integer UPDATED_DAY_COUNT = 2;

    private static final Integer DEFAULT_MONTH_COUNT = 1;
    private static final Integer UPDATED_MONTH_COUNT = 2;

    private static final Integer DEFAULT_YEAR_COUNT = 1;
    private static final Integer UPDATED_YEAR_COUNT = 2;

    private static final Integer DEFAULT_IN_DAY = 1;
    private static final Integer UPDATED_IN_DAY = 2;

    private static final Integer DEFAULT_IN_MONTH = 1;
    private static final Integer UPDATED_IN_MONTH = 2;

    private static final Integer DEFAULT_IN_YEAR = 1;
    private static final Integer UPDATED_IN_YEAR = 2;

    private static final CheckStatusEnum DEFAULT_STATUS = CheckStatusEnum.NOTCHECK;
    private static final CheckStatusEnum UPDATED_STATUS = CheckStatusEnum.CHECKED;

    @Autowired
    private FanpageStatisticsRepository fanpageStatisticsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFanpageStatisticsMockMvc;

    private FanpageStatisticsEntity fanpageStatistics;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FanpageStatisticsResource fanpageStatisticsResource = new FanpageStatisticsResource(fanpageStatisticsRepository);
        this.restFanpageStatisticsMockMvc = MockMvcBuilders.standaloneSetup(fanpageStatisticsResource)
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
    public static FanpageStatisticsEntity createEntity() {
        FanpageStatisticsEntity fanpageStatistics = new FanpageStatisticsEntity()
            .dayCount(DEFAULT_DAY_COUNT)
            .monthCount(DEFAULT_MONTH_COUNT)
            .yearCount(DEFAULT_YEAR_COUNT)
            .inDay(DEFAULT_IN_DAY)
            .inMonth(DEFAULT_IN_MONTH)
            .inYear(DEFAULT_IN_YEAR)
            .status(DEFAULT_STATUS);
        return fanpageStatistics;
    }

    @Before
    public void initTest() {
        fanpageStatisticsRepository.deleteAll();
        fanpageStatistics = createEntity();
    }

    @Test
    public void createFanpageStatistics() throws Exception {
        int databaseSizeBeforeCreate = fanpageStatisticsRepository.findAll().size();

        // Create the FanpageStatistics
        restFanpageStatisticsMockMvc.perform(post("/api/fanpage-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fanpageStatistics)))
            .andExpect(status().isCreated());

        // Validate the FanpageStatistics in the database
        List<FanpageStatisticsEntity> fanpageStatisticsList = fanpageStatisticsRepository.findAll();
        assertThat(fanpageStatisticsList).hasSize(databaseSizeBeforeCreate + 1);
        FanpageStatisticsEntity testFanpageStatistics = fanpageStatisticsList.get(fanpageStatisticsList.size() - 1);
        assertThat(testFanpageStatistics.getDayCount()).isEqualTo(DEFAULT_DAY_COUNT);
        assertThat(testFanpageStatistics.getMonthCount()).isEqualTo(DEFAULT_MONTH_COUNT);
        assertThat(testFanpageStatistics.getYearCount()).isEqualTo(DEFAULT_YEAR_COUNT);
        assertThat(testFanpageStatistics.getInDay()).isEqualTo(DEFAULT_IN_DAY);
        assertThat(testFanpageStatistics.getInMonth()).isEqualTo(DEFAULT_IN_MONTH);
        assertThat(testFanpageStatistics.getInYear()).isEqualTo(DEFAULT_IN_YEAR);
        assertThat(testFanpageStatistics.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    public void createFanpageStatisticsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fanpageStatisticsRepository.findAll().size();

        // Create the FanpageStatistics with an existing ID
        fanpageStatistics.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFanpageStatisticsMockMvc.perform(post("/api/fanpage-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fanpageStatistics)))
            .andExpect(status().isBadRequest());

        // Validate the FanpageStatistics in the database
        List<FanpageStatisticsEntity> fanpageStatisticsList = fanpageStatisticsRepository.findAll();
        assertThat(fanpageStatisticsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllFanpageStatistics() throws Exception {
        // Initialize the database
        fanpageStatisticsRepository.save(fanpageStatistics);

        // Get all the fanpageStatisticsList
        restFanpageStatisticsMockMvc.perform(get("/api/fanpage-statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fanpageStatistics.getId())))
            .andExpect(jsonPath("$.[*].seqId").value(hasItem(DEFAULT_SEQ_ID.toString())))
            .andExpect(jsonPath("$.[*].dayCount").value(hasItem(DEFAULT_DAY_COUNT)))
            .andExpect(jsonPath("$.[*].monthCount").value(hasItem(DEFAULT_MONTH_COUNT)))
            .andExpect(jsonPath("$.[*].yearCount").value(hasItem(DEFAULT_YEAR_COUNT)))
            .andExpect(jsonPath("$.[*].inDay").value(hasItem(DEFAULT_IN_DAY)))
            .andExpect(jsonPath("$.[*].inMonth").value(hasItem(DEFAULT_IN_MONTH)))
            .andExpect(jsonPath("$.[*].inYear").value(hasItem(DEFAULT_IN_YEAR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    public void getFanpageStatistics() throws Exception {
        // Initialize the database
        fanpageStatisticsRepository.save(fanpageStatistics);

        // Get the fanpageStatistics
        restFanpageStatisticsMockMvc.perform(get("/api/fanpage-statistics/{id}", fanpageStatistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fanpageStatistics.getId()))
            .andExpect(jsonPath("$.seqId").value(DEFAULT_SEQ_ID.toString()))
            .andExpect(jsonPath("$.dayCount").value(DEFAULT_DAY_COUNT))
            .andExpect(jsonPath("$.monthCount").value(DEFAULT_MONTH_COUNT))
            .andExpect(jsonPath("$.yearCount").value(DEFAULT_YEAR_COUNT))
            .andExpect(jsonPath("$.inDay").value(DEFAULT_IN_DAY))
            .andExpect(jsonPath("$.inMonth").value(DEFAULT_IN_MONTH))
            .andExpect(jsonPath("$.inYear").value(DEFAULT_IN_YEAR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    public void getNonExistingFanpageStatistics() throws Exception {
        // Get the fanpageStatistics
        restFanpageStatisticsMockMvc.perform(get("/api/fanpage-statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFanpageStatistics() throws Exception {
        // Initialize the database
        fanpageStatisticsRepository.save(fanpageStatistics);
        int databaseSizeBeforeUpdate = fanpageStatisticsRepository.findAll().size();

        // Update the fanpageStatistics
        FanpageStatisticsEntity updatedFanpageStatistics = fanpageStatisticsRepository.findOne(fanpageStatistics.getId());
        updatedFanpageStatistics
            .dayCount(UPDATED_DAY_COUNT)
            .monthCount(UPDATED_MONTH_COUNT)
            .yearCount(UPDATED_YEAR_COUNT)
            .inDay(UPDATED_IN_DAY)
            .inMonth(UPDATED_IN_MONTH)
            .inYear(UPDATED_IN_YEAR)
            .status(UPDATED_STATUS);

        restFanpageStatisticsMockMvc.perform(put("/api/fanpage-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFanpageStatistics)))
            .andExpect(status().isOk());

        // Validate the FanpageStatistics in the database
        List<FanpageStatisticsEntity> fanpageStatisticsList = fanpageStatisticsRepository.findAll();
        assertThat(fanpageStatisticsList).hasSize(databaseSizeBeforeUpdate);
        FanpageStatisticsEntity testFanpageStatistics = fanpageStatisticsList.get(fanpageStatisticsList.size() - 1);
        assertThat(testFanpageStatistics.getDayCount()).isEqualTo(UPDATED_DAY_COUNT);
        assertThat(testFanpageStatistics.getMonthCount()).isEqualTo(UPDATED_MONTH_COUNT);
        assertThat(testFanpageStatistics.getYearCount()).isEqualTo(UPDATED_YEAR_COUNT);
        assertThat(testFanpageStatistics.getInDay()).isEqualTo(UPDATED_IN_DAY);
        assertThat(testFanpageStatistics.getInMonth()).isEqualTo(UPDATED_IN_MONTH);
        assertThat(testFanpageStatistics.getInYear()).isEqualTo(UPDATED_IN_YEAR);
        assertThat(testFanpageStatistics.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    public void updateNonExistingFanpageStatistics() throws Exception {
        int databaseSizeBeforeUpdate = fanpageStatisticsRepository.findAll().size();

        // Create the FanpageStatistics

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFanpageStatisticsMockMvc.perform(put("/api/fanpage-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fanpageStatistics)))
            .andExpect(status().isCreated());

        // Validate the FanpageStatistics in the database
        List<FanpageStatisticsEntity> fanpageStatisticsList = fanpageStatisticsRepository.findAll();
        assertThat(fanpageStatisticsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFanpageStatistics() throws Exception {
        // Initialize the database
        fanpageStatisticsRepository.save(fanpageStatistics);
        int databaseSizeBeforeDelete = fanpageStatisticsRepository.findAll().size();

        // Get the fanpageStatistics
        restFanpageStatisticsMockMvc.perform(delete("/api/fanpage-statistics/{id}", fanpageStatistics.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FanpageStatisticsEntity> fanpageStatisticsList = fanpageStatisticsRepository.findAll();
        assertThat(fanpageStatisticsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FanpageStatisticsEntity.class);
        FanpageStatisticsEntity fanpageStatistics1 = new FanpageStatisticsEntity();
        fanpageStatistics1.setId("id1");
        FanpageStatisticsEntity fanpageStatistics2 = new FanpageStatisticsEntity();
        fanpageStatistics2.setId(fanpageStatistics1.getId());
        assertThat(fanpageStatistics1).isEqualTo(fanpageStatistics2);
        fanpageStatistics2.setId("id2");
        assertThat(fanpageStatistics1).isNotEqualTo(fanpageStatistics2);
        fanpageStatistics1.setId(null);
        assertThat(fanpageStatistics1).isNotEqualTo(fanpageStatistics2);
    }
}
