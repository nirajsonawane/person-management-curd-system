package com.api.person.service;

import com.api.person.enity.Person;
import com.api.person.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;


    public Flux<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Mono<Person> save(Person person) {
        log.info("Saving Person to Database {}", person);
        return personRepository.insert(person);
    }

    public Mono<ResponseEntity<Person>> getPersonById(String id) {
        log.info("Finding Person for id {}", id);
        return personRepository.findById(id)
                               .map(person -> ResponseEntity.ok(person))
                               .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    public Mono<ResponseEntity<Void>> deletePersonById(String id) {
        log.info("Deleting Person for id {}", id);
        return personRepository.findById(id)
                               .flatMap(it -> personRepository.deleteById(it.getPersonId()).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
                               .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<Person>> update(Person person) {
        log.info("Updating Person for id {}", person.getPersonId());
        return personRepository.findById(person.getPersonId())
                               .flatMap(personRepository::save)
                               .map(updatedPerson -> new ResponseEntity<>(updatedPerson, HttpStatus.NO_CONTENT))
                               .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
