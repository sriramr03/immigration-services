package com.sriram.immigrationservice.api;

import com.sriram.immigrationservice.model.Immigration;
import com.sriram.immigrationservice.repository.ImmigrationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ImmigrationAPI {

    private final ImmigrationRepository immigrationRepository;

    public ImmigrationAPI(ImmigrationRepository immigrationRepository) {
        this.immigrationRepository = immigrationRepository;
    }

    @GetMapping(value = "/immigration")
    public Flux<Immigration> getAll(){
        return immigrationRepository.findAll();
    }

    @GetMapping(value = "/immigration/{id}")
    public Mono<Immigration> getById(@PathVariable String id){
        return immigrationRepository.findById(id);
    }

    @PostMapping(value = "/immigration")
    public ResponseEntity<Mono<Immigration>> create(@RequestBody Immigration immigration) {
        return ResponseEntity.ok(immigrationRepository.save(immigration));
    }

}
