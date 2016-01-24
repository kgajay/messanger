package com.ajay.messenger;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.Setter;

import com.ajay.messenger.configs.ServiceConfig;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessengerAppConfiguration extends Configuration {
    
	@NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";
    
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
    
    @NotNull
    private ServiceConfig serviceConfig = new ServiceConfig();
	
}
