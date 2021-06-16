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
 * TaskResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-06-09T10:49:04.429+02:00")

public class TaskResponse   {
  @JsonProperty("responseHeader")
  private RequestHeader responseHeader = null;

  @JsonProperty("task")
  private Task task = null;

  public TaskResponse responseHeader(RequestHeader responseHeader) {
    this.responseHeader = responseHeader;
    return this;
  }

  /**
   * Get responseHeader
   * @return responseHeader
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public RequestHeader getResponseHeader() {
    return responseHeader;
  }

  public void setResponseHeader(RequestHeader responseHeader) {
    this.responseHeader = responseHeader;
  }

  public TaskResponse task(Task task) {
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
    TaskResponse taskResponse = (TaskResponse) o;
    return Objects.equals(this.responseHeader, taskResponse.responseHeader) &&
        Objects.equals(this.task, taskResponse.task);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responseHeader, task);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskResponse {\n");
    
    sb.append("    responseHeader: ").append(toIndentedString(responseHeader)).append("\n");
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

