package com.niuniu.fitness_shop.service;

import com.niuniu.fitness_shop.entity.Log;
import com.niuniu.fitness_shop.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public void addLog(String operator, String operation, String detail, String ip) {
        Log log = new Log();
        log.setOperator(operator);
        log.setOperation(operation);
        log.setDetail(detail);
        log.setIp(ip);
        logRepository.save(log);
    }

    public Page<Log> getLogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return logRepository.findByOrderByCreatedAtDesc(pageable);
    }
}
