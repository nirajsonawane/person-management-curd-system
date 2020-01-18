package com.api.person.service;

import com.api.person.repository.PersonRepository;
import com.api.person.enity.Person;
import com.api.person.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Long save(Person person) {
        log.info("Saving Person to Database {}", person);
        Person personFromDatabase = personRepository.save(person);
        return personFromDatabase.getPersonId();
    }

    public Person getPersonById(Long id) {
        log.info("Finding Person for id {}",id);
        return personRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person Not Found for id " + id));
    }

    public void delete(Long id) {
        log.info("Deleting Person for id {}",id);
        personRepository.deleteById(id);
    }

    public void update(Person person) {
        log.info("Updating Person for id {}",person.getPersonId());
        personRepository.save(person);
    }
}
