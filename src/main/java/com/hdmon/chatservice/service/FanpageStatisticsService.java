package com.hdmon.chatservice.service;

import com.hdmon.chatservice.domain.FanpageStatisticsEntity;
import com.hdmon.chatservice.repository.FanpageStatisticsRepository;
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
public class FanpageStatisticsService {
    private final Logger log = LoggerFactory.getLogger(FanpageStatisticsService.class);

    private final FanpageStatisticsRepository fanpageStatisticsRepository;

    public FanpageStatisticsService(FanpageStatisticsRepository fanpageStatisticsRepository) {
        this.fanpageStatisticsRepository = fanpageStatisticsRepository;
    }

    /**
     * Save a fanpageStatistics.
     *
     * @return the persisted entity
     */
    public FanpageStatisticsEntity increaseStatistics() {
        Date dtDay = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dtDay);

        int inDay = cal.get(Calendar.DAY_OF_MONTH);
        int inMonth = cal.get(Calendar.MONTH);
        int inYear = cal.get(Calendar.YEAR);

        log.debug("Request to save FanpageStatistics : {}-{}-{}", inDay, inMonth, inYear);

        FanpageStatisticsEntity dbInfo = fanpageStatisticsRepository.findByInDayEqualsAndInMonthEqualsAndInYearEquals(inDay, inMonth, inYear);
        if(dbInfo != null && dbInfo.getId() != null)
        {
            dbInfo.setDayCount(dbInfo.getDayCount() + 1);
            dbInfo.setMonthCount(dbInfo.getMonthCount() + 1);
            dbInfo.setYearCount(dbInfo.getYearCount() + 1);

            return fanpageStatisticsRepository.save(dbInfo);
        }
        else
        {
            FanpageStatisticsEntity newInfo = new FanpageStatisticsEntity();
            newInfo.setDayCount(1);
            newInfo.setMonthCount(1);
            newInfo.setYearCount(1);
            newInfo.setInDay(inDay);
            newInfo.setInMonth(inMonth);
            newInfo.setInYear(inYear);

            return fanpageStatisticsRepository.save(newInfo);
        }
    }
}
