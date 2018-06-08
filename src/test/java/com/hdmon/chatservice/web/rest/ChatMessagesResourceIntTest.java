package com.hdmon.chatservice.web.rest;

import com.hdmon.chatservice.ChatserviceApp;
import com.hdmon.chatservice.config.SecurityBeanOverrideConfiguration;
import com.hdmon.chatservice.domain.ChatMessagesEntity;
import com.hdmon.chatservice.domain.enumeration.ChatMessageTypeEnum;
import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;
import com.hdmon.chatservice.domain.enumeration.ReceiverTypeEnum;
import com.hdmon.chatservice.domain.extents.extMessageReceiverEntity;
import com.hdmon.chatservice.repository.ChatMessagesRepository;
import com.hdmon.chatservice.service.ChatMessagesService;
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
 * Test class for the ChatMessagesResource REST controller.
 *
 * @see ChatMessagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatserviceApp.class, SecurityBeanOverrideConfiguration.class})
public class ChatMessagesResourceIntTest {

    private static final String DEFAULT_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_CHAT_ID = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_CHAT_ID = "BBBBBBBBBB";

    private static final GroupTypeEnum DEFAULT_GROUP_TYPE = GroupTypeEnum.PUBLIC;
    private static final GroupTypeEnum UPDATED_GROUP_TYPE = GroupTypeEnum.FANPAGE;

    private static final String DEFAULT_MESSAGE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_VALUE = "BBBBBBBBBB";

    private static final ChatMessageTypeEnum DEFAULT_MESSAGE_TYPE = ChatMessageTypeEnum.TEXT;
    private static final ChatMessageTypeEnum UPDATED_MESSAGE_TYPE = ChatMessageTypeEnum.FILE;

    private static final Long DEFAULT_SENDER_ID = 1L;
    private static final Long UPDATED_SENDER_ID = 2L;

    private static final String DEFAULT_SENDER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_SENDER_LOGIN = "BBBBBBBBBB";

    private static final List<extMessageReceiverEntity> DEFAULT_RECEIVER_LISTS = new ArrayList<>();
    private static final List<extMessageReceiverEntity> UPDATED_RECEIVER_LISTS = new ArrayList<>();

    private static final ReceiverTypeEnum DEFAULT_RECEIVER_TYPE = ReceiverTypeEnum.FRIEND;
    private static final ReceiverTypeEnum UPDATED_RECEIVER_TYPE = ReceiverTypeEnum.FRIEND;

    private static final String DEFAULT_RECEIVER_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_TEXT = "BBBBBBBBBB";

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

    private static final Integer DEFAULT_MAX_TIME_TO_ACTION = 1;
    private static final Integer UPDATED_MAX_TIME_TO_ACTION = 2;

    private static final String DEFAULT_REFER_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_REFER_MESSAGE_ID = "BBBBBBBBBB";

    @Autowired
    private ChatMessagesRepository chatMessagesRepository;

    @Autowired
    private ChatMessagesService chatMessagesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restChatMessagesMockMvc;

    private ChatMessagesEntity chatMessages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChatMessagesResource chatMessagesResource = new ChatMessagesResource(chatMessagesService);
        this.restChatMessagesMockMvc = MockMvcBuilders.standaloneSetup(chatMessagesResource)
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
    public static ChatMessagesEntity createEntity() {
        ChatMessagesEntity chatMessages = new ChatMessagesEntity()
            .messageId(DEFAULT_MESSAGE_ID)
            .groupChatId(DEFAULT_GROUP_CHAT_ID)
            .groupType(DEFAULT_GROUP_TYPE)
            .messageValue(DEFAULT_MESSAGE_VALUE)
            .messageType(DEFAULT_MESSAGE_TYPE)
            .senderId(DEFAULT_SENDER_ID)
            .senderLogin(DEFAULT_SENDER_LOGIN)
            .receiverLists(DEFAULT_RECEIVER_LISTS)
            .receiverType(DEFAULT_RECEIVER_TYPE)
            .receiverText(DEFAULT_RECEIVER_TEXT)
            .createdUnixTime(DEFAULT_CREATED_UNIX_TIME)
            .lastModifiedUnixTime(DEFAULT_LAST_MODIFIED_UNIX_TIME)
            .reportDay(DEFAULT_REPORT_DAY)
            .maxTimeToAction(DEFAULT_MAX_TIME_TO_ACTION)
            .referMessageId(DEFAULT_REFER_MESSAGE_ID);
        return chatMessages;
    }

