package com.alfresco.activiti.analytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alfresco.activiti.analytics.entiity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	

}
