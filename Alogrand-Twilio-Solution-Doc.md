## Overview

Smart phones have become apart of everyday life. Whether its a text, a call, using apps, or browsing the
internet, these hand held devices allow us to communicate in efficient and innovative ways.

In this solution, you will see how to interact with the Algorand Blockchain using SMS and Voice. This sample project uses the PureStake API for interacting with the Algorand Blockchain, and the Twilio APIs for processing text and voice requests to be sent against the Algorand Blockchain through the PureStake API. By the end of this walkthrough you should have a basic understanding of how these APIs can work together, and hopefully it will inspire you to create more complex implementations.

[TOC]

### Goal
The goal of the project is to interact with the Algorand Blockchain through texting and calling.
For this solution, we will be able to:
- request the last round on any network (MainNet/BetaNet/TestNet) through text and a phone call
- request the supply on any network (MainNet/BetaNet/TestNet) through text and a phone call


### Requirements
- Maven: https://maven.apache.org/download.cgi
- JDK 8: https://www.oracle.com/java/technologies/javase-jdk8-downloads.html
- Ngrok: https://ngrok.com/download
- Twilio Developer API Account: https://www.twilio.com/try-twilio
- PureStake Developer API Account:  https://developer.purestake.io/

!!! tip
    I recommend adding all of your API credentials to a `resources/application.properties`
    file so that they can be accessed as environment variables.


#### How it works: SMS


Flow Diagram:
![Algorand Image](https://drive.google.com/file/d/1IwpcWGmC1soxybOAO5glZGIrXkCS7Orm/view?usp=sharing)


Using a webhook, when a text is sent to the Twilio number, Twilio forwards the text to a specified`GET` request.

Twilio WebHook configuration:

![twilioImage](https://user-images.githubusercontent.com/6632748/82156853-e169d500-984b-11ea-9847-02470e8e9a39.JPG)


The text message is received as an `HttpServeletRequest`, and once the body (text message) of the request is extracted, 
it is sent to the Algorand API to query.

Recieving Text as HttpServelet request:

```
@RequestMapping(value = "/text", produces = "application/xml", method = RequestMethod.GET)
	public void getPureStakeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String body = request.getParameter("Body");
		String message = smsSender.processRequest(body);
		...
```

The result of that query is then sent to the Algorand PureStake API to be processed, based on the query:

```
public String processRequest(String request) {
		String result = null;
		switch (request.toLowerCase()) {
		case "supply":
			result = pureStake.getSupply();
			break;
		case "lastround":
			result = pureStake.getLastRound();
			break;
		...
```

Twilio then builds a message using this result, and sends it back to the number as an `HttpServletResponse`:

```
		...
		Body messageBody = new Body.Builder(message).build();
		Message sms = new Message.Builder().body(messageBody).build();
		MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();

		response.setContentType("application/xml");

		try {
			response.getWriter().print(twiml.toXml());
		} catch (TwiMLException e) {
			e.printStackTrace();
		}
```



#### How it works: Voice


![Voice Flow Chart](https://drive.google.com/file/d/1RoUd3A5Db2n3jwuXFKDAFVwCYV-1k_54/view?usp=sharing)


Twilio makes it simple to implement an IVR (Interactive Voice Response) system.
This provides the incoming caller with a navigation menu, which allows them to get the information they are looking for.
In our case, the caller would indicate if they are looking for information on the last round or supply and 
recieve information relevant to whichever option they choose.


The interaction with with the Algorand PureStake API is very similar to how it is done with text, the difference is how 
Twilio responds to the caller when they choose different options. Also, whereas Twilio SMS uses `MessageResponse()` to build 
responses to send back to the user, Twilio Voice uses a `VoiceResponse()`.

A webhook has to be configured to handle voice requests sent to the Twilio number:

![vcTwilio](https://user-images.githubusercontent.com/6632748/82241711-9f539880-990a-11ea-8d39-d19a01adf0cf.JPG)


When using Twilio Voice, the more navigation options there are, the more request methods are needed to handle each step of the naviation menu.

The first request provides the caller with a greeting message, instructions, and captures the users incoming selection (whether the caller presses 1 or 2).
It then forwards the callers input to another request which processes which number they chose:

```
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
```

Request that received user's input and uses helper method to figure out the response, and return it to the caller:

```
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
```

Helper method that creates voice response to return to the caller:

```
private VoiceResponse getReply(String string) {
		String someInt = smsSender.processRequestForVoice(string);

		VoiceResponse response = new VoiceResponse.Builder()
				.say(new Say.Builder("The " + " " + string + " " + "is:").build()).say(new Say.Builder(someInt).build())
				.say(new Say.Builder(
						"Thank you for trying our Algorand Blockchain network phone service. Have a nice day").build())
				.build();

		return response;
	}
```


###Conclusion

In this solution Twilio was used to receive information from the Algorand Blockchain. One way to expand on Twilios capabilities 
would be to executes POST requests where you are making transactions on the Blockchain. Hopefully this has been an insightful walkthrough, 
and I hope to see how the developer community can expand on this!
















