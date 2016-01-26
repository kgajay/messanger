package com.ajay.messenger.core;

import org.junit.Test;
import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ajay.messenger.models.Person;
import com.ajay.messenger.utils.ObjectMapperUtils;

public class PersonTest {

	@Test
    public void serializesToJSON() throws Exception {
        final Person person = new Person("Luther Blissett", "Senior Software Engineeer");

        System.out.println("person object " + ObjectMapperUtils.getObjectMapper().writeValueAsString(person));
        final String expected = ObjectMapperUtils.getObjectMapper().writeValueAsString(
        		ObjectMapperUtils.getObjectMapper().readValue(fixture("fixtures/person.json"), Person.class));

        System.out.println("person expected " + ObjectMapperUtils.getObjectMapper().writeValueAsString(
        		ObjectMapperUtils.getObjectMapper().readValue(fixture("fixtures/person.json"), Person.class)));
        
        assertThat(ObjectMapperUtils.getObjectMapper().writeValueAsString(person)).isEqualTo(expected);
    }
	
	
	@Test
    public void deserializesFromJSON() throws Exception {
        final Person person = new Person("Luther Blissett", "Senior Software Engineeer");
        assertThat(ObjectMapperUtils.getObjectMapper().readValue(fixture("fixtures/person.json"), Person.class))
                .isEqualTo(person);
    }
	
}
