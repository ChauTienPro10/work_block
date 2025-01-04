package com.work_block.main.repository;

import com.work_block.main.entity.BlackListJwt;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListJwtRepository extends JpaRepository<BlackListJwt, Long> {
    BlackListJwt findByValue(String tokenName);

    @Query("SELECT b.value FROM BlackListJwt b")
    List<String> findAllValueJwt();

    List<BlackListJwt> findAll();

}
