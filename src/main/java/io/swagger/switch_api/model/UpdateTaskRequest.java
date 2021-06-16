package io.swagger.switch_api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.switch_api.model.RequestHeader;
import io.swagger.switch_api.model.Task;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UpdateTaskRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-06-09T10:49:04.429+02:00")

public class UpdateTaskRequest   {
  @JsonProperty("requestHeader")
  private RequestHeader requestHeader = null;

  @JsonProperty("task")
  private Task task = null;

  public UpdateTaskRequest requestHeader(RequestHeader requestHeader) {
    this.requestHeader = requestHeader;
    return this;
  }

  /**
   * Get requestHeader
   * @return requestHeader
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public RequestHeader getRequestHeader() {
    return requestHeader;
  }

  public void setRequestHeader(RequestHeader requestHeader) {
    this.requestHeader = requestHeader;
  }

  public UpdateTaskRequest task(Task task) {
    this.task = task;
    return this;
  }

  /**
   * Get task
   * @return task
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateTaskRequest updateTaskRequest = (UpdateTaskRequest) o;
    return Objects.equals(this.requestHeader, updateTaskRequest.requestHeader) &&
        Objects.equals(this.task, updateTaskRequest.task);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestHeader, task);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateTaskRequest {\n");
    
    sb.append("    requestHeader: ").append(toIndentedString(requestHeader)).append("\n");
    sb.append("    task: ").append(toIndentedString(task)).append("\n");
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

