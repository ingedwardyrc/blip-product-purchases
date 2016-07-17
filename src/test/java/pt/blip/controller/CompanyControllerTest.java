package pt.blip.controller;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.blip.domain.Detail;
import pt.blip.domain.Purchase;
import pt.blip.service.PurchaseService;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CompanyControllerTest {
  public static final String PRODUCT_TYPE_BMW = "BMW";
  public static final long PURCHASE_ID_1 = 1L;
  public static final long PURCHASE_ID_2 = 2L;

  @Mock
  private PurchaseService purchaseService;
  @InjectMocks
  private CompanyController companyController;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders
      .standaloneSetup(companyController)
      .build();
  }

  @Test
  public void testGetCompanyPurchases() throws Exception {
    Detail detail_purchase_1 = new Detail(1L, PURCHASE_ID_1, "Bought in shopping mall", 1, 22000.22);
    Detail detail_purchase_2 = new Detail(2L, PURCHASE_ID_2, "Bought in concessionary", 1, 20000.22);
    Detail detail_purchase_3 = new Detail(3L, PURCHASE_ID_2, "Bought in concessionary", 1, 30000.22);

    Purchase validPurchase_1 = new Purchase(PURCHASE_ID_1, PRODUCT_TYPE_BMW, DateTime.now().plusDays(1), asList(detail_purchase_1));
    Purchase validPurchase_2 = new Purchase(PURCHASE_ID_2, PRODUCT_TYPE_BMW, DateTime.now().plusDays(1), asList(detail_purchase_2, detail_purchase_3));

    when(purchaseService.getAllValidPurchasesWithDetails(11111L)).thenReturn(asList(validPurchase_1, validPurchase_2));

    //TODO: finish mvc tests
    mockMvc.perform(get("/company/11111/purchases"))
      .andExpect(jsonPath("$", hasSize(2)))
      .andDo(print())
      .andExpect(jsonPath("$[0].id", is(1)))
      .andExpect(jsonPath("$[0].purchaseDetails", hasSize(1)))
      .andExpect(jsonPath("$[0].purchaseDetails[0].id", is(1)))
      .andExpect(jsonPath("$[1].id", is(2)))
      .andExpect(jsonPath("$[1].purchaseDetails", hasSize(2)))
      .andExpect(jsonPath("$[1].purchaseDetails[0].id", is(2)))
      .andExpect(jsonPath("$[1].purchaseDetails[1].id", is(3)))
      .andExpect(status().isOk());
  }
}