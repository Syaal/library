package com.miniproject.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.library.dto.bookcart.BookCartRequest;
import com.miniproject.library.dto.loan.LoanResponse;
import com.miniproject.library.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class LoanControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @Test
    void testBorrowBooksEndpoint() throws Exception {
        BookCartRequest request = new BookCartRequest();

        LoanResponse loanResponse = LoanResponse.builder()
                .id(1)
                .dateBorrow(new Date())
                .dueBorrow(new Date())
                .dateReturn(new Date())
                .bookCartId(1)
                .build();

        when(loanService.borrowBooks(request)).thenReturn(loanResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/loan/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testReturnBooksEndpoint() throws Exception {
        Integer loanId = 1;
        boolean isDamagedOrLost = true;

        LoanResponse loanResponse = LoanResponse.builder()
                .id(1)
                .dateBorrow(new Date())
                .dueBorrow(new Date())
                .dateReturn(new Date())
                .bookCartId(1)
                .build();
        // Set up expected response for returning books

        when(loanService.returnBooks(loanId, isDamagedOrLost)).thenReturn(loanResponse);

        mockMvc.perform(post("/loan/return")
                        .param("loanId", loanId.toString())
                        .param("isDamagedOrLost", String.valueOf(isDamagedOrLost)))
                .andExpect(status().isOk());
    }

}
