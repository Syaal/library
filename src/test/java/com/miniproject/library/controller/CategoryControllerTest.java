package com.miniproject.library.controller;

import com.miniproject.library.dto.category.CategoryRequest;
import com.miniproject.library.dto.category.CategoryResponse;
import com.miniproject.library.entity.Category;
import com.miniproject.library.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoryControllerTest {
    @InjectMocks
    CategoryController categoryController;
    @Mock
    CategoryService categoryService;

    private final ModelMapper mapper = new ModelMapper();


    private List<Category>categoryList(){
        List<Category>categoryList = new ArrayList<Category>();
        Category category = new Category();
        category.setName("Fiction");
        category.setId(2);
        Category category2 = new Category();
        category.setName("18++");
        category.setId(1);
        categoryList.add(category);
        categoryList.add(category2);
        return categoryList;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addCategory() {
        CategoryRequest request = new CategoryRequest();
        request.setName("18++");
        CategoryResponse response = mapper.map(request, CategoryResponse.class);
        response.setId(1);
        when(categoryService.addCategory(request)).thenReturn(response);
        ResponseEntity<CategoryResponse> responseEntity = categoryController.addCategory(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    void updateCategory() {
    }

    @Test
    void getAllCategory() {
        when(categoryService.getAllCategory()).thenReturn(categoryList());
        ResponseEntity <List<Category>> responseEntity = categoryController.getAllCategory();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categoryList(), responseEntity.getBody());
    }

    @Test
    void getCategoryById() {
    }
}