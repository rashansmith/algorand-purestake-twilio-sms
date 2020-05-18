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
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Say;

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

	@RequestMapping(value = "/voice", produces = "application/xml", method = RequestMethod.GET)
	public void getPureStakeInfoVoice(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String message = "Welcome to the Algorand Blockchain Network. "
				+ "	Press 1 to find out information about the supply,"
				+ "Press 2 to find out information about the last round.";

		VoiceResponse.Builder builder = new VoiceResponse.Builder().gather(new Gather.Builder().numDigits(1)
				.action("/createResponse").say(new Say.Builder(message).build()).build());

		response.setContentType("application/xml");
		try {
			response.getWriter().print(builder.build().toXml());
		} catch (TwiMLException e) {
			throw new RuntimeException(e);
		}

	}

	@RequestMapping(value = "/createResponse", produces = "application/xml", method = RequestMethod.POST)
	public void getPureStakeInfoVoiceResult(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String digits = request.getParameter("Digits");
		VoiceResponse vResponse;

		if (digits != null) {
			switch (digits) {
			case "1":
				vResponse = getReply("supply");
				break;
			case "2":
				vResponse = getReply("lastround");
				break;
			default:
				vResponse = getReply("supply");
			}
		} else {
			vResponse = getReply("supply");
		}

		response.setContentType("application/xml");
		try {
			response.getWriter().print(vResponse.toXml());
		} catch (TwiMLException e) {
			throw new RuntimeException(e);
		}
	}

	private VoiceResponse getReply(String string) {

		String someInt = smsSender.processRequestForVoice(string);

		VoiceResponse response = new VoiceResponse.Builder()
				.say(new Say.Builder("The " + " " + string + " " + "is:").build()).say(new Say.Builder(someInt).build())
				.say(new Say.Builder(
						"Thank you for trying our Algorand Blockchain network phone service. Have a nice day").build())
				.build();

		return response;
	}

	@RequestMapping(value = "/testText", produces = "application/xml", method = RequestMethod.GET)
	public void respondToText() {

		try {
			smsSender.sendMessage("Your text was successful!!");
		} catch (Exception ex) {
		}
	}

}