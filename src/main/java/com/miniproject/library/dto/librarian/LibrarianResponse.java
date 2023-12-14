package com.miniproject.library.dto.librarian;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LibrarianResponse {
    private Integer id;
    private Long nip;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String gender;
}
