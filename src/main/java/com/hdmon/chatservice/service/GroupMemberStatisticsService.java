package com.hdmon.chatservice.service;

import com.hdmon.chatservice.domain.FanpageStatisticsEntity;
import com.hdmon.chatservice.domain.GroupMemberStatisticsEntity;
import com.hdmon.chatservice.repository.FanpageStatisticsRepository;
import com.hdmon.chatservice.repository.GroupMemberStatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by UserName on 6/6/2018.
 */
@Service
@Transactional
public class GroupMemberStatisticsService {
    private final Logger log = LoggerFactory.getLogger(GroupMemberStatisticsService.class);

    private final GroupMemberStatisticsRepository groupMemberStatisticsRepository;

    public GroupMemberStatisticsService(GroupMemberStatisticsRepository groupMemberStatisticsRepository) {
        this.groupMemberStatisticsRepository = groupMemberStatisticsRepository;
    }

    /**
     * Save a fanpageStatistics.
     *
     * @return the persisted entity
     */
    public GroupMemberStatisticsEntity increaseStatistics() {
        Date dtDay = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dtDay);

        int inDay = cal.get(Calendar.DAY_OF_MONTH);
        int inMonth = cal.get(Calendar.MONTH);
        int inYear = cal.get(Calendar.YEAR);

        log.debug("Request to save GroupMemberStatistics : {}-{}-{}", inDay, inMonth, inYear);

        GroupMemberStatisticsEntity dbInfo = groupMemberStatisticsRepository.findByInDayEqualsAndInMonthEqualsAndInYearEquals(inDay, inMonth, inYear);
        if(dbInfo != null && dbInfo.getId() != null)
        {
            dbInfo.setDayCount(dbInfo.getDayCount() + 1);
            dbInfo.setMonthCount(dbInfo.getMonthCount() + 1);
            dbInfo.setYearCount(dbInfo.getYearCount() + 1);

            return groupMemberStatisticsRepository.save(dbInfo);
        }
        else
        {
            GroupMemberStatisticsEntity newInfo = new GroupMemberStatisticsEntity();
            newInfo.setDayCount(1);
            newInfo.setMonthCount(1);
            newInfo.setYearCount(1);
            newInfo.setInDay(inDay);
            newInfo.setInMonth(inMonth);
            newInfo.setInYear(inYear);

            return groupMemberStatisticsRepository.save(newInfo);
        }
    }
}
