package com.hdmon.chatservice.web.rest;

import com.hdmon.chatservice.ChatserviceApp;
import com.hdmon.chatservice.config.SecurityBeanOverrideConfiguration;
import com.hdmon.chatservice.domain.GroupMemberStatistics;
import com.hdmon.chatservice.domain.enumeration.CheckStatus;
import com.hdmon.chatservice.repository.GroupMemberStatisticsRepository;
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
 * Test class for the GroupMemberStatisticsResource REST controller.
 *
 * @see GroupMemberStatisticsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatserviceApp.class, SecurityBeanOverrideConfiguration.class})
public class GroupMemberStatisticsResourceIntTest {

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

    private static final CheckStatus DEFAULT_STATUS = CheckStatus.NOTCHECK;
    private static final CheckStatus UPDATED_STATUS = CheckStatus.CHECKED;

    @Autowired
    private GroupMemberStatisticsRepository groupMemberStatisticsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restGroupMemberStatisticsMockMvc;

    private GroupMemberStatistics groupMemberStatistics;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupMemberStatisticsResource groupMemberStatisticsResource = new GroupMemberStatisticsResource(groupMemberStatisticsRepository);
        this.restGroupMemberStatisticsMockMvc = MockMvcBuilders.standaloneSetup(groupMemberStatisticsResource)
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
    public static GroupMemberStatistics createEntity() {
        GroupMemberStatistics groupMemberStatistics = new GroupMemberStatistics()
            .seqId(DEFAULT_SEQ_ID)
            .dayCount(DEFAULT_DAY_COUNT)
            .monthCount(DEFAULT_MONTH_COUNT)
            .yearCount(DEFAULT_YEAR_COUNT)
            .inDay(DEFAULT_IN_DAY)
            .inMonth(DEFAULT_IN_MONTH)
            .inYear(DEFAULT_IN_YEAR)
            .status(DEFAULT_STATUS);
        return groupMemberStatistics;
    }

    @Before
    public void initTest() {
        groupMemberStatisticsRepository.deleteAll();
        groupMemberStatistics = createEntity();
    }

