package pt.blip.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import pt.blip.domain.Detail;

@AllArgsConstructor
public class DetailResource extends ResourceSupport {
  private Detail detail;

  @JsonProperty("id")
  public Long getDetailId() {
    return detail.getId();
  }

  public String getDescription() {
    return detail.getDescription();
  }

  public Integer getQuantity() {
    return detail.getQuantity();
  }

  public Double getValue() {
    return detail.getValue();
  }

  @JsonIgnore
  public Detail getDetail() {
    return detail;
  }

}
