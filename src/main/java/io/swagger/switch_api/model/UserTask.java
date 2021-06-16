package io.swagger.switch_api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserTask
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-06-09T10:49:04.429+02:00")

@Entity
@Table(name = "UserTask")
public class UserTask   {
  @JsonProperty("UserTask_id")
  private @Id @GeneratedValue UUID userTaskId = null;
  
  @JsonProperty("User_id")
  private UUID userId = null;

  @JsonProperty("Task_id")
  private UUID taskId = null;
  
  public UserTask() {
	  
  }
  
  public UserTask(UUID userId, UUID taskId) {
	  this.userId = userId;
	  this.taskId = taskId;
  }
  
  public UserTask userTaskId(UUID userTaskId) {
    this.userTaskId = userTaskId;
    return this;
  }

  /**
   * Get userTaskId
   * @return userTaskId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public UUID getUserTaskId() {
    return userTaskId;
  }

  public void setUserTaskId(UUID userTaskId) {
    this.userTaskId = userTaskId;
  }

  public UserTask userId(UUID userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public UserTask taskId(UUID taskId) {
    this.taskId = taskId;
    return this;
  }

  /**
   * Get taskId
   * @return taskId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public UUID getTaskId() {
    return taskId;
  }

  public void setTaskId(UUID taskId) {
    this.taskId = taskId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserTask userTask = (UserTask) o;
    return Objects.equals(this.userTaskId, userTask.userTaskId) &&
		Objects.equals(this.userId, userTask.userId) &&
        Objects.equals(this.taskId, userTask.taskId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userTaskId, userId, taskId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserTask {\n");

    sb.append("    userTaskId: ").append(toIndentedString(userTaskId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    taskId: ").append(toIndentedString(taskId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

