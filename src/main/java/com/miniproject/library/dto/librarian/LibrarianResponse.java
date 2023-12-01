package com.miniproject.library.dto.librarian;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LibrarianResponse {
    private String name;
    private String address;
    private String email;
    private String gender;
}
