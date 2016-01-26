package com.ajay.messenger;

import com.ajay.messenger.dto.CommentDTO;
import com.ajay.messenger.dto.CommentDTOImpl;
import com.ajay.messenger.dto.MessageDTO;
import com.ajay.messenger.dto.MessageDTOImpl;
import com.ajay.messenger.dto.PersonDTO;
import com.ajay.messenger.dto.ProfileDTO;
import com.ajay.messenger.dto.ProfileDTOImpl;
import com.ajay.messenger.exceptions.DataNotFoundExceptionMapper;
import com.ajay.messenger.exceptions.GenericExceptionMapper;
import com.ajay.messenger.filters.AuthFilter;
import com.ajay.messenger.filters.LoginFilter;
import com.ajay.messenger.filters.PoweredByResponseFilter;
import com.ajay.messenger.health.DatabaseHealthCheck;
import com.ajay.messenger.health.TemplateHealthCheck;
import com.ajay.messenger.messagebodywriters.DateMessageBodyWriter;
import com.ajay.messenger.messagebodywriters.ShortDateMessageBodyWriter;
import com.ajay.messenger.models.Comment;
import com.ajay.messenger.models.Message;
import com.ajay.messenger.models.Person;
import com.ajay.messenger.models.Profile;
import com.ajay.messenger.models.RecordTracker;
import com.ajay.messenger.paramconverters.MyDateParamConverter;
import com.ajay.messenger.resources.CommentResource;
import com.ajay.messenger.resources.IndexResource;
import com.ajay.messenger.resources.MessageResource;
import com.ajay.messenger.resources.PeopleResource;
import com.ajay.messenger.resources.ProfileResource;
import com.ajay.messenger.services.CommentService;
import com.ajay.messenger.services.MessageService;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;


public class MessengerAppApplication extends Application<MessengerAppConfiguration> {

	private final HibernateBundle<MessengerAppConfiguration> hibernate = 
			new HibernateBundle<MessengerAppConfiguration>(Person.class, Comment.class, Message.class, Profile.class, RecordTracker.class) {
		//more entities can be added separated with a comma
		@Override
	    public DataSourceFactory getDataSourceFactory(MessengerAppConfiguration configuration) {
	        return configuration.getDatabase();
	    }
	};
	
    @Override
    public String getName() {
        return "MessengerApp";
    }

    @Override
    public void initialize(final Bootstrap<MessengerAppConfiguration> bootstrap) {
        
    	bootstrap.addBundle(hibernate);
    	
    	// Asset Bundle
    	bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
    	
    	// Enable View
    	bootstrap.addBundle(new ViewBundle<MessengerAppConfiguration>());
    }

    @Override
    public void run(final MessengerAppConfiguration configuration,
                    final Environment environment) {
        
		final PersonDTO personDTO = new PersonDTO(hibernate.getSessionFactory());
    	final CommentDTO commentDTO = new CommentDTOImpl(hibernate.getSessionFactory());
    	final MessageDTO messageDTO = new MessageDTOImpl(hibernate.getSessionFactory());
    	final ProfileDTO profileDTO = new ProfileDTOImpl(hibernate.getSessionFactory());
    	
    	// healthCheck
    	environment.healthChecks().register("template", new TemplateHealthCheck(configuration.getTemplate()));
    	environment.healthChecks().register("database", new DatabaseHealthCheck(configuration.getDatabase()));
    	
    	 // Register the custom ExceptionMapper(s)
        environment.jersey().register(new GenericExceptionMapper());
        environment.jersey().register(new DataNotFoundExceptionMapper());
        
        // Register custom filter(s)
        environment.jersey().register(new AuthFilter());
        environment.jersey().register(new LoginFilter());
        environment.jersey().register(new PoweredByResponseFilter());
        
        // Register message body writer
        environment.jersey().register(new DateMessageBodyWriter());
        environment.jersey().register(new ShortDateMessageBodyWriter());
        
        // Register param converter
        environment.jersey().register(new MyDateParamConverter());
        
    	// Resource Registration
    	environment.jersey().register(new IndexResource(configuration.getTemplate(), configuration.getDefaultName(), configuration.getServiceConfig()));
    	environment.jersey().register(new MessageResource(new MessageService(messageDTO)));
    	environment.jersey().register(new ProfileResource(profileDTO));
    	environment.jersey().register(new CommentResource(new CommentService(commentDTO, messageDTO)));
    	environment.jersey().register(new PeopleResource(configuration.getServiceConfig(), personDTO));
    }

    
    
    public static void main(final String[] args) throws Exception {
        new MessengerAppApplication().run(args);
    }

    
}
