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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
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
    @Test
    public void testGetLoanIdByAnggotaId_WhenLoanExists() {
        // Given
        Integer anggotaId = 1;
        Integer loanId = 10;

        LoanService loanService = mock(LoanService.class);
        when(loanService.getLoanIdByAnggotaId(anggotaId)).thenReturn(loanId);

        LoanController loanController = new LoanController(loanService);

        // When
        ResponseEntity<Integer> responseEntity = loanController.getLoanIdByAnggotaId(anggotaId);

        // Then
        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(loanId, responseEntity.getBody());
        verify(loanService).getLoanIdByAnggotaId(anggotaId);
    }

    @Test
    public void testGetLoanIdByAnggotaId_WhenLoanDoesNotExist() {
        // Given
        Integer anggotaId = 1;

        LoanService loanService = mock(LoanService.class);
        when(loanService.getLoanIdByAnggotaId(anggotaId)).thenReturn(null);

        LoanController loanController = new LoanController(loanService);

        // When & Then
        try {
            loanController.getLoanIdByAnggotaId(anggotaId);
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
        verify(loanService).getLoanIdByAnggotaId(anggotaId);
    }

}