    @Before
    public void initTest() {
        chatMessagesRepository.deleteAll();
        chatMessages = createEntity();
    }

    @Test
    public void createChatMessages() throws Exception {
        int databaseSizeBeforeCreate = chatMessagesRepository.findAll().size();

        // Create the ChatMessages
        restChatMessagesMockMvc.perform(post("/api/chat-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chatMessages)))
            .andExpect(status().isCreated());

        // Validate the ChatMessages in the database
        List<ChatMessagesEntity> chatMessagesList = chatMessagesRepository.findAll();
        assertThat(chatMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        ChatMessagesEntity testChatMessages = chatMessagesList.get(chatMessagesList.size() - 1);
        assertThat(testChatMessages.getMessageId()).isEqualTo(DEFAULT_MESSAGE_ID);
        assertThat(testChatMessages.getGroupChatId()).isEqualTo(DEFAULT_GROUP_CHAT_ID);
        assertThat(testChatMessages.getGroupType()).isEqualTo(DEFAULT_GROUP_TYPE);
        assertThat(testChatMessages.getMessageValue()).isEqualTo(DEFAULT_MESSAGE_VALUE);
        assertThat(testChatMessages.getMessageType()).isEqualTo(DEFAULT_MESSAGE_TYPE);
        assertThat(testChatMessages.getSenderId()).isEqualTo(DEFAULT_SENDER_ID);
        assertThat(testChatMessages.getSenderLogin()).isEqualTo(DEFAULT_SENDER_LOGIN);
        assertThat(testChatMessages.getReceiverLists()).isEqualTo(DEFAULT_RECEIVER_LISTS);
        assertThat(testChatMessages.getReceiverType()).isEqualTo(DEFAULT_RECEIVER_TYPE);
        assertThat(testChatMessages.getReceiverText()).isEqualTo(DEFAULT_RECEIVER_TEXT);
        assertThat(testChatMessages.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testChatMessages.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testChatMessages.getCreatedUnixTime()).isEqualTo(DEFAULT_CREATED_UNIX_TIME);
        assertThat(testChatMessages.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testChatMessages.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testChatMessages.getLastModifiedUnixTime()).isEqualTo(DEFAULT_LAST_MODIFIED_UNIX_TIME);
        assertThat(testChatMessages.getReportDay()).isEqualTo(DEFAULT_REPORT_DAY);
        assertThat(testChatMessages.getMaxTimeToAction()).isEqualTo(DEFAULT_MAX_TIME_TO_ACTION);
        assertThat(testChatMessages.getReferMessageId()).isEqualTo(DEFAULT_REFER_MESSAGE_ID);
    }

    @Test
    public void createChatMessagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chatMessagesRepository.findAll().size();

        // Create the ChatMessages with an existing ID
        chatMessages.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restChatMessagesMockMvc.perform(post("/api/chat-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chatMessages)))
            .andExpect(status().isBadRequest());

        // Validate the ChatMessages in the database
        List<ChatMessagesEntity> chatMessagesList = chatMessagesRepository.findAll();
        assertThat(chatMessagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllChatMessages() throws Exception {
        // Initialize the database
        chatMessagesRepository.save(chatMessages);

        // Get all the chatMessagesList
        restChatMessagesMockMvc.perform(get("/api/chat-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chatMessages.getId())))
            .andExpect(jsonPath("$.[*].messageId").value(hasItem(DEFAULT_MESSAGE_ID.toString())))
            .andExpect(jsonPath("$.[*].groupChatId").value(hasItem(DEFAULT_GROUP_CHAT_ID.toString())))
            .andExpect(jsonPath("$.[*].groupType").value(hasItem(DEFAULT_GROUP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].messageValue").value(hasItem(DEFAULT_MESSAGE_VALUE.toString())))
            .andExpect(jsonPath("$.[*].messageType").value(hasItem(DEFAULT_MESSAGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].senderId").value(hasItem(DEFAULT_SENDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].senderLogin").value(hasItem(DEFAULT_SENDER_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].receiverLists").value(hasItem(DEFAULT_RECEIVER_LISTS.toString())))
            .andExpect(jsonPath("$.[*].receiverType").value(hasItem(DEFAULT_RECEIVER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].receiverText").value(hasItem(DEFAULT_RECEIVER_TEXT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdUnixTime").value(hasItem(DEFAULT_CREATED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedUnixTime").value(hasItem(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].reportDay").value(hasItem(DEFAULT_REPORT_DAY)))
            .andExpect(jsonPath("$.[*].maxTimeToAction").value(hasItem(DEFAULT_MAX_TIME_TO_ACTION)))
            .andExpect(jsonPath("$.[*].referMessageId").value(hasItem(DEFAULT_REFER_MESSAGE_ID)));
    }

    @Test
    public void getChatMessages() throws Exception {
        // Initialize the database
        chatMessagesRepository.save(chatMessages);

        // Get the chatMessages
        restChatMessagesMockMvc.perform(get("/api/chat-messages/{id}", chatMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chatMessages.getId()))
            .andExpect(jsonPath("$.messageId").value(DEFAULT_MESSAGE_ID.toString()))
            .andExpect(jsonPath("$.groupChatId").value(DEFAULT_GROUP_CHAT_ID.toString()))
            .andExpect(jsonPath("$.groupType").value(DEFAULT_GROUP_TYPE.toString()))
            .andExpect(jsonPath("$.messageValue").value(DEFAULT_MESSAGE_VALUE.toString()))
            .andExpect(jsonPath("$.messageType").value(DEFAULT_MESSAGE_TYPE.toString()))
            .andExpect(jsonPath("$.senderId").value(DEFAULT_SENDER_ID.intValue()))
            .andExpect(jsonPath("$.senderLogin").value(DEFAULT_SENDER_LOGIN.toString()))
            .andExpect(jsonPath("$.receiverLists").value(DEFAULT_RECEIVER_LISTS.toString()))
            .andExpect(jsonPath("$.receiverType").value(DEFAULT_RECEIVER_TYPE.toString()))
            .andExpect(jsonPath("$.receiverText").value(DEFAULT_RECEIVER_TEXT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdUnixTime").value(DEFAULT_CREATED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedUnixTime").value(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.reportDay").value(DEFAULT_REPORT_DAY))
            .andExpect(jsonPath("$.maxTimeToAction").value(DEFAULT_MAX_TIME_TO_ACTION))
            .andExpect(jsonPath("$.referMessageId").value(DEFAULT_REFER_MESSAGE_ID));
    }

    @Test
    public void getNonExistingChatMessages() throws Exception {
        // Get the chatMessages
        restChatMessagesMockMvc.perform(get("/api/chat-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateChatMessages() throws Exception {
        // Initialize the database
        chatMessagesRepository.save(chatMessages);
        int databaseSizeBeforeUpdate = chatMessagesRepository.findAll().size();

        // Update the chatMessages
        ChatMessagesEntity updatedChatMessages = chatMessagesRepository.findOne(chatMessages.getId());
        updatedChatMessages
            .messageId(UPDATED_MESSAGE_ID)
            .groupChatId(UPDATED_GROUP_CHAT_ID)
            .groupType(UPDATED_GROUP_TYPE)
            .messageValue(UPDATED_MESSAGE_VALUE)
            .messageType(UPDATED_MESSAGE_TYPE)
            .senderId(UPDATED_SENDER_ID)
            .senderLogin(UPDATED_SENDER_LOGIN)
            .receiverLists(UPDATED_RECEIVER_LISTS)
            .receiverType(UPDATED_RECEIVER_TYPE)
            .receiverText(UPDATED_RECEIVER_TEXT)
            .createdUnixTime(UPDATED_CREATED_UNIX_TIME)
            .lastModifiedUnixTime(UPDATED_LAST_MODIFIED_UNIX_TIME)
            .reportDay(UPDATED_REPORT_DAY)
            .maxTimeToAction(UPDATED_MAX_TIME_TO_ACTION)
            .referMessageId(UPDATED_REFER_MESSAGE_ID);

        restChatMessagesMockMvc.perform(put("/api/chat-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChatMessages)))
            .andExpect(status().isOk());

        // Validate the ChatMessages in the database
        List<ChatMessagesEntity> chatMessagesList = chatMessagesRepository.findAll();
        assertThat(chatMessagesList).hasSize(databaseSizeBeforeUpdate);
        ChatMessagesEntity testChatMessages = chatMessagesList.get(chatMessagesList.size() - 1);
        assertThat(testChatMessages.getMessageId()).isEqualTo(UPDATED_MESSAGE_ID);
        assertThat(testChatMessages.getGroupChatId()).isEqualTo(UPDATED_GROUP_CHAT_ID);
        assertThat(testChatMessages.getGroupType()).isEqualTo(UPDATED_GROUP_TYPE);
        assertThat(testChatMessages.getMessageValue()).isEqualTo(UPDATED_MESSAGE_VALUE);
        assertThat(testChatMessages.getMessageType()).isEqualTo(UPDATED_MESSAGE_TYPE);
        assertThat(testChatMessages.getSenderId()).isEqualTo(UPDATED_SENDER_ID);
        assertThat(testChatMessages.getSenderLogin()).isEqualTo(UPDATED_SENDER_LOGIN);
        assertThat(testChatMessages.getReceiverLists()).isEqualTo(UPDATED_RECEIVER_LISTS);
        assertThat(testChatMessages.getReceiverType()).isEqualTo(UPDATED_RECEIVER_TYPE);
        assertThat(testChatMessages.getReceiverText()).isEqualTo(UPDATED_RECEIVER_TEXT);
        assertThat(testChatMessages.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testChatMessages.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testChatMessages.getCreatedUnixTime()).isEqualTo(UPDATED_CREATED_UNIX_TIME);
        assertThat(testChatMessages.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testChatMessages.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testChatMessages.getLastModifiedUnixTime()).isEqualTo(UPDATED_LAST_MODIFIED_UNIX_TIME);
        assertThat(testChatMessages.getReportDay()).isEqualTo(UPDATED_REPORT_DAY);
        assertThat(testChatMessages.getMaxTimeToAction()).isEqualTo(UPDATED_MAX_TIME_TO_ACTION);
        assertThat(testChatMessages.getReferMessageId()).isEqualTo(UPDATED_REFER_MESSAGE_ID);
    }

    @Test
    public void updateNonExistingChatMessages() throws Exception {
        int databaseSizeBeforeUpdate = chatMessagesRepository.findAll().size();

        // Create the ChatMessages

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChatMessagesMockMvc.perform(put("/api/chat-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chatMessages)))
            .andExpect(status().isCreated());

        // Validate the ChatMessages in the database
        List<ChatMessagesEntity> chatMessagesList = chatMessagesRepository.findAll();
        assertThat(chatMessagesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteChatMessages() throws Exception {
        // Initialize the database
        chatMessagesRepository.save(chatMessages);
        int databaseSizeBeforeDelete = chatMessagesRepository.findAll().size();

        // Get the chatMessages
        restChatMessagesMockMvc.perform(delete("/api/chat-messages/{id}", chatMessages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChatMessagesEntity> chatMessagesList = chatMessagesRepository.findAll();
        assertThat(chatMessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChatMessagesEntity.class);
        ChatMessagesEntity chatMessages1 = new ChatMessagesEntity();
        chatMessages1.setId("id1");
        ChatMessagesEntity chatMessages2 = new ChatMessagesEntity();
        chatMessages2.setId(chatMessages1.getId());
        assertThat(chatMessages1).isEqualTo(chatMessages2);
        chatMessages2.setId("id2");
        assertThat(chatMessages1).isNotEqualTo(chatMessages2);
        chatMessages1.setId(null);
        assertThat(chatMessages1).isNotEqualTo(chatMessages2);
    }
}
