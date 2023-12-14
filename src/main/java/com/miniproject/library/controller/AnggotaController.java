package com.miniproject.library.controller;

import com.miniproject.library.dto.anggota.AnggotaRequest;
import com.miniproject.library.dto.anggota.AnggotaResponse;
import com.miniproject.library.entity.Anggota;
import com.miniproject.library.service.AnggotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/anggota")
public class AnggotaController {
    private final AnggotaService anggotaService;

    @PutMapping("/{id}")
    public ResponseEntity<AnggotaResponse> updateAnggota(@PathVariable Integer id, @Valid
    @RequestBody AnggotaRequest request){
        AnggotaResponse anggotaResponse = anggotaService.updateAnggota(request, id);
        return ResponseEntity.ok(anggotaResponse);
    }

    @GetMapping
    public ResponseEntity<List<Anggota>> getAllAnggota(){
        List<Anggota> anggota = anggotaService.getAllAnggota();
        return ResponseEntity.ok(anggota);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anggota> getAnggotaById(@PathVariable Integer id){
        Anggota anggota = anggotaService.getAnggotaById(id);
        return ResponseEntity.ok(anggota);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnggotaById(@PathVariable Integer id){
        anggotaService.deleteAnggotaById(id);
        return ResponseEntity.noContent().build();
    }
}
