package pt.blip.dao;

import org.springframework.stereotype.Repository;
import pt.blip.domain.Purchase;

import java.util.List;

//TODO: Implement
@Repository
public class PurchaseDao {
  public List<Purchase> getAllPurchasesByCompany(Long companyId){
    return null;
  }

  public void updatePurchase(Purchase purchase) {
  }
}
