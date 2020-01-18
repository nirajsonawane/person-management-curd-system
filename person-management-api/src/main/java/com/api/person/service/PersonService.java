package com.api.person.service;

import com.api.person.enity.Hobby;
import com.api.person.enity.Person;
import com.api.person.model.Colour;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private List<Person> personList = new ArrayList<>();


    @PostConstruct
    public void setup(){

        Person person = Person.builder()

                .age(32)
                .favouriteColour(Colour.RED)
                .firstName("Niraj")
                .lastName("Sonawane")
                .hobby(Arrays.asList((new Hobby("Cricket"))))
                .build();
        personList.add(person);
    }

    public List<Person> getAllPersons() {
        return personList;
    }

    public Integer save(Person person) {
        log.info("Saving Person to Database {}",person);
        return  2;
    }
    public Person getPersonById(Integer id){

        return personList.get(0);
    }

    public void delete(Integer id) {

    }

    public void update(Person person) {

    }
}
