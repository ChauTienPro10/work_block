package com.work_block.main.repository;

import com.work_block.main.entity.GithubAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubAuthRepository extends JpaRepository<GithubAuth, Long> {

}
