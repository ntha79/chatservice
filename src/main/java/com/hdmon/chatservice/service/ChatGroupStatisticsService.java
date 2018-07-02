package com.hdmon.chatservice.service;

import com.hdmon.chatservice.domain.ChatGroupStatisticsEntity;
import com.hdmon.chatservice.repository.ChatGroupStatisticsRepository;
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
public class ChatGroupStatisticsService {
    private final Logger log = LoggerFactory.getLogger(ChatGroupStatisticsService.class);

    private final ChatGroupStatisticsRepository chatGroupStatisticsRepository;

    public ChatGroupStatisticsService(ChatGroupStatisticsRepository chatGroupStatisticsRepository) {
        this.chatGroupStatisticsRepository = chatGroupStatisticsRepository;
    }

    /**
     * Save a chatGroupStatistics.
     *
     * @return the persisted entity
     */
    public ChatGroupStatisticsEntity increaseStatistics() {
        Date dtDay = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dtDay);

        int inDay = cal.get(Calendar.DAY_OF_MONTH);
        int inMonth = cal.get(Calendar.MONTH);
        int inYear = cal.get(Calendar.YEAR);

        log.debug("Request to save ChatGroupStatistics : {}-{}-{}", inDay, inMonth, inYear);

        ChatGroupStatisticsEntity dbInfo = chatGroupStatisticsRepository.findByInDayEqualsAndInMonthEqualsAndInYearEquals(inDay, inMonth, inYear);
        if(dbInfo != null && dbInfo.getSeqId() != null)
        {
            dbInfo.setDayCount(dbInfo.getDayCount() + 1);
            dbInfo.setMonthCount(dbInfo.getMonthCount() + 1);
            dbInfo.setYearCount(dbInfo.getYearCount() + 1);

            return chatGroupStatisticsRepository.save(dbInfo);
        }
        else
        {
            ChatGroupStatisticsEntity newInfo = new ChatGroupStatisticsEntity();
            newInfo.setDayCount(1);
            newInfo.setMonthCount(1);
            newInfo.setYearCount(1);
            newInfo.setInDay(inDay);
            newInfo.setInMonth(inMonth);
            newInfo.setInYear(inYear);

            return chatGroupStatisticsRepository.save(newInfo);
        }
    }
}
