package com.hdmon.chatservice.web.rest;

import com.hdmon.chatservice.ChatserviceApp;
import com.hdmon.chatservice.config.SecurityBeanOverrideConfiguration;
import com.hdmon.chatservice.domain.ChatGroupsEntity;
import com.hdmon.chatservice.domain.enumeration.GroupMemberStatusEnum;
import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;
import com.hdmon.chatservice.domain.extents.extGroupMemberEntity;
import com.hdmon.chatservice.repository.ChatGroupStatisticsRepository;
import com.hdmon.chatservice.repository.ChatGroupsRepository;
import com.hdmon.chatservice.service.ChatGroupsService;
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
 * Test class for the ChatGroupsResource REST controller.
 *
 * @see ChatGroupsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatserviceApp.class, SecurityBeanOverrideConfiguration.class})
public class ChatGroupsResourceIntTest {

    private static final String DEFAULT_SEQ_ID = "AAAAAAAAAA";
    private static final String UPDATED_SEQ_ID = "BBBBBBBBBB";

    private static final GroupTypeEnum DEFAULT_GROUP_TYPE = GroupTypeEnum.PUBLIC;
    private static final GroupTypeEnum UPDATED_GROUP_TYPE = GroupTypeEnum.FANPAGE;

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_ICON = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_SLOGAN = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_SLOGAN = "BBBBBBBBBB";

    private static final GroupMemberStatusEnum DEFAULT_GROUP_STATUS = GroupMemberStatusEnum.NORMAL;
    private static final GroupMemberStatusEnum UPDATED_GROUP_STATUS = GroupMemberStatusEnum.NORMAL;

    private static final Long DEFAULT_OWNER_ID = 1L;
    private static final Long UPDATED_OWNER_ID = 2L;

    private static final String DEFAULT_OWNER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_LOGIN = "BBBBBBBBBB";

    private static final List<extGroupMemberEntity> DEFAULT_MEMBER_LISTS = new ArrayList<>();
    private static final List<extGroupMemberEntity> UPDATED_MEMBER_LISTS = new ArrayList<>();

    private static final Integer DEFAULT_MAX_MEMBER = 1;
    private static final Integer UPDATED_MAX_MEMBER = 2;

    private static final Integer DEFAULT_MEMBER_COUNT = 1;
    private static final Integer UPDATED_MEMBER_COUNT = 2;

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

    private static final Long DEFAULT_LAST_CHAT_UNIX_TIME = 1L;
    private static final Long UPDATED_LAST_CHAT_UNIX_TIME = 2L;

    private static final Integer DEFAULT_REPORT_DAY = 1;
    private static final Integer UPDATED_REPORT_DAY = 2;

    @Autowired
    private ChatGroupsRepository chatGroupsRepository;

    @Autowired
    private ChatGroupsService chatGroupsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restGroupMembersMockMvc;

    private ChatGroupsEntity chatGroups;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChatGroupsResource groupMembersResource = new ChatGroupsResource(chatGroupsService);
        this.restGroupMembersMockMvc = MockMvcBuilders.standaloneSetup(groupMembersResource)
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
    public static ChatGroupsEntity createEntity() {
        ChatGroupsEntity chatGroups = new ChatGroupsEntity()
            .groupType(DEFAULT_GROUP_TYPE)
            .groupName(DEFAULT_GROUP_NAME)
            .groupIcon(DEFAULT_GROUP_ICON)
            .groupSlogan(DEFAULT_GROUP_SLOGAN)
            .groupStatus(DEFAULT_GROUP_STATUS)
            .ownerUserid(DEFAULT_OWNER_ID)
            .memberLists(DEFAULT_MEMBER_LISTS)
            .maxMember(DEFAULT_MAX_MEMBER)
            .createdTime(DEFAULT_CREATED_UNIX_TIME)
            .lastModifiedTime(DEFAULT_LAST_MODIFIED_UNIX_TIME)
            .reportDay(DEFAULT_REPORT_DAY);
        return chatGroups;
    }

    @Before
    public void initTest() {
        chatGroupsRepository.deleteAll();
        chatGroups = createEntity();
    }

