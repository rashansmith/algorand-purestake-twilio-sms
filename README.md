# Summary

This sample SpringBoot Java project uses the Twilio SMS and Voice APIs to allow users to interact with the Algorand Blockchain Purestake API.
Through text and a phone calls you are able to:
- get the supply on MainNet/TestNet/BetaNet through SMS and Voice
- get the last round on MainNet/TestNet/BetaNet through SMS and Voice

This sample implementation can be expanded to many more features and interactions.


### Prerequisites:
- Maven
- JDK 8
- Ngrok (to expose your localhost:8080): https://ngrok.com/download
- Twilio Developer API account: https://www.twilio.com/try-twilio
- PureStake Developer API account: https://developer.purestake.io/

### Get Started:

```
git clone https://github.com/rashansmith/algorand-purestake-twilio-sms
cd algorand-purestake-twilio-sms
mvn clean install
```


### Twilio Configuration Instructions:
If you want more detailed instructions: https://www.twilio.com/blog/2013/10/test-your-webhooks-locally-with-ngrok.html


Expose your localhost:8080 port with ngrok (windows command). The exposed url will be 
used in the Twilio Developer Console:

```
ngrok http 8080 
```

![ngrokImage](https://user-images.githubusercontent.com/6632748/82156857-e7f84c80-984b-11ea-9142-500e47542ef2.JPG)


Configure your Twilio Messaging webhook to use exposed ngrok address:

In the Twilio Dev Console, Go to **Manage Numbers** **->**  **Active Numbers** and click on the number you are going to use for this project

Scroll down to the **Voice & Fax** section
- Add your ngrok url
- Add '/voice' at the end of your url (this the endpoint defined in our controller)
- Set the WebHook to `GET`
- Click save
	
![vcTwilio](https://user-images.githubusercontent.com/6632748/82241711-9f539880-990a-11ea-8d39-d19a01adf0cf.JPG)

Scroll down to the **Messaging** section
- Add your ngrok url
- Add '/text' at the end of your url (this the endpoint defined in our controller)
- Set the WebHook to `GET`
- Click save


![twilioImage](https://user-images.githubusercontent.com/6632748/82156853-e169d500-984b-11ea-9847-02470e8e9a39.JPG)



### Deploy the application with your credentials:

Modify the `src/main/resources/application.properties` file:

```
# Twilio API Keys and relevant variables
spring.twilioAccountSid= # Add your AccountSID here 
spring.twilioAuthToken=  # Add your AuthToken here 
spring.twilioNumber=	# Add your twilioNumber here 
spring.myNumber=         # Add your Number here (optional)

#PureStake API keys and relevant variables
spring.algodApiAddress.url= # Add your PureStake API Address here
spring.algodApiKey= # Add your PureStake API Key here
```


From the project directory in the command terminal: 

```
mvn spring-boot:run
```




### Test application
Text 'supply' to your Twilio number:

![supplyScreenshot](https://user-images.githubusercontent.com/6632748/82157421-5559ac80-984f-11ea-8bc9-eb12ef035f74.jpg)

Text 'lastround' to your Twilio number:

![lastRoundScreenShot](https://user-images.githubusercontent.com/6632748/82157430-62769b80-984f-11ea-937d-547d599bd3ef.jpg)



Call your Twilio number: 
- Press any number (to bypass trial introduction statment)
- Follow the voice command (Press 1 for the supply, press 2 for the lastround)
- a voice will provide the result and hangup.







