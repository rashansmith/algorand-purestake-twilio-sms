package com.example.twilio;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class SetEnvironmentVariables {

	public void setEnvVars(String twilio_account_sid,
			String twilio_auth_token,
			String twilio_twilio_number,
			String twilio_my_number,
			String algod_api_address,
			String algod_api_key) {
		
		System.setProperty("spring.twilioAccountSid", twilio_account_sid);
		System.setProperty("spring.twilioAuthToken", twilio_auth_token);
		System.setProperty("spring.twilioNumber", twilio_twilio_number);
		System.setProperty("spring.myNumber", twilio_my_number);
		setAddress(algod_api_address);
		System.setProperty("spring.algodApiKey", algod_api_key);
	}
	
	public void setAddress(String addr) {
		switch (addr) {
		case "MainNet":
			System.setProperty("spring.algodApiAddress.url", "https://mainnet-algorand.api.purestake.io/ps1");
		case "BetaNet":
			System.setProperty("spring.algodApiAddress.url", "https://betanet-algorand.api.purestake.io/ps1");
			break;
		case "TestNet":
			System.setProperty("spring.algodApiAddress.url", "https://testnet-algorand.api.purestake.io/ps1");
			break;
		default:
			System.setProperty("spring.algodApiAddress.url", "https://mainnet-algorand.api.purestake.io/ps1");
		}
	}
}
