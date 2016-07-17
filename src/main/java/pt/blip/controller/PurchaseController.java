package pt.blip.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pt.blip.domain.Detail;
import pt.blip.domain.Purchase;
import pt.blip.resource.DetailResource;
import pt.blip.resource.PurchaseResource;
import pt.blip.service.PurchaseService;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(value = "/purchase")
public class PurchaseController {
  public static final String DEFAULT_PURCHASE_PUT = "{\"expires\":1234556, \"links\":[],\"purchaseDetails\":[{\"quantity\":1,\"description\":\"Bought in shopping mall\",\"links\":[],\"id\":1,\"value\":22000.22}]}";

  @Autowired
  private PurchaseService purchaseService;

  @ApiOperation(value = "Update purchase")
  @RequestMapping(value = "/{purchase-id}", method = PUT, consumes = APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Void> getCompanyPurchases(
    @ApiParam(required = true, defaultValue = "11111", name = "purchase-id") @PathVariable("purchase-id") Long purchaseId,
    @ApiParam(required = true, defaultValue = DEFAULT_PURCHASE_PUT) @RequestBody PurchaseResource purchaseResource
  ){
    Purchase purchase = purchaseResource.getPurchase();
    List<DetailResource> DetailResource = purchaseResource.getPurchaseDetails();
    List<Detail> details = DetailResource.stream()
      .map(p -> p.getDetail())
      .collect(toList());

    purchase.setPurchaseDetails(details);

    purchaseService.updatePurchaseAndDetails(purchase);
    return new ResponseEntity<>(ACCEPTED);
  }
}
