package pt.blip.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import org.springframework.hateoas.ResourceSupport;
import pt.blip.domain.Purchase;

import java.util.List;

public class PurchaseResource extends ResourceSupport {
  private Purchase purchase;
  private List<DetailResource> purchaseDetails;

  public PurchaseResource(Purchase purchase, List<DetailResource> purchaseDetails){
    this.purchase = purchase ;
    this.purchaseDetails = purchaseDetails;
  }

  @JsonProperty("id")
  public Long getPurchaseId() {
    return purchase.getId();
  }

  public String getProductType() {
    return purchase.getProductType();
  }

  @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
  public DateTime getExpires() {
    return purchase.getExpires();
  }

  public List<DetailResource> getPurchaseDetails() {
    return purchaseDetails;
  }
}
