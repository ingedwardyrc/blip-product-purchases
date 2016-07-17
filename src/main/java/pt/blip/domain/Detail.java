package pt.blip.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Detail {
  private Long id;
  private Long purchaseId;
  private String description;
  private Integer quantity;
  private Double value;
}
