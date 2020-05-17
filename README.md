## Summary

This sample java project uses the Twilio SMS api to allow users to interact with the Algorand Purestake API:
Through text are able to:
- get the supply on MainNet/TestNet/BetaNet
- get the last round on MainNet/TestNet/BetaNet


### Prerequisites:
- Java 1.8 or higher
- Ngrok (to expose your localhost:8080)
- Twilio Developer API account: 
- PureStake Developer API account


### Twilio Configuration Instructions:
If you want more detailed instructions: https://www.twilio.com/blog/2013/10/test-your-webhooks-locally-with-ngrok.html


Expose your localhost:8080 port with ngrok (windows command). The exposed url will be 
used in the Twilio Developer Console:

![Ngrok generated url](ngrokImage.jpg)


```
ngrok http 8080 
```

Configure your Twilio Messaging webhook to use exposed ngrok address:
- Go to Manage Numbers ->  Active Numbers and click on the number you are going to use for this project
- Scroll down to the Messaging section
- Add '/text' at the end of your url (this the endpoint defined in our controller)
- Set the WebHook to `GET`
- Click save


![Twlio Developer Console](twilioImage.jpg)


#### Deploy application
From the project directory in the command terminal: 

```
mvn spring-boot:run
```

#### Add your PureStake and Twilio API credentials to the application by:

Modifying the `src/main/resources/application.properties` file:	

```
# Twilio API Keys and relevant variables
spring.twilioAccountSid= # Add your AccountSID here 
spring.twilioAuthToken=  # Add your AuthToken here 
spring.twilioNumber=	# Add your twilioNumber here 
spring.myNumber=         # Add yourNumber here (optional)

#PureStake API keys and relevant variables
spring.algodApiAddress.url= # Add your PureStake API Address here
spring.algodApiKey= # Add your PureStake API Key here
```

OR 

Navigating to localhost:8080/sendForm and providing your credentials there:

![Provide Credentials](sendForm.jpg)


#### Test application
- Text 'supply' to your twilio number
- Text 'lastround' to your twilio number






