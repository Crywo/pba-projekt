package io.swagger.switch_api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.switch_api.model.RequestHeader;
import io.swagger.switch_api.model.UserTask;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserTaskResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-06-09T10:49:04.429+02:00")

public class UserTaskResponse   {
  @JsonProperty("responseHeader")
  private RequestHeader responseHeader = null;

  @JsonProperty("userTask")
  private UserTask userTask = null;

  public UserTaskResponse responseHeader(RequestHeader responseHeader) {
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

  public UserTaskResponse userTask(UserTask userTask) {
    this.userTask = userTask;
    return this;
  }

  /**
   * Get userTask
   * @return userTask
  **/
  @ApiModelProperty(value = "")

  @Valid

  public UserTask getUser() {
    return userTask;
  }

  public void setUser(UserTask userTask) {
    this.userTask = userTask;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserTaskResponse userTaskResponse = (UserTaskResponse) o;
    return Objects.equals(this.responseHeader, userTaskResponse.responseHeader) &&
        Objects.equals(this.userTask, userTaskResponse.userTask);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responseHeader, userTask);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserTaskResponse {\n");
    
    sb.append("    responseHeader: ").append(toIndentedString(responseHeader)).append("\n");
    sb.append("    userTask: ").append(toIndentedString(userTask)).append("\n");
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

