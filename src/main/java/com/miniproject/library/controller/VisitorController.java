package com.miniproject.library.controller;

import com.miniproject.library.dto.visitor.VisitorRequest;
import com.miniproject.library.dto.visitor.VisitorResponse;
import com.miniproject.library.entity.Visitor;
import com.miniproject.library.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visitor")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @PostMapping()
    public ResponseEntity<VisitorResponse> createVisitor(@RequestBody VisitorRequest request){
        VisitorResponse response = visitorService.createVisitor(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Visitor> getVisitorById(@RequestParam("id") Integer id){
        Visitor response = visitorService.getByIdVisitor(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Visitor>> getAllVisitor(){
        List<Visitor> response = visitorService.getAllVisitor();
        return ResponseEntity.ok(response);
    }
}
