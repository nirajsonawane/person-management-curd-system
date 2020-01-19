package com.api.person.controller;

import com.api.person.enity.Person;
import com.api.person.mapper.PersonMapper;
import com.api.person.model.CreatePersonRequest;
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
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;


    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Person> getAllPerson() {
        log.info("Getting All Person Data");
        return personService
                .getAllPersons();
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<Person>> getPerson(@PathVariable String id) {
        log.info("Getting Person Data For id {}", id);
        return personService.getPersonById(id);
    }

    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<Person> savePerson(@Validated @RequestBody CreatePersonRequest person) {
        log.info("Creating Person Resource {}", person);
        Mono<Person> save = personService.save(personMapper.toPerson(person));
        return save;

    }
    /*@PutMapping
    public ResponseEntity updatePerson(@Validated @RequestBody UpdatePersonRequest person) {
        log.info("Creating Person Resource {}", person);
        personService.update(personMapper.toPerson(person));
        return ResponseEntity
                .noContent()
                .build();
    }
*/
    @DeleteMapping("/{id}")

    public Mono<ResponseEntity<Void>> deletePerson(@PathVariable String id) {
        log.info("Deleting Person Data For id {}", id);
        return personService.deletePersonById(id);
    }
}
