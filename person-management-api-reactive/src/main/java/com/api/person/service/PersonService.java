package com.api.person.service;

import com.api.person.enity.Person;
import com.api.person.mapper.PersonMapper;
import com.api.person.model.GetPersonResponse;
import com.api.person.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public Flux<GetPersonResponse> getAllPersons() {
        return personRepository
                .findAll()
                .map(personMapper::fromPerson);
    }

    public Mono<GetPersonResponse> save(Person person) {
        log.info("Saving Person to Database {}", person);
        return personRepository
                .insert(person)
                .map(personMapper::fromPerson);
    }

    public Mono<ResponseEntity<GetPersonResponse>> getPersonById(String id) {
        log.info("Finding Person for id {}", id);
        return personRepository
                .findById(id)
                .map(personMapper::fromPerson)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity
                        .notFound()
                        .build());

    }

    public Mono<ResponseEntity<Void>> deletePersonById(String id) {
        log.info("Deleting Person for id {}", id);
        return personRepository
                .findById(id)
                .flatMap(it -> personRepository
                        .deleteById(it.getPersonId())
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<GetPersonResponse>> update(String personId, Person person) {
        log.info("Updating Person for id {}", personId);
        return personRepository
                .findById(personId)
                .flatMap(person1 -> {
                    person1.setLastName(person.getLastName());
                    person1.setFirstName(person.getFirstName());
                    person1.setAge(person.getAge());
                    person1.setFavouriteColour(person.getFavouriteColour());
                    //person1.setFirstName(person.getFirstName());
                    return personRepository.save(person1);
                })
                .map(updatedPerson -> new ResponseEntity<>(personMapper.fromPerson(person), HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }
}
