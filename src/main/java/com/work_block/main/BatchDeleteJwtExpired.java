package com.work_block.main;

import com.work_block.main.configuration.JwtTokenUtil;
import com.work_block.main.entity.BlackListJwt;
import com.work_block.main.repository.BlackListJwtRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BatchDeleteJwtExpired implements Runnable {
    @Autowired
    private BlackListJwtRepository blackListJwtRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<BlackListJwt> tokens = blackListJwtRepository.findAll();
        if(tokens.isEmpty()){
            return;
        }
        tokens.forEach(token -> {
            Date expTk = jwtTokenUtil.getClaimsFromExpiredToken(token.getValue()).getExpiration();
            Date currentDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
            if(expTk.before(currentDate)){
                blackListJwtRepository.delete(token);
                log.info("Deleted: " + token.getValue());
            }
        });
    }

    /**
     * quản lý tần suất chạy batch
     */
    @Scheduled(fixedRate = 5000)
    public void scheduleTask() {
        log.info("*** Start batch delete expired tokens***");
        run();
        log.info("*** Finish batch delete expired tokens ***");
    }
}
