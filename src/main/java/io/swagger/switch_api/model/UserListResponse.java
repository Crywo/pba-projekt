package io.swagger.switch_api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.switch_api.model.RequestHeader;
import io.swagger.switch_api.model.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserListResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-06-09T10:49:04.429+02:00")

public class UserListResponse   {
  @JsonProperty("responseHeader")
  private RequestHeader responseHeader = null;

  @JsonProperty("usersList")
  @Valid
  private List<User> usersList = null;

  public UserListResponse responseHeader(RequestHeader responseHeader) {
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

  public UserListResponse usersList(List<User> usersList) {
    this.usersList = usersList;
    return this;
  }

  public UserListResponse addUsersListItem(User usersListItem) {
    if (this.usersList == null) {
      this.usersList = new ArrayList<User>();
    }
    this.usersList.add(usersListItem);
    return this;
  }

  /**
   * Get usersList
   * @return usersList
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<User> getUsersList() {
    return usersList;
  }

  public void setUsersList(List<User> usersList) {
    this.usersList = usersList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserListResponse userListResponse = (UserListResponse) o;
    return Objects.equals(this.responseHeader, userListResponse.responseHeader) &&
        Objects.equals(this.usersList, userListResponse.usersList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responseHeader, usersList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserListResponse {\n");
    
    sb.append("    responseHeader: ").append(toIndentedString(responseHeader)).append("\n");
    sb.append("    usersList: ").append(toIndentedString(usersList)).append("\n");
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

