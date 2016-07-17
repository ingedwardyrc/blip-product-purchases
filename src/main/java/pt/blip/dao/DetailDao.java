package pt.blip.dao;

import org.springframework.stereotype.Repository;
import pt.blip.domain.Detail;

import java.util.List;

//TODO: Implement
@Repository
public class DetailDao {
  public List<Detail> getDetailsByPurchaseIds(List<Long> purchaseIds){
    return null;
  }

  public void updateDetails(List<Detail> purchaseDetails) {
  }
}
