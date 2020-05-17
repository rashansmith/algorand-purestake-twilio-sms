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

Expose your localhost:8080 port (windows command)

```
ngrok http 8080 
```

Configure your Twilio Messaging webhook to use exposed ngrok address

Instructions: https://www.twilio.com/blog/coding-twilio-webhooks-in-java-with-spring-boot



#### Deploy application
From the project directory in the command terminal: 

```
mvn spring-boot:run
```

#### Add your PureStake and Twilio API credentials to the application by:

1. Editing the `src/main/resources/application.properties` file OR
2. navigating to localhost:8080/sendForm and providing your credentials there



#### Test application
- Text 'supply' to your twilio number
- Text 'lastround' to your twilio number



