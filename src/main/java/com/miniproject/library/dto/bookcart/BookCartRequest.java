package com.miniproject.library.dto.bookcart;

import lombok.Data;

import java.util.List;

@Data
public class BookCartRequest {
    private List<Integer> bookIds;
    private Integer anggotaId;
}
