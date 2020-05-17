package com.example.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.ApplicationScope;

import com.example.twilio.SetEnvironmentVariables;
import com.example.twilio.SmsSender;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

@Controller
@ApplicationScope
@Component
public class AppController {

	@Autowired
	SmsSender smsSender;

	@Autowired
	SetEnvironmentVariables setEnv;

	@RequestMapping(value = "/text", produces = "application/xml", method = RequestMethod.GET)
	public void getPureStakeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String body = request.getParameter("Body");
		String message = smsSender.processRequest(body);

		// Create a TwiML response and add our friendly message.
		Body messageBody = new Body.Builder(message).build();
		Message sms = new Message.Builder().body(messageBody).build();
		MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();

		response.setContentType("application/xml");

		try {
			response.getWriter().print(twiml.toXml());
		} catch (TwiMLException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/authentication", method = RequestMethod.POST)
	public String authenticate(@RequestParam(value = "twilio_account_sid") String twilio_account_sid,
			@RequestParam(value = "twilio_auth_token") String twilio_auth_token,
			@RequestParam(value = "twilio_twilio_number") String twilio_twilio_number,
			@RequestParam(value = "twilio_my_number", required = false) String twilio_my_number,
			@RequestParam(value = "algod_api_address") String algod_api_address,
			@RequestParam(value = "algod_api_key") String algod_api_key) {

		setEnv.setEnvVars(twilio_account_sid, twilio_auth_token, twilio_twilio_number, twilio_my_number,
				algod_api_address, algod_api_key);

		return "index";
	}

	@RequestMapping(value = "/sendForm", produces = "application/xml", method = RequestMethod.GET)
	public String sendForm() {

		return "index";
	}

	@RequestMapping(value = "/testText", produces = "application/xml", method = RequestMethod.GET)
	public void respondToText() {

		try {
			smsSender.sendMessage("Your text was successful!!");
		} catch (Exception ex) {
		}
	}

}