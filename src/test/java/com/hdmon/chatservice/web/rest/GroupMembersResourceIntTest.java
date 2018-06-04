package com.hdmon.chatservice.web.rest;

import com.hdmon.chatservice.ChatserviceApp;
import com.hdmon.chatservice.config.SecurityBeanOverrideConfiguration;
import com.hdmon.chatservice.domain.GroupMembers;
import com.hdmon.chatservice.domain.enumeration.GroupType;
import com.hdmon.chatservice.domain.extents.extGroupMemberLists;
import com.hdmon.chatservice.repository.GroupMembersRepository;
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
 * Test class for the GroupMembersResource REST controller.
 *
 * @see GroupMembersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatserviceApp.class, SecurityBeanOverrideConfiguration.class})
public class GroupMembersResourceIntTest {

    private static final String DEFAULT_GROUP_ID = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_ID = "BBBBBBBBBB";

    private static final GroupType DEFAULT_GROUP_TYPE = GroupType.PUBLIC;
    private static final GroupType UPDATED_GROUP_TYPE = GroupType.FANPAGE;

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_ICON = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_SLOGAN = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_SLOGAN = "BBBBBBBBBB";

    private static final Integer DEFAULT_GROUP_STATUS = 1;
    private static final Integer UPDATED_GROUP_STATUS = 2;

    private static final Long DEFAULT_OWNER_ID = 1L;
    private static final Long UPDATED_OWNER_ID = 2L;

    private static final String DEFAULT_OWNER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_LOGIN = "BBBBBBBBBB";

    private static final List<extGroupMemberLists> DEFAULT_MEMBER_LISTS = new ArrayList<>();
    private static final List<extGroupMemberLists> UPDATED_MEMBER_LISTS = new ArrayList<>();

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
    private GroupMembersRepository groupMembersRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restGroupMembersMockMvc;

    private GroupMembers groupMembers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupMembersResource groupMembersResource = new GroupMembersResource(groupMembersRepository);
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
    public static GroupMembers createEntity() {
        GroupMembers groupMembers = new GroupMembers()
            .groupId(DEFAULT_GROUP_ID)
            .groupType(DEFAULT_GROUP_TYPE)
            .groupName(DEFAULT_GROUP_NAME)
            .groupIcon(DEFAULT_GROUP_ICON)
            .groupSlogan(DEFAULT_GROUP_SLOGAN)
            .groupStatus(DEFAULT_GROUP_STATUS)
            .ownerId(DEFAULT_OWNER_ID)
            .ownerLogin(DEFAULT_OWNER_LOGIN)
            .memberLists(DEFAULT_MEMBER_LISTS)
            .maxMember(DEFAULT_MAX_MEMBER)
            .memberCount(DEFAULT_MEMBER_COUNT)
            .createdUnixTime(DEFAULT_CREATED_UNIX_TIME)
            .lastModifiedUnixTime(DEFAULT_LAST_MODIFIED_UNIX_TIME)
            .lastChatUnixTime(DEFAULT_LAST_CHAT_UNIX_TIME)
            .reportDay(DEFAULT_REPORT_DAY);
        return groupMembers;
    }

    @Before
    public void initTest() {
        groupMembersRepository.deleteAll();
        groupMembers = createEntity();
    }

    @Test
    public void createGroupMembers() throws Exception {
        int databaseSizeBeforeCreate = groupMembersRepository.findAll().size();

        // Create the GroupMembers
        restGroupMembersMockMvc.perform(post("/api/group-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupMembers)))
            .andExpect(status().isCreated());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeCreate + 1);
        GroupMembers testGroupMembers = groupMembersList.get(groupMembersList.size() - 1);
        assertThat(testGroupMembers.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testGroupMembers.getGroupType()).isEqualTo(DEFAULT_GROUP_TYPE);
        assertThat(testGroupMembers.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
        assertThat(testGroupMembers.getGroupIcon()).isEqualTo(DEFAULT_GROUP_ICON);
        assertThat(testGroupMembers.getGroupSlogan()).isEqualTo(DEFAULT_GROUP_SLOGAN);
        assertThat(testGroupMembers.getGroupStatus()).isEqualTo(DEFAULT_GROUP_STATUS);
        assertThat(testGroupMembers.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testGroupMembers.getOwnerLogin()).isEqualTo(DEFAULT_OWNER_LOGIN);
        assertThat(testGroupMembers.getMemberLists()).isEqualTo(DEFAULT_MEMBER_LISTS);
        assertThat(testGroupMembers.getMaxMember()).isEqualTo(DEFAULT_MAX_MEMBER);
        assertThat(testGroupMembers.getMemberCount()).isEqualTo(DEFAULT_MEMBER_COUNT);
        assertThat(testGroupMembers.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testGroupMembers.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testGroupMembers.getCreatedUnixTime()).isEqualTo(DEFAULT_CREATED_UNIX_TIME);
        assertThat(testGroupMembers.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testGroupMembers.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testGroupMembers.getLastModifiedUnixTime()).isEqualTo(DEFAULT_LAST_MODIFIED_UNIX_TIME);
        assertThat(testGroupMembers.getLastChatUnixTime()).isEqualTo(DEFAULT_LAST_CHAT_UNIX_TIME);
        assertThat(testGroupMembers.getReportDay()).isEqualTo(DEFAULT_REPORT_DAY);
    }

    @Test
    public void createGroupMembersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupMembersRepository.findAll().size();

        // Create the GroupMembers with an existing ID
        groupMembers.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupMembersMockMvc.perform(post("/api/group-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupMembers)))
            .andExpect(status().isBadRequest());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllGroupMembers() throws Exception {
        // Initialize the database
        groupMembersRepository.save(groupMembers);

        // Get all the groupMembersList
        restGroupMembersMockMvc.perform(get("/api/group-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupMembers.getId())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.toString())))
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
        groupMembersRepository.save(groupMembers);

        // Get the groupMembers
        restGroupMembersMockMvc.perform(get("/api/group-members/{id}", groupMembers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupMembers.getId()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.toString()))
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
        restGroupMembersMockMvc.perform(get("/api/group-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGroupMembers() throws Exception {
        // Initialize the database
        groupMembersRepository.save(groupMembers);
        int databaseSizeBeforeUpdate = groupMembersRepository.findAll().size();

        // Update the groupMembers
        GroupMembers updatedGroupMembers = groupMembersRepository.findOne(groupMembers.getId());
        updatedGroupMembers
            .groupId(UPDATED_GROUP_ID)
            .groupType(UPDATED_GROUP_TYPE)
            .groupName(UPDATED_GROUP_NAME)
            .groupIcon(UPDATED_GROUP_ICON)
            .groupSlogan(UPDATED_GROUP_SLOGAN)
            .groupStatus(UPDATED_GROUP_STATUS)
            .ownerId(UPDATED_OWNER_ID)
            .ownerLogin(UPDATED_OWNER_LOGIN)
            .memberLists(UPDATED_MEMBER_LISTS)
            .maxMember(UPDATED_MAX_MEMBER)
            .memberCount(UPDATED_MEMBER_COUNT)
            .createdUnixTime(UPDATED_CREATED_UNIX_TIME)
            .lastModifiedUnixTime(UPDATED_LAST_MODIFIED_UNIX_TIME)
            .lastChatUnixTime(UPDATED_LAST_CHAT_UNIX_TIME)
            .reportDay(UPDATED_REPORT_DAY);

        restGroupMembersMockMvc.perform(put("/api/group-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupMembers)))
            .andExpect(status().isOk());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate);
        GroupMembers testGroupMembers = groupMembersList.get(groupMembersList.size() - 1);
        assertThat(testGroupMembers.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testGroupMembers.getGroupType()).isEqualTo(UPDATED_GROUP_TYPE);
        assertThat(testGroupMembers.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testGroupMembers.getGroupIcon()).isEqualTo(UPDATED_GROUP_ICON);
        assertThat(testGroupMembers.getGroupSlogan()).isEqualTo(UPDATED_GROUP_SLOGAN);
        assertThat(testGroupMembers.getGroupStatus()).isEqualTo(UPDATED_GROUP_STATUS);
        assertThat(testGroupMembers.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testGroupMembers.getOwnerLogin()).isEqualTo(UPDATED_OWNER_LOGIN);
        assertThat(testGroupMembers.getMemberLists()).isEqualTo(UPDATED_MEMBER_LISTS);
        assertThat(testGroupMembers.getMaxMember()).isEqualTo(UPDATED_MAX_MEMBER);
        assertThat(testGroupMembers.getMemberCount()).isEqualTo(UPDATED_MEMBER_COUNT);
        assertThat(testGroupMembers.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testGroupMembers.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testGroupMembers.getCreatedUnixTime()).isEqualTo(UPDATED_CREATED_UNIX_TIME);
        assertThat(testGroupMembers.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testGroupMembers.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testGroupMembers.getLastModifiedUnixTime()).isEqualTo(UPDATED_LAST_MODIFIED_UNIX_TIME);
        assertThat(testGroupMembers.getLastChatUnixTime()).isEqualTo(UPDATED_LAST_CHAT_UNIX_TIME);
        assertThat(testGroupMembers.getReportDay()).isEqualTo(UPDATED_REPORT_DAY);
    }

    @Test
    public void updateNonExistingGroupMembers() throws Exception {
        int databaseSizeBeforeUpdate = groupMembersRepository.findAll().size();

        // Create the GroupMembers

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGroupMembersMockMvc.perform(put("/api/group-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupMembers)))
            .andExpect(status().isCreated());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteGroupMembers() throws Exception {
        // Initialize the database
        groupMembersRepository.save(groupMembers);
        int databaseSizeBeforeDelete = groupMembersRepository.findAll().size();

        // Get the groupMembers
        restGroupMembersMockMvc.perform(delete("/api/group-members/{id}", groupMembers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupMembers.class);
        GroupMembers groupMembers1 = new GroupMembers();
        groupMembers1.setId("id1");
        GroupMembers groupMembers2 = new GroupMembers();
        groupMembers2.setId(groupMembers1.getId());
        assertThat(groupMembers1).isEqualTo(groupMembers2);
        groupMembers2.setId("id2");
        assertThat(groupMembers1).isNotEqualTo(groupMembers2);
        groupMembers1.setId(null);
        assertThat(groupMembers1).isNotEqualTo(groupMembers2);
    }
}
