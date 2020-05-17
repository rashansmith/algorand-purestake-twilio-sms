# Summary

This sample java project uses the Twilio SMS api to allow users to interact with the Algorand Purestake API:
Through text are able to:
- get the supply on MainNet/TestNet/BetaNet
- get the last round on MainNet/TestNet/BetaNet


### Prerequisites:
- Java 1.8 or higher
- Ngrok (to expose your localhost:8080): https://ngrok.com/download
- Twilio Developer API account: https://www.twilio.com/try-twilio
- PureStake Developer API account: https://developer.purestake.io/


### Twilio Configuration Instructions:
If you want more detailed instructions: https://www.twilio.com/blog/2013/10/test-your-webhooks-locally-with-ngrok.html


Expose your localhost:8080 port with ngrok (windows command). The exposed url will be 
used in the Twilio Developer Console:

```
ngrok http 8080 
```

![ngrokImage](https://user-images.githubusercontent.com/6632748/82156857-e7f84c80-984b-11ea-9142-500e47542ef2.JPG)


Configure your Twilio Messaging webhook to use exposed ngrok address:
- In the Twilio Dev Console, Go to Manage Numbers ->  Active Numbers and click on the number you are going to use for this project
- Scroll down to the Messaging section
- Add '/text' at the end of your url (this the endpoint defined in our controller)
- Set the WebHook to `GET`
- Click save


![twilioImage](https://user-images.githubusercontent.com/6632748/82156853-e169d500-984b-11ea-9847-02470e8e9a39.JPG)




### There are 2 ways to deploy the application with your credentials:

### 1. Add credentials to `application.properties` and then deploy the application : 

Modify the `src/main/resources/application.properties` file:

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


From the project directory in the command terminal: 

```
mvn spring-boot:run
```

### OR 

### 2. Deploy application and add credentials through Web Browser Form : 

From the project directory in the command terminal: 

```
mvn spring-boot:run
```


Navigating to localhost:8080/sendForm and providing your credentials there:

![sendForm](https://user-images.githubusercontent.com/6632748/82156856-e62e8900-984b-11ea-9905-109212c69e5c.JPG)






#### Test application
Text 'supply' to your twilio number:

![supplyScreenshot](https://user-images.githubusercontent.com/6632748/82157421-5559ac80-984f-11ea-8bc9-eb12ef035f74.jpg)

Text 'lastround' to your twilio number:

![lastRoundScreenShot](https://user-images.githubusercontent.com/6632748/82157430-62769b80-984f-11ea-937d-547d599bd3ef.jpg)






