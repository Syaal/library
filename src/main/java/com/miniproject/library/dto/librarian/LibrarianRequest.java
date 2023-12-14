package com.miniproject.library.dto.librarian;

import lombok.Data;

@Data
public class LibrarianRequest {
    private Long nip;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String gender;
}
