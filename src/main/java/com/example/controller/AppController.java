package com.example.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.ApplicationScope;

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

	@RequestMapping(value = "/text", produces = "application/xml", method = RequestMethod.GET)
	public void getPureStakeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String body = request.getParameter("Body");
		String message = smsSender.processRequest(body);

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