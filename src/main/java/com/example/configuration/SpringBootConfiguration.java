package com.example.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.example.purestake.PureStake;
import com.example.twilio.SmsSender;

@Configuration
@Component
public class SpringBootConfiguration {
	@Bean
	public SmsSender smsSender() {
	    return new SmsSender();
	}
	
	@Bean
	public PureStake pureStake() {
	    return new PureStake();
	}
	
}

