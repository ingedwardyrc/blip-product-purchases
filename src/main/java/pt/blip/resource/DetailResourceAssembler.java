package pt.blip.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import pt.blip.domain.Detail;
public class DetailResourceAssembler extends ResourceAssemblerSupport<Detail, DetailResource> {
  public DetailResourceAssembler(){
    super(Detail.class, DetailResource.class);
  }

  @Override
  public DetailResource toResource(Detail detail) {
    DetailResource resource = instantiateResource(detail);
    return resource;
  }

  @Override
  public DetailResource instantiateResource(Detail detail) {
    return new DetailResource(detail);
  }
}
