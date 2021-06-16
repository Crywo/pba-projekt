package io.swagger.switch_api.model;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTaskRepository extends JpaRepository<UserTask, UUID>  {

}
