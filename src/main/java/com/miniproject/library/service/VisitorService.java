package com.miniproject.library.service;

import com.miniproject.library.dto.visitor.VisitorRequest;
import com.miniproject.library.dto.visitor.VisitorResponse;
import com.miniproject.library.entity.Visitor;
import com.miniproject.library.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;

    private final ModelMapper mapper = new ModelMapper();

    //add visitor
    public VisitorResponse createVisitor(VisitorRequest visitorRequest){
        Visitor visitor = mapper.map(visitorRequest, Visitor.class);
        visitorRepository.save(visitor);

        return mapper.map(visitor, VisitorResponse.class);
    }

    //get by id
    public Visitor getByIdVisitor(Integer id){
        Optional<Visitor> optionalVisitor = visitorRepository.findById(id);
        if (optionalVisitor.isPresent()){
            Visitor visitor = optionalVisitor.get();
            return mapper.map(visitor, Visitor.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Tidak Ditemukan");
        }
    }

    //get all visitor
    public List<Visitor> getAllVisitor(){
        List<Visitor> visitorList = visitorRepository.findAll();
        return visitorList.stream().map(visitor -> mapper.map(visitor, Visitor.class))
                .toList();
    }
}
