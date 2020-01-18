package com.api.person.mapper;

import com.api.person.enity.Hobby;
import com.api.person.enity.Person;
import com.api.person.model.CreatePersonRequest;
import com.api.person.model.GetPersonResponse;
import com.api.person.model.UpdatePersonRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PersonMapper {

    @Mapping(target = "personId", ignore = true)
    @Mapping(target = "hobby",source = "hobby", qualifiedByName = "convertToHobby")
    Person toPerson(CreatePersonRequest personRequest);

    @Mapping(target = "personId", source = "personId")
    @Mapping(target = "hobby",source = "hobby", qualifiedByName = "convertToHobby")
    Person toPerson(UpdatePersonRequest personRequest);

    @Mapping(target = "hobby",source = "hobby", qualifiedByName = "convertFromHobby")
    GetPersonResponse fromPerson(Person person);

    default List<Hobby> convertToHobby(List<String> hobbyList){
        if(hobbyList==null)
            return new ArrayList<>();

        return hobbyList.stream().map(it-> Hobby.builder().hobbyStringValue(it).build()).collect(Collectors.toList());
    }

    default  List<String> convertFromHobby(List<Hobby> list){
        if(null==list){
            return new ArrayList<>();
        }
        return list.stream().map(Hobby::getHobbyStringValue).collect(Collectors.toList());
    }
}
