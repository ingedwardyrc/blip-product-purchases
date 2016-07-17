package pt.blip.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
@AllArgsConstructor
public class Purchase {
  private Long id;
  private String productType;
  private DateTime expires;
  private List<Detail> purchaseDetails;
}
