package io.swagger.switch_api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ResponseHeader
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-06-09T10:49:04.429+02:00")

public class ResponseHeader   {
  @JsonProperty("requestId")
  private UUID requestId = null;

  @JsonProperty("sendDate")
  private OffsetDateTime sendDate = null;

  public ResponseHeader requestId(UUID requestId) {
    this.requestId = requestId;
    return this;
  }

  /**
   * Get requestId
   * @return requestId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public UUID getRequestId() {
    return requestId;
  }

  public void setRequestId(UUID requestId) {
    this.requestId = requestId;
  }

  public ResponseHeader sendDate(OffsetDateTime sendDate) {
    this.sendDate = sendDate;
    return this;
  }

  /**
   * Date format according to ISO_8601 for example: yyyy-MM-dd'T'HH:mm:ss.SSSZ
   * @return sendDate
  **/
  @ApiModelProperty(example = "2021-02-22T02:06:58.147Z", required = true, value = "Date format according to ISO_8601 for example: yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  @NotNull

  @Valid

  public OffsetDateTime getSendDate() {
    return sendDate;
  }

  public void setSendDate(OffsetDateTime sendDate) {
    this.sendDate = sendDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseHeader responseHeader = (ResponseHeader) o;
    return Objects.equals(this.requestId, responseHeader.requestId) &&
        Objects.equals(this.sendDate, responseHeader.sendDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, sendDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResponseHeader {\n");
    
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    sendDate: ").append(toIndentedString(sendDate)).append("\n");
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

