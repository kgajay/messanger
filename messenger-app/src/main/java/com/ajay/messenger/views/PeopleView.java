package com.ajay.messenger.views;

import com.ajay.messenger.models.Person;

import io.dropwizard.views.View;

public class PeopleView extends View {

	private final Person person;

    public PeopleView(Person person) {
        super("person.ftl");
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