    @Test
    public void createGroupMembers() throws Exception {
        int databaseSizeBeforeCreate = chatGroupsRepository.findAll().size();

        // Create the ChatGroups
        restGroupMembersMockMvc.perform(post("/api/chat-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chatGroups)))
            .andExpect(status().isCreated());

        // Validate the ChatGroups in the database
        List<ChatGroupsEntity> chatGroupsList = chatGroupsRepository.findAll();
        assertThat(chatGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        ChatGroupsEntity testChatGroups = chatGroupsList.get(chatGroupsList.size() - 1);
        assertThat(testChatGroups.getGroupType()).isEqualTo(DEFAULT_GROUP_TYPE);
        assertThat(testChatGroups.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
        assertThat(testChatGroups.getGroupIcon()).isEqualTo(DEFAULT_GROUP_ICON);
        assertThat(testChatGroups.getGroupSlogan()).isEqualTo(DEFAULT_GROUP_SLOGAN);
        assertThat(testChatGroups.getGroupStatus()).isEqualTo(DEFAULT_GROUP_STATUS);
        assertThat(testChatGroups.getOwnerUserid()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testChatGroups.getMemberLists()).isEqualTo(DEFAULT_MEMBER_LISTS);
        assertThat(testChatGroups.getMaxMember()).isEqualTo(DEFAULT_MAX_MEMBER);
        assertThat(testChatGroups.getOwnerUsername()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testChatGroups.getCreatedTime()).isEqualTo(DEFAULT_CREATED_UNIX_TIME);
        assertThat(testChatGroups.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testChatGroups.getLastModifiedTime()).isEqualTo(DEFAULT_LAST_MODIFIED_UNIX_TIME);
        assertThat(testChatGroups.getReportDay()).isEqualTo(DEFAULT_REPORT_DAY);
    }

    @Test
    public void createGroupMembersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chatGroupsRepository.findAll().size();

        // Create the ChatGroups with an existing ID
        chatGroups.setGroupId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupMembersMockMvc.perform(post("/api/chatgroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chatGroups)))
            .andExpect(status().isBadRequest());

        // Validate the ChatGroups in the database
        List<ChatGroupsEntity> chatGroupsList = chatGroupsRepository.findAll();
        assertThat(chatGroupsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllGroupMembers() throws Exception {
        // Initialize the database
        chatGroupsRepository.save(chatGroups);

        // Get all the chatGroupsList
        restGroupMembersMockMvc.perform(get("/api/chatgroups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chatGroups.getGroupId())))
            .andExpect(jsonPath("$.[*].seqId").value(hasItem(DEFAULT_SEQ_ID.toString())))
            .andExpect(jsonPath("$.[*].groupType").value(hasItem(DEFAULT_GROUP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME.toString())))
            .andExpect(jsonPath("$.[*].groupIcon").value(hasItem(DEFAULT_GROUP_ICON.toString())))
            .andExpect(jsonPath("$.[*].groupSlogan").value(hasItem(DEFAULT_GROUP_SLOGAN.toString())))
            .andExpect(jsonPath("$.[*].groupStatus").value(hasItem(DEFAULT_GROUP_STATUS)))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID.intValue())))
            .andExpect(jsonPath("$.[*].ownerLogin").value(hasItem(DEFAULT_OWNER_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].memberLists").value(hasItem(DEFAULT_MEMBER_LISTS.toString())))
            .andExpect(jsonPath("$.[*].maxMember").value(hasItem(DEFAULT_MAX_MEMBER)))
            .andExpect(jsonPath("$.[*].memberCount").value(hasItem(DEFAULT_MEMBER_COUNT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdUnixTime").value(hasItem(DEFAULT_CREATED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedUnixTime").value(hasItem(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].lastChatUnixTime").value(hasItem(DEFAULT_LAST_CHAT_UNIX_TIME.intValue())))
            .andExpect(jsonPath("$.[*].reportDay").value(hasItem(DEFAULT_REPORT_DAY)));
    }

    @Test
    public void getGroupMembers() throws Exception {
        // Initialize the database
        chatGroupsRepository.save(chatGroups);

        // Get the chatGroups
        restGroupMembersMockMvc.perform(get("/api/chatgroups/{id}", chatGroups.getGroupId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chatGroups.getGroupId()))
            .andExpect(jsonPath("$.seqId").value(DEFAULT_SEQ_ID.toString()))
            .andExpect(jsonPath("$.groupType").value(DEFAULT_GROUP_TYPE.toString()))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME.toString()))
            .andExpect(jsonPath("$.groupIcon").value(DEFAULT_GROUP_ICON.toString()))
            .andExpect(jsonPath("$.groupSlogan").value(DEFAULT_GROUP_SLOGAN.toString()))
            .andExpect(jsonPath("$.groupStatus").value(DEFAULT_GROUP_STATUS))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID.intValue()))
            .andExpect(jsonPath("$.ownerLogin").value(DEFAULT_OWNER_LOGIN.toString()))
            .andExpect(jsonPath("$.memberLists").value(DEFAULT_MEMBER_LISTS.toString()))
            .andExpect(jsonPath("$.maxMember").value(DEFAULT_MAX_MEMBER))
            .andExpect(jsonPath("$.memberCount").value(DEFAULT_MEMBER_COUNT))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdUnixTime").value(DEFAULT_CREATED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedUnixTime").value(DEFAULT_LAST_MODIFIED_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.lastChatUnixTime").value(DEFAULT_LAST_CHAT_UNIX_TIME.intValue()))
            .andExpect(jsonPath("$.reportDay").value(DEFAULT_REPORT_DAY));
    }

    @Test
    public void getNonExistingGroupMembers() throws Exception {
        // Get the groupMembers
        restGroupMembersMockMvc.perform(get("/api/chatgroups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGroupMembers() throws Exception {
        // Initialize the database
        chatGroupsRepository.save(chatGroups);
        int databaseSizeBeforeUpdate = chatGroupsRepository.findAll().size();

        // Update the ChatGroups
        ChatGroupsEntity updatedGroupMembers = chatGroupsRepository.findOne(chatGroups.getGroupId());
        updatedGroupMembers
            .groupType(UPDATED_GROUP_TYPE)
            .groupName(UPDATED_GROUP_NAME)
            .groupIcon(UPDATED_GROUP_ICON)
            .groupSlogan(UPDATED_GROUP_SLOGAN)
            .groupStatus(UPDATED_GROUP_STATUS)
            .ownerUserid(UPDATED_OWNER_ID)
            .memberLists(UPDATED_MEMBER_LISTS)
            .maxMember(UPDATED_MAX_MEMBER)
            .createdTime(UPDATED_CREATED_UNIX_TIME)
            .lastModifiedTime(UPDATED_LAST_MODIFIED_UNIX_TIME)
            .reportDay(UPDATED_REPORT_DAY);

        restGroupMembersMockMvc.perform(put("/api/chatgroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupMembers)))
            .andExpect(status().isOk());

        // Validate the ChatGroups in the database
        List<ChatGroupsEntity> chatGroupsList = chatGroupsRepository.findAll();
        assertThat(chatGroupsList).hasSize(databaseSizeBeforeUpdate);
        ChatGroupsEntity testChatGroups = chatGroupsList.get(chatGroupsList.size() - 1);
        assertThat(testChatGroups.getGroupType()).isEqualTo(UPDATED_GROUP_TYPE);
        assertThat(testChatGroups.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testChatGroups.getGroupIcon()).isEqualTo(UPDATED_GROUP_ICON);
        assertThat(testChatGroups.getGroupSlogan()).isEqualTo(UPDATED_GROUP_SLOGAN);
        assertThat(testChatGroups.getGroupStatus()).isEqualTo(UPDATED_GROUP_STATUS);
        assertThat(testChatGroups.getOwnerUserid()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testChatGroups.getMemberLists()).isEqualTo(UPDATED_MEMBER_LISTS);
        assertThat(testChatGroups.getMaxMember()).isEqualTo(UPDATED_MAX_MEMBER);
        assertThat(testChatGroups.getOwnerUsername()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testChatGroups.getCreatedTime()).isEqualTo(UPDATED_CREATED_UNIX_TIME);
        assertThat(testChatGroups.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testChatGroups.getLastModifiedTime()).isEqualTo(UPDATED_LAST_MODIFIED_UNIX_TIME);
        assertThat(testChatGroups.getReportDay()).isEqualTo(UPDATED_REPORT_DAY);
    }

    @Test
    public void updateNonExistingGroupMembers() throws Exception {
        int databaseSizeBeforeUpdate = chatGroupsRepository.findAll().size();

        // Create the ChatGroups

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGroupMembersMockMvc.perform(put("/api/chatgroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chatGroups)))
            .andExpect(status().isCreated());

        // Validate the ChatGroups in the database
        List<ChatGroupsEntity> groupMembersList = chatGroupsRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteGroupMembers() throws Exception {
        // Initialize the database
        chatGroupsRepository.save(chatGroups);
        int databaseSizeBeforeDelete = chatGroupsRepository.findAll().size();

        // Get the chatGroups
        restGroupMembersMockMvc.perform(delete("/api/chatgroups/{id}", chatGroups.getGroupId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChatGroupsEntity> chatGroupsList = chatGroupsRepository.findAll();
        assertThat(chatGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChatGroupsEntity.class);
        ChatGroupsEntity chatGroups1 = new ChatGroupsEntity();
        chatGroups1.setGroupId("id1");
        ChatGroupsEntity chatGroups2 = new ChatGroupsEntity();
        chatGroups2.setGroupId(chatGroups1.getGroupId());
        assertThat(chatGroups1).isEqualTo(chatGroups2);
        chatGroups2.setGroupId("id2");
        assertThat(chatGroups1).isNotEqualTo(chatGroups2);
        chatGroups1.setGroupId(null);
        assertThat(chatGroups1).isNotEqualTo(chatGroups2);
    }
}
