package pt.blip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.blip.dao.DetailDao;
import pt.blip.dao.PurchaseDao;
import pt.blip.domain.Detail;
import pt.blip.domain.Purchase;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class PurchaseService {
  @Autowired
  private PurchaseDao purchaseDao;
  @Autowired
  private DetailDao detailDao;

  public List<Purchase> getAllValidPurchasesWithDetails(Long companyId) {
    List<Purchase> validPurchases = getAllValidPurchases(companyId);
    List<Long> purchaseIds = validPurchases.stream()
      .map(Purchase::getId)
      .collect(toList());
    List<Detail> detailList = detailDao.getDetailsByPurchaseIds(purchaseIds);

    return joinPurchaseAndDetails(validPurchases, detailList);
  }

  List<Purchase> getAllValidPurchases(Long companyId) {
    return purchaseDao.getAllPurchasesByCompany(companyId)
      .stream().filter(p -> p.getExpires().isAfterNow())
      .collect(toList());
  }

  /**
   * This method joins the purchases and the purchases details (in real life this should be done with the database query)
   * this implementation has space complexity n, time complexity n, this can be implemented as time complexity nlogn as well,
   * ordering both arrays and doing a secuencial insert, without the extra space complexity in case of prioritize space over time
   * @param purchases purchases for the PurchaseResource
   * @param details details  for the PurchaseResource
   * @return the list of PurchaseResource with the details included
   */
  private List<Purchase> joinPurchaseAndDetails(List<Purchase> purchases, List<Detail> details) {
    Map<Long, List<Detail>> detailListMap = details.stream()
      .collect(groupingBy(Detail::getPurchaseId));
    purchases.stream().forEach(p -> p.setPurchaseDetails(detailListMap.get(p.getId())));
    return purchases;
  }

  public void updatePurchaseAndDetails(Purchase purchase) {
    purchaseDao.updatePurchase(purchase);
    detailDao.updateDetails(purchase.getPurchaseDetails());
  }
}
