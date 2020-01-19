package com.api.person.repository;

import com.api.person.enity.Hobby;
import com.api.person.enity.Person;
import com.api.person.model.Colour;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(RandomBeansExtension.class)
class PersonRepositoryTest {

    @Autowired
    private  PersonRepository personRepository;
    @Autowired
    private HobbyRepository hobbyRepository;
    @Random
    Person person;

    @Test
    @DisplayName("Should Save Person With All Details")
    void shouldSavePerson(){
        Person save = personRepository.save(person);
        Optional<Person> personRepositoryById = personRepository.findById(save.getPersonId());
        assertTrue(personRepositoryById.isPresent());
        Person personById = personRepositoryById.get();
        assertNotNull(personById.getPersonId());
        assertEquals(personById.getFirstName(),person.getFirstName());
        assertEquals(personById.getLastName(),person.getLastName());
        assertEquals(personById.getAge(),person.getAge());
        assertEquals(personById.getFavouriteColour(),person.getFavouriteColour());
        assertEquals(personById.getHobby().size(),person.getHobby().size());
    }
    @ParameterizedTest(name = "Should Save Person With Correct Colour #{index} with Value [{arguments}]")
    @EnumSource(Colour.class)
    void shouldSavePerson_EnumMapping(Colour c){
        person.setFavouriteColour(c);
        Person save = personRepository.save(person);
        Optional<Person> personRepositoryById = personRepository.findById(save.getPersonId());
        assertTrue(personRepositoryById.isPresent());
        Person personById = personRepositoryById.get();
        assertEquals(personById.getFavouriteColour(),c);

    }
    @DisplayName("Test One To Many Mapping")
    @Test
    void shouldSavePerson_OneToMany(){
        Person save = personRepository.save(person);
        Optional<Person> personRepositoryById = personRepository.findById(save.getPersonId());
        List<Hobby> all = hobbyRepository.findAll();
        assertFalse(all.isEmpty());
        assertEquals(person.getHobby().size(),all.size());
    }
    @DisplayName("Test CascadeType.ALL Mapping")
    @Test
    void deletePerson_shouldAlsoDeleteHobby(){
        Person save = personRepository.save(person);
        Optional<Person> personRepositoryById = personRepository.findById(save.getPersonId());
        List<Hobby> all = hobbyRepository.findAll();
        assertFalse(all.isEmpty());
        assertEquals(person.getHobby().size(),all.size());
        personRepository.deleteById(save.getPersonId());
        assertTrue(hobbyRepository.findAll().isEmpty());
    }
    @Test
    @DisplayName("Should Update Person With All Details")
    void shouldUpdatePerson(){
        Person personFromDb = personRepository.save(person);
        personRepository.flush();

        Optional<Person> personRepositoryById = personRepository.findById(personFromDb.getPersonId());
        assertTrue(personRepositoryById.isPresent());
        Person personById = personRepositoryById.get();
        personById.setAge(99);
        personById.setLastName("Test");
        personById.setFirstName("Test");

        personRepository.save(personById);
        personRepository.flush();
        Person updatedPerson = personRepository.findById(personById.getPersonId()).get();


        assertNotNull(updatedPerson.getPersonId());
        assertEquals(updatedPerson.getFirstName(),"Test");
        assertEquals(updatedPerson.getLastName(),"Test");
        assertEquals(updatedPerson.getAge(),99);
    }
}