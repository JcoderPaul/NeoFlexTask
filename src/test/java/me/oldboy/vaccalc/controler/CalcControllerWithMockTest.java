package me.oldboy.vaccalc.controler;

import me.oldboy.vaccalc.service.CalcAmountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Import(CalcAmountService.class)
@WebMvcTest(CalcController.class)
class CalcControllerWithMockTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnVacationIncomeInResponse_TwoParamRequest_returnStatusOkTest() throws Exception {
        mockMvc.perform(get("/api/calculacte")
                .contentType("application/json")
                        .queryParam("avgAmount", "50000")
                        .queryParam("vacationDays", "14"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .matches("'vacAmount':20784.93");
    }

    @Test
    void shouldReturnVacationIncomeInResponse_ThreeParamRequest_returnStatusOkTest() throws Exception {
        mockMvc.perform(get("/api/calculacte")
                        .contentType("application/json")
                        .queryParam("avgAmount", "50000")
                        .queryParam("vacationDays", "14")
                        .queryParam("firstDate", "2024-07-13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vacAmount").value(20784.93F));
    }

    @Test
    void shouldReturn400_ZeroParamRequest_returnStatusBadRequestTest() throws Exception {
        mockMvc.perform(get("/api/calculacte")
                        .contentType("application/json"))
                .andExpect(status().is4xxClientError());
    }
}