package com.base.biz.epidemic.server.service.impl;


import com.base.biz.epidemic.server.manager.EpidemicManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/8/3 11:27 PM
 */
@Component
public class ScheduleServiceImpl {

    private final static Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Autowired
    private EpidemicManager epidemicManager;

    @Scheduled(cron = "0 30 11 * * ?")
    public void commit() {
        try {
            epidemicManager.commitAll();
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
}