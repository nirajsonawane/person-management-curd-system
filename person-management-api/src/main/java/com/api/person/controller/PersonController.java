package com.api.person.controller;

import com.api.person.mapper.PersonMapper;
import com.api.person.model.CreatePersonRequest;
import com.api.person.model.GetPersonResponse;
import com.api.person.model.UpdatePersonRequest;
import com.api.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
@Slf4j
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;


    @GetMapping
    public List<GetPersonResponse> getAllPerson() {
        log.info("Getting All Person Data");
        return personService
                .getAllPersons()
                .stream()
                .map(personMapper::fromPerson)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GetPersonResponse getPerson(@PathVariable Integer id) {
        log.info("Getting Person Data For id {}", id);
        return personMapper.fromPerson(personService.getPersonById(id));
    }

    @PostMapping
    public ResponseEntity savePerson(@Validated @RequestBody CreatePersonRequest person) {
        log.info("Creating Person Resource {}", person);
        personService.save(personMapper.toPerson(person));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
    @PutMapping
    public ResponseEntity updatePerson(@Validated @RequestBody UpdatePersonRequest person) {
        log.info("Creating Person Resource {}", person);
        personService.update(personMapper.toPerson(person));
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable Integer id) {
        log.info("Deleting Person Data For id {}", id);
        personService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
