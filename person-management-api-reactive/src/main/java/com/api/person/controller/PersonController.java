package com.api.person.controller;

import com.api.person.mapper.PersonMapper;
import com.api.person.model.CreatePersonRequest;
import com.api.person.model.GetPersonResponse;
import com.api.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/person")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;


    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GetPersonResponse> getAllPerson() {
        log.info("Getting All Person Data");
        return personService
                .getAllPersons();
    }

    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<GetPersonResponse>> getPerson(@PathVariable String id) {
        log.info("Getting Person Data For id {}", id);
        return personService.getPersonById(id);
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<GetPersonResponse> savePerson(@Validated @RequestBody CreatePersonRequest person) {
        log.info("Creating Person Resource {}", person);
        return personService.save(personMapper.toPerson(person));

    }
    @PutMapping(value = "/{id}")
    public Mono<ResponseEntity<GetPersonResponse>> updatePerson(@Validated @RequestBody CreatePersonRequest person,@PathVariable String id) {
        log.info("Updating Person Resource {} and Id {}", person,id);
        return personService.update(id,personMapper.toPerson(person));

    }

    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> deletePerson(@PathVariable String id) {
        log.info("Deleting Person Data For id {}", id);
        return personService.deletePersonById(id);
    }
}
