package pt.blip.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import pt.blip.domain.Purchase;

import java.util.List;

public class PurchaseResourceAssembler extends ResourceAssemblerSupport<Purchase, PurchaseResource> {
  private DetailResourceAssembler detailResourceAssembler = new DetailResourceAssembler();

  public PurchaseResourceAssembler(){
    super(Purchase.class, PurchaseResource.class);
  }

  @Override
  public PurchaseResource toResource(Purchase entity) {
    PurchaseResource resource = instantiateResource(entity);
    return resource;
  }

  @Override
  public PurchaseResource instantiateResource(Purchase purchase) {
    List<DetailResource> detailResources = detailResourceAssembler.toResources(purchase.getPurchaseDetails());
    return new PurchaseResource(purchase, detailResources);
  }
}
