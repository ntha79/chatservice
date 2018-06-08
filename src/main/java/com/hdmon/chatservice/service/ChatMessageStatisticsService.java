package com.hdmon.chatservice.service;

import com.hdmon.chatservice.domain.ChatMessageStatisticsEntity;
import com.hdmon.chatservice.repository.ChatMessageStatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by UserName on 6/5/2018.
 */
@Service
@Transactional
public class ChatMessageStatisticsService {
    private final Logger log = LoggerFactory.getLogger(ChatMessageStatisticsService.class);

    private final ChatMessageStatisticsRepository chatMessageStatisticsRepository;

    public ChatMessageStatisticsService(ChatMessageStatisticsRepository chatMessageStatisticsRepository) {
        this.chatMessageStatisticsRepository = chatMessageStatisticsRepository;
    }

    /**
     * Save a chatMessageStatistics.
     *
     * @return the persisted entity
     */
    public ChatMessageStatisticsEntity increaseStatistics() {
        Date dtDay = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dtDay);

        int inDay = cal.get(Calendar.DAY_OF_MONTH);
        int inMonth = cal.get(Calendar.MONTH);
        int inYear = cal.get(Calendar.YEAR);

        log.debug("Request to save ChatMessageStatistics : {}-{}-{}", inDay, inMonth, inYear);

        ChatMessageStatisticsEntity dbInfo = chatMessageStatisticsRepository.findByInDayEqualsAndInMonthEqualsAndInYearEquals(inDay, inMonth, inYear);
        if(dbInfo != null && dbInfo.getId() != null)
        {
            dbInfo.setDayCount(dbInfo.getDayCount() + 1);
            dbInfo.setMonthCount(dbInfo.getMonthCount() + 1);
            dbInfo.setYearCount(dbInfo.getYearCount() + 1);

            return chatMessageStatisticsRepository.save(dbInfo);
        }
        else
        {
            ChatMessageStatisticsEntity newInfo = new ChatMessageStatisticsEntity();
            newInfo.setDayCount(1);
            newInfo.setMonthCount(1);
            newInfo.setYearCount(1);
            newInfo.setInDay(inDay);
            newInfo.setInMonth(inMonth);
            newInfo.setInYear(inYear);

            return chatMessageStatisticsRepository.save(newInfo);
        }
    }
}
