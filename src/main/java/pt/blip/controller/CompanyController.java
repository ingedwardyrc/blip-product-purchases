package pt.blip.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pt.blip.domain.Purchase;
import pt.blip.resource.PurchaseResource;
import pt.blip.resource.PurchaseResourceAssembler;
import pt.blip.service.PurchaseService;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {
  private PurchaseResourceAssembler purchaseResourceAssembler = new PurchaseResourceAssembler();

  @Autowired
  private PurchaseService purchaseService;

  @ApiOperation(value = "Get valid purchases for company")
  @RequestMapping(value = "/{company-id}/purchases", method = GET)
  @ResponseBody
  public List<PurchaseResource> getCompanyPurchases(
    @ApiParam(required = true, defaultValue = "11111", name = "company-id") @PathVariable("company-id") Long companyId){
    List<Purchase> purchases = purchaseService.getAllValidPurchasesWithDetails(companyId);
    return purchaseResourceAssembler.toResources(purchases);
  }
}
