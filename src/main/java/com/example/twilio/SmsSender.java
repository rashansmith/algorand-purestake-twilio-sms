package com.example.twilio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.purestake.PureStake;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
@Service
public class SmsSender {

	@Autowired
	PureStake pureStake;

	@Value("${spring.twilioAccountSid}")
	String TwilioAccountSID;

	@Value("${spring.twilioAuthToken}")
	String TwilioAuthToken;

	@Value("${spring.twilioNumber}")
	String TwilioNumber;

	@Value("${spring.myNumber}")
	String MyNumber;

	public void sendMessage(String messageToSend) {
		Twilio.init(TwilioAccountSID, TwilioAuthToken);
		Message message = Message.creator(new PhoneNumber(MyNumber), new PhoneNumber(TwilioNumber), messageToSend)
				.create();
		System.out.println(message.getSid());

	}

	public String processRequest(String request) {
		String result = null;
		switch (request.toLowerCase()) {
		case "supply":
			result = pureStake.getSupply();
			break;
		case "lastround":
			result = pureStake.getLastRound();
			break;
		case "helpme":
			result = pureStake.getInstructions();
			break;
		default:
			result = pureStake.getErrorMessage();
		}
		return result;
	}

	public String processRequestForVoice(String request) {
		String result = null;
		switch (request.toLowerCase()) {
		case "supply":
			result = pureStake.getSupplyInt().get(1);
			break;
		case "lastround":
			result = pureStake.getLastRoundInt();
			break;
		default:
			result = "Your request was not understood, please call again";
		}
		return result;
	}

}
