package com.ajay.messenger.dto;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import com.ajay.messenger.models.Person;
import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;

public class PersonDTO extends AbstractDAO<Person> {

private static final Logger logger = Logger.getLogger(PersonDTO.class);
	
    public PersonDTO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Person> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public Person create(Person person) {
    	logger.info("create people");
        return persist(person);
    }

    public List<Person> findAll() {
        return list(namedQuery("com.ajay.messenger.models.Person.findAll"));
    }
}
