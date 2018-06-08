package com.hdmon.chatservice.web.rest;

import com.hdmon.chatservice.ChatserviceApp;
import com.hdmon.chatservice.config.SecurityBeanOverrideConfiguration;
import com.hdmon.chatservice.domain.ChatMessageStatisticsEntity;
import com.hdmon.chatservice.domain.enumeration.CheckStatusEnum;
import com.hdmon.chatservice.repository.ChatMessageStatisticsRepository;
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
 * Test class for the ChatMessageStatisticsResource REST controller.
 *
 * @see ChatMessageStatisticsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatserviceApp.class, SecurityBeanOverrideConfiguration.class})
public class ChatMessageStatisticsResourceIntTest {

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
    private ChatMessageStatisticsRepository chatMessageStatisticsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restChatMessageStatisticsMockMvc;

    private ChatMessageStatisticsEntity chatMessageStatistics;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChatMessageStatisticsResource chatMessageStatisticsResource = new ChatMessageStatisticsResource(chatMessageStatisticsRepository);
        this.restChatMessageStatisticsMockMvc = MockMvcBuilders.standaloneSetup(chatMessageStatisticsResource)
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
    public static ChatMessageStatisticsEntity createEntity() {
        ChatMessageStatisticsEntity chatMessageStatistics = new ChatMessageStatisticsEntity()
            .dayCount(DEFAULT_DAY_COUNT)
            .monthCount(DEFAULT_MONTH_COUNT)
            .yearCount(DEFAULT_YEAR_COUNT)
            .inDay(DEFAULT_IN_DAY)
            .inMonth(DEFAULT_IN_MONTH)
            .inYear(DEFAULT_IN_YEAR)
            .status(DEFAULT_STATUS);
        return chatMessageStatistics;
    }

    @Before
    public void initTest() {
        chatMessageStatisticsRepository.deleteAll();
        chatMessageStatistics = createEntity();
    }

    @Test
    public void createChatMessageStatistics() throws Exception {
        int databaseSizeBeforeCreate = chatMessageStatisticsRepository.findAll().size();

        // Create the ChatMessageStatistics
        restChatMessageStatisticsMockMvc.perform(post("/api/chat-message-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chatMessageStatistics)))
            .andExpect(status().isCreated());

        // Validate the ChatMessageStatistics in the database
        List<ChatMessageStatisticsEntity> chatMessageStatisticsList = chatMessageStatisticsRepository.findAll();
        assertThat(chatMessageStatisticsList).hasSize(databaseSizeBeforeCreate + 1);
        ChatMessageStatisticsEntity testChatMessageStatistics = chatMessageStatisticsList.get(chatMessageStatisticsList.size() - 1);
        assertThat(testChatMessageStatistics.getDayCount()).isEqualTo(DEFAULT_DAY_COUNT);
        assertThat(testChatMessageStatistics.getMonthCount()).isEqualTo(DEFAULT_MONTH_COUNT);
        assertThat(testChatMessageStatistics.getYearCount()).isEqualTo(DEFAULT_YEAR_COUNT);
        assertThat(testChatMessageStatistics.getInDay()).isEqualTo(DEFAULT_IN_DAY);
        assertThat(testChatMessageStatistics.getInMonth()).isEqualTo(DEFAULT_IN_MONTH);
        assertThat(testChatMessageStatistics.getInYear()).isEqualTo(DEFAULT_IN_YEAR);
        assertThat(testChatMessageStatistics.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    public void createChatMessageStatisticsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chatMessageStatisticsRepository.findAll().size();

        // Create the ChatMessageStatistics with an existing ID
        chatMessageStatistics.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restChatMessageStatisticsMockMvc.perform(post("/api/chat-message-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chatMessageStatistics)))
            .andExpect(status().isBadRequest());

        // Validate the ChatMessageStatistics in the database
        List<ChatMessageStatisticsEntity> chatMessageStatisticsList = chatMessageStatisticsRepository.findAll();
        assertThat(chatMessageStatisticsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllChatMessageStatistics() throws Exception {
        // Initialize the database
        chatMessageStatisticsRepository.save(chatMessageStatistics);

        // Get all the chatMessageStatisticsList
        restChatMessageStatisticsMockMvc.perform(get("/api/chat-message-statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chatMessageStatistics.getId())))
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
    public void getChatMessageStatistics() throws Exception {
        // Initialize the database
        chatMessageStatisticsRepository.save(chatMessageStatistics);

        // Get the chatMessageStatistics
        restChatMessageStatisticsMockMvc.perform(get("/api/chat-message-statistics/{id}", chatMessageStatistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chatMessageStatistics.getId()))
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
    public void getNonExistingChatMessageStatistics() throws Exception {
        // Get the chatMessageStatistics
        restChatMessageStatisticsMockMvc.perform(get("/api/chat-message-statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateChatMessageStatistics() throws Exception {
        // Initialize the database
        chatMessageStatisticsRepository.save(chatMessageStatistics);
        int databaseSizeBeforeUpdate = chatMessageStatisticsRepository.findAll().size();

        // Update the chatMessageStatistics
        ChatMessageStatisticsEntity updatedChatMessageStatistics = chatMessageStatisticsRepository.findOne(chatMessageStatistics.getId());
        updatedChatMessageStatistics
            .dayCount(UPDATED_DAY_COUNT)
            .monthCount(UPDATED_MONTH_COUNT)
            .yearCount(UPDATED_YEAR_COUNT)
            .inDay(UPDATED_IN_DAY)
            .inMonth(UPDATED_IN_MONTH)
            .inYear(UPDATED_IN_YEAR)
            .status(UPDATED_STATUS);

        restChatMessageStatisticsMockMvc.perform(put("/api/chat-message-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChatMessageStatistics)))
            .andExpect(status().isOk());

        // Validate the ChatMessageStatistics in the database
        List<ChatMessageStatisticsEntity> chatMessageStatisticsList = chatMessageStatisticsRepository.findAll();
        assertThat(chatMessageStatisticsList).hasSize(databaseSizeBeforeUpdate);
        ChatMessageStatisticsEntity testChatMessageStatistics = chatMessageStatisticsList.get(chatMessageStatisticsList.size() - 1);
        assertThat(testChatMessageStatistics.getDayCount()).isEqualTo(UPDATED_DAY_COUNT);
        assertThat(testChatMessageStatistics.getMonthCount()).isEqualTo(UPDATED_MONTH_COUNT);
        assertThat(testChatMessageStatistics.getYearCount()).isEqualTo(UPDATED_YEAR_COUNT);
        assertThat(testChatMessageStatistics.getInDay()).isEqualTo(UPDATED_IN_DAY);
        assertThat(testChatMessageStatistics.getInMonth()).isEqualTo(UPDATED_IN_MONTH);
        assertThat(testChatMessageStatistics.getInYear()).isEqualTo(UPDATED_IN_YEAR);
        assertThat(testChatMessageStatistics.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    public void updateNonExistingChatMessageStatistics() throws Exception {
        int databaseSizeBeforeUpdate = chatMessageStatisticsRepository.findAll().size();

        // Create the ChatMessageStatistics

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChatMessageStatisticsMockMvc.perform(put("/api/chat-message-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chatMessageStatistics)))
            .andExpect(status().isCreated());

        // Validate the ChatMessageStatistics in the database
        List<ChatMessageStatisticsEntity> chatMessageStatisticsList = chatMessageStatisticsRepository.findAll();
        assertThat(chatMessageStatisticsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteChatMessageStatistics() throws Exception {
        // Initialize the database
        chatMessageStatisticsRepository.save(chatMessageStatistics);
        int databaseSizeBeforeDelete = chatMessageStatisticsRepository.findAll().size();

        // Get the chatMessageStatistics
        restChatMessageStatisticsMockMvc.perform(delete("/api/chat-message-statistics/{id}", chatMessageStatistics.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChatMessageStatisticsEntity> chatMessageStatisticsList = chatMessageStatisticsRepository.findAll();
        assertThat(chatMessageStatisticsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChatMessageStatisticsEntity.class);
        ChatMessageStatisticsEntity chatMessageStatistics1 = new ChatMessageStatisticsEntity();
        chatMessageStatistics1.setId("id1");
        ChatMessageStatisticsEntity chatMessageStatistics2 = new ChatMessageStatisticsEntity();
        chatMessageStatistics2.setId(chatMessageStatistics1.getId());
        assertThat(chatMessageStatistics1).isEqualTo(chatMessageStatistics2);
        chatMessageStatistics2.setId("id2");
        assertThat(chatMessageStatistics1).isNotEqualTo(chatMessageStatistics2);
        chatMessageStatistics1.setId(null);
        assertThat(chatMessageStatistics1).isNotEqualTo(chatMessageStatistics2);
    }
}