    @Test
    public void createGroupMemberStatistics() throws Exception {
        int databaseSizeBeforeCreate = groupMemberStatisticsRepository.findAll().size();

        // Create the GroupMemberStatistics
        restGroupMemberStatisticsMockMvc.perform(post("/api/group-member-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupMemberStatistics)))
            .andExpect(status().isCreated());

        // Validate the GroupMemberStatistics in the database
        List<GroupMemberStatistics> groupMemberStatisticsList = groupMemberStatisticsRepository.findAll();
        assertThat(groupMemberStatisticsList).hasSize(databaseSizeBeforeCreate + 1);
        GroupMemberStatistics testGroupMemberStatistics = groupMemberStatisticsList.get(groupMemberStatisticsList.size() - 1);
        assertThat(testGroupMemberStatistics.getSeqId()).isEqualTo(DEFAULT_SEQ_ID);
        assertThat(testGroupMemberStatistics.getDayCount()).isEqualTo(DEFAULT_DAY_COUNT);
        assertThat(testGroupMemberStatistics.getMonthCount()).isEqualTo(DEFAULT_MONTH_COUNT);
        assertThat(testGroupMemberStatistics.getYearCount()).isEqualTo(DEFAULT_YEAR_COUNT);
        assertThat(testGroupMemberStatistics.getInDay()).isEqualTo(DEFAULT_IN_DAY);
        assertThat(testGroupMemberStatistics.getInMonth()).isEqualTo(DEFAULT_IN_MONTH);
        assertThat(testGroupMemberStatistics.getInYear()).isEqualTo(DEFAULT_IN_YEAR);
        assertThat(testGroupMemberStatistics.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    public void createGroupMemberStatisticsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupMemberStatisticsRepository.findAll().size();

        // Create the GroupMemberStatistics with an existing ID
        groupMemberStatistics.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupMemberStatisticsMockMvc.perform(post("/api/group-member-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupMemberStatistics)))
            .andExpect(status().isBadRequest());

        // Validate the GroupMemberStatistics in the database
        List<GroupMemberStatistics> groupMemberStatisticsList = groupMemberStatisticsRepository.findAll();
        assertThat(groupMemberStatisticsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllGroupMemberStatistics() throws Exception {
        // Initialize the database
        groupMemberStatisticsRepository.save(groupMemberStatistics);

        // Get all the groupMemberStatisticsList
        restGroupMemberStatisticsMockMvc.perform(get("/api/group-member-statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupMemberStatistics.getId())))
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
    public void getGroupMemberStatistics() throws Exception {
        // Initialize the database
        groupMemberStatisticsRepository.save(groupMemberStatistics);

        // Get the groupMemberStatistics
        restGroupMemberStatisticsMockMvc.perform(get("/api/group-member-statistics/{id}", groupMemberStatistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupMemberStatistics.getId()))
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
    public void getNonExistingGroupMemberStatistics() throws Exception {
        // Get the groupMemberStatistics
        restGroupMemberStatisticsMockMvc.perform(get("/api/group-member-statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGroupMemberStatistics() throws Exception {
        // Initialize the database
        groupMemberStatisticsRepository.save(groupMemberStatistics);
        int databaseSizeBeforeUpdate = groupMemberStatisticsRepository.findAll().size();

        // Update the groupMemberStatistics
        GroupMemberStatistics updatedGroupMemberStatistics = groupMemberStatisticsRepository.findOne(groupMemberStatistics.getId());
        updatedGroupMemberStatistics
            .seqId(UPDATED_SEQ_ID)
            .dayCount(UPDATED_DAY_COUNT)
            .monthCount(UPDATED_MONTH_COUNT)
            .yearCount(UPDATED_YEAR_COUNT)
            .inDay(UPDATED_IN_DAY)
            .inMonth(UPDATED_IN_MONTH)
            .inYear(UPDATED_IN_YEAR)
            .status(UPDATED_STATUS);

        restGroupMemberStatisticsMockMvc.perform(put("/api/group-member-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupMemberStatistics)))
            .andExpect(status().isOk());

        // Validate the GroupMemberStatistics in the database
        List<GroupMemberStatistics> groupMemberStatisticsList = groupMemberStatisticsRepository.findAll();
        assertThat(groupMemberStatisticsList).hasSize(databaseSizeBeforeUpdate);
        GroupMemberStatistics testGroupMemberStatistics = groupMemberStatisticsList.get(groupMemberStatisticsList.size() - 1);
        assertThat(testGroupMemberStatistics.getSeqId()).isEqualTo(UPDATED_SEQ_ID);
        assertThat(testGroupMemberStatistics.getDayCount()).isEqualTo(UPDATED_DAY_COUNT);
        assertThat(testGroupMemberStatistics.getMonthCount()).isEqualTo(UPDATED_MONTH_COUNT);
        assertThat(testGroupMemberStatistics.getYearCount()).isEqualTo(UPDATED_YEAR_COUNT);
        assertThat(testGroupMemberStatistics.getInDay()).isEqualTo(UPDATED_IN_DAY);
        assertThat(testGroupMemberStatistics.getInMonth()).isEqualTo(UPDATED_IN_MONTH);
        assertThat(testGroupMemberStatistics.getInYear()).isEqualTo(UPDATED_IN_YEAR);
        assertThat(testGroupMemberStatistics.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    public void updateNonExistingGroupMemberStatistics() throws Exception {
        int databaseSizeBeforeUpdate = groupMemberStatisticsRepository.findAll().size();

        // Create the GroupMemberStatistics

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGroupMemberStatisticsMockMvc.perform(put("/api/group-member-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupMemberStatistics)))
            .andExpect(status().isCreated());

        // Validate the GroupMemberStatistics in the database
        List<GroupMemberStatistics> groupMemberStatisticsList = groupMemberStatisticsRepository.findAll();
        assertThat(groupMemberStatisticsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteGroupMemberStatistics() throws Exception {
        // Initialize the database
        groupMemberStatisticsRepository.save(groupMemberStatistics);
        int databaseSizeBeforeDelete = groupMemberStatisticsRepository.findAll().size();

        // Get the groupMemberStatistics
        restGroupMemberStatisticsMockMvc.perform(delete("/api/group-member-statistics/{id}", groupMemberStatistics.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GroupMemberStatistics> groupMemberStatisticsList = groupMemberStatisticsRepository.findAll();
        assertThat(groupMemberStatisticsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupMemberStatistics.class);
        GroupMemberStatistics groupMemberStatistics1 = new GroupMemberStatistics();
        groupMemberStatistics1.setId("id1");
        GroupMemberStatistics groupMemberStatistics2 = new GroupMemberStatistics();
        groupMemberStatistics2.setId(groupMemberStatistics1.getId());
        assertThat(groupMemberStatistics1).isEqualTo(groupMemberStatistics2);
        groupMemberStatistics2.setId("id2");
        assertThat(groupMemberStatistics1).isNotEqualTo(groupMemberStatistics2);
        groupMemberStatistics1.setId(null);
        assertThat(groupMemberStatistics1).isNotEqualTo(groupMemberStatistics2);
    }
}
