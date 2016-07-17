package pt.blip.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.blip.service.PurchaseService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pt.blip.controller.PurchaseController.DEFAULT_PURCHASE_PUT;

public class PurchaseControllerTest {
  @Mock
  private PurchaseService purchaseService;
  @InjectMocks
  private PurchaseController purchaseController;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders
      .standaloneSetup(purchaseController)
      .build();
  }

  @Test
  @Ignore
  //TODO: fix controller so the test can succeed
  public void testGetCompanyPurchases() throws Exception {
    mockMvc.perform(put("/purchase/1")
      .content(DEFAULT_PURCHASE_PUT)
      .contentType(APPLICATION_JSON))
      .andExpect(status().isAccepted());
  }
}