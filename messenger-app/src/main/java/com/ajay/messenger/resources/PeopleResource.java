package com.ajay.messenger.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.ajay.messenger.configs.ServiceConfig;
import com.ajay.messenger.dto.PersonDTO;
import com.ajay.messenger.models.Person;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class PeopleResource {

	private final ServiceConfig serviceConfig;
	private final PersonDTO personDTO;
	private static final Logger logger = Logger.getLogger(PeopleResource.class);
	
	public PeopleResource(ServiceConfig serviceConfig, PersonDTO personDTO) {
		this.serviceConfig = serviceConfig;
		this.personDTO = personDTO;
	}
	
	@GET
	@Timed
	@UnitOfWork
	@Path("/get-person/{id}")
	public String getPerson(@PathParam("id") long id) {
		logger.info("create people");
		System.out.println("Just to check: " + serviceConfig.getPaytm().get("host"));
		Optional<Person> persons = personDTO.findById(id);
		if(persons.isPresent()) {
			Person p = persons.get();
			System.out.println("person : " + p.getFullName());
			return p.getJobTitle();
			
		}
		return "LOL";
	}
}
