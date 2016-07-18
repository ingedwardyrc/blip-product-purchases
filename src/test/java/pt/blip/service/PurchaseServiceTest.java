package pt.blip.service;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import pt.blip.dao.DetailDao;
import pt.blip.dao.PurchaseDao;
import pt.blip.domain.Detail;
import pt.blip.domain.Purchase;
import pt.blip.exception.NotFoundException;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest {
  public static final String PRODUCT_TYPE_BMW = "BMW";
  public static final long PURCHASE_ID_NOT_VALID = 3L;
  public static final long PURCHASE_ID_1 = 1L;
  public static final long PURCHASE_ID_2 = 2L;
  public static final long COMPANY_ID = 1234L;

  @Spy
  private PurchaseDao purchaseDao;
  @Spy
  private DetailDao detailDao;

  @InjectMocks
  private PurchaseService purchaseService;

  @Test
  public void shouldGetOnlyValidPurchases(){
    Purchase notValidpurchase = new Purchase(1L, PRODUCT_TYPE_BMW, DateTime.now().minusDays(1), null);
    Purchase validPurchase = new Purchase(2L, PRODUCT_TYPE_BMW, DateTime.now().plusDays(1), null);
    when(purchaseDao.getAllPurchasesByCompany(COMPANY_ID)).thenReturn(asList(validPurchase, notValidpurchase));

    List<Purchase> purchases = purchaseService.getAllValidPurchases(COMPANY_ID);
    assertEquals(purchases.size(), 1);
    assertEquals(purchases.get(0), validPurchase);
  }

  @Test(expected=NotFoundException.class)
  public void shouldGetNotFoundExceptionWhenThereIsNoPurchases(){
    when(purchaseDao.getAllPurchasesByCompany(COMPANY_ID)).thenReturn(null);
    purchaseService.getAllValidPurchases(COMPANY_ID);
  }

  @Test(expected=NotFoundException.class)
  public void shouldGetNotFoundExceptionWhenThereIsNoDetails(){
    Purchase notValidpurchase = new Purchase(PURCHASE_ID_NOT_VALID, PRODUCT_TYPE_BMW, DateTime.now().minusDays(1), null);
    Purchase validPurchase_1 = new Purchase(PURCHASE_ID_1, PRODUCT_TYPE_BMW, DateTime.now().plusDays(1), null);
    Purchase validPurchase_2 = new Purchase(PURCHASE_ID_2, PRODUCT_TYPE_BMW, DateTime.now().plusDays(1), null);
    when(purchaseDao.getAllPurchasesByCompany(COMPANY_ID)).thenReturn(asList(validPurchase_1, validPurchase_2, notValidpurchase));

    when(detailDao.getDetailsByPurchaseIds(asList(PURCHASE_ID_1, PURCHASE_ID_2))).thenReturn(null);

    purchaseService.getAllValidPurchasesWithDetails(COMPANY_ID);
  }

  @Test(expected=NotFoundException.class)
  public void shouldGetNotFoundExceptionWhenThereIsNoValidPurchases(){
    Purchase notValidpurchase_1 = new Purchase(PURCHASE_ID_1, PRODUCT_TYPE_BMW, DateTime.now().minusDays(1), null);
    Purchase notValidpurchase_2 = new Purchase(PURCHASE_ID_2, PRODUCT_TYPE_BMW, DateTime.now().minusDays(1), null);
    when(purchaseDao.getAllPurchasesByCompany(COMPANY_ID)).thenReturn(asList(notValidpurchase_1, notValidpurchase_2));

    purchaseService.getAllValidPurchasesWithDetails(COMPANY_ID);
  }

  @Test
  public void shouldGetOnlyValidPurchasesResourceWithDetails(){
    Purchase notValidpurchase = new Purchase(PURCHASE_ID_NOT_VALID, PRODUCT_TYPE_BMW, DateTime.now().minusDays(1), null);
    Purchase validPurchase_1 = new Purchase(PURCHASE_ID_1, PRODUCT_TYPE_BMW, DateTime.now().plusDays(1), null);
    Purchase validPurchase_2 = new Purchase(PURCHASE_ID_2, PRODUCT_TYPE_BMW, DateTime.now().plusDays(1), null);
    when(purchaseDao.getAllPurchasesByCompany(COMPANY_ID)).thenReturn(asList(validPurchase_1, validPurchase_2, notValidpurchase));

    Detail detail_purchase_1 = new Detail(1L, PURCHASE_ID_1,  "Bought in shopping mall", 1, 22000.22);
    Detail detail_purchase_2 = new Detail(2L, PURCHASE_ID_2, "Bought in concessionary", 1, 20000.22);
    Detail detail_purchase_3 = new Detail(3L, PURCHASE_ID_2, "Bought in concessionary", 1, 30000.22);
    when(detailDao.getDetailsByPurchaseIds(asList(PURCHASE_ID_1, PURCHASE_ID_2))).thenReturn(asList(detail_purchase_1, detail_purchase_2, detail_purchase_3 ));

    List<Purchase> purchaseResources = purchaseService.getAllValidPurchasesWithDetails(COMPANY_ID);
    assertEquals(purchaseResources.get(0).getPurchaseDetails().size(), 1);
    assertTrue(reflectionEquals(purchaseResources.get(0).getPurchaseDetails().get(0), detail_purchase_1));

    assertEquals(purchaseResources.get(1).getPurchaseDetails().size(), 2);
    assertTrue(reflectionEquals(purchaseResources.get(1).getPurchaseDetails().get(0), detail_purchase_2));
    assertTrue(reflectionEquals(purchaseResources.get(1).getPurchaseDetails().get(1), detail_purchase_3));
  }

  @Test
  public void shouldUpdatePurchaseAndDetails(){
    Purchase purchase = new Purchase(PURCHASE_ID_1, PRODUCT_TYPE_BMW, DateTime.now().plusDays(1), null);
    Detail detail_purchase_1 = new Detail(1L, PURCHASE_ID_1, "Bought in shopping mall", 1, 22000.22);
    Detail detail_purchase_2 = new Detail(2L, PURCHASE_ID_1, "Bought in concessionary", 1, 20000.22);
    Detail detail_purchase_3 = new Detail(3L, PURCHASE_ID_1, "Bought in concessionary", 1, 30000.22);
    List<Detail> details = asList(detail_purchase_1, detail_purchase_2, detail_purchase_3);
    purchase.setPurchaseDetails(details);

    purchaseService.updatePurchaseAndDetails(purchase);
    verify(purchaseDao).updatePurchase(purchase);
    verify(detailDao).updateDetails(details);
  }

}