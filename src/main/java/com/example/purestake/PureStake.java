package com.example.purestake;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.algorand.algosdk.algod.client.AlgodClient;
import com.algorand.algosdk.algod.client.ApiException;
import com.algorand.algosdk.algod.client.api.AlgodApi;
import com.algorand.algosdk.algod.client.model.Supply;

@Component
@Service
public class PureStake {

	@Value("${spring.algodApiAddress.url}")
	String algoApiAddress;

	@Value("${spring.algodApiKey}")
	String algoApiKey;

	static AlgodClient client;
	static AlgodApi algodApiInstance;

	public void init() {
		client = new AlgodClient();
		client.setBasePath(algoApiAddress);
		client.addDefaultHeader("X-API-Key", algoApiKey);
		algodApiInstance = new AlgodApi(client);
	}

	public String getStatus() {
		init();
		String status = null;
		try {
			status = algodApiInstance.getStatus().toString();
		} catch (ApiException e) {
			System.err.println("Exception when calling algod#getStatus");
			e.printStackTrace();
		}
		return status;
	}

	public String getSupply() {
		init();
		String supplyResult = null;

		try {
			Supply supply = algodApiInstance.getSupply();
			supplyResult = "Supply Summary " + "\n" + "Online Money: " + supply.getOnlineMoney() + "\n" + "Round: "
					+ supply.getRound() + "\n" + "Total Money: " + supply.getTotalMoney();

		} catch (ApiException e) {
			System.err.println("Exception when calling algod#getSupply");
			e.printStackTrace();
		}

		return supplyResult;
	}

	public List<String> getSupplyInt() {
		init();
		List<String> arr = new ArrayList<String>();

		try {
			Supply supply = algodApiInstance.getSupply();
			arr.add(supply.getOnlineMoney().toString());
			arr.add(supply.getTotalMoney().toString());

		} catch (ApiException e) {
			System.err.println("Exception when calling algod#getSupply");
			e.printStackTrace();
		}

		return arr;
	}

	public String getLastRound() {
		init();
		String lastRound = null;
		try {
			lastRound = "The last round is: " + algodApiInstance.getStatus().getLastRound().toString();
		} catch (ApiException e) {
			System.err.println("Exception when calling algod#lastRound");
			e.printStackTrace();
		}
		return lastRound;
	}

	public String getLastRoundInt() {
		init();
		String round = null;

		try {
			round = algodApiInstance.getStatus().getLastRound().toString();

		} catch (ApiException e) {
			System.err.println("Exception when calling algod#getSupply");
			e.printStackTrace();
		}
		return round;
	}

	public String getInstructions() {
		init();
		String instructions = null;

		instructions = "Welcome to The PureStake & Twilio API project. " + "\n"
				+ " Text 'supply' to get the latest supply info" + "\n" + " Text 'lastround' to get the last round";

		return instructions;
	}

	public String getErrorMessage() {
		init();
		String instructions = null;

		instructions = "There was an error processing your request." + "\n"
				+ " Text 'supply' to get the latest supply info" + "\n" + " Text 'lastround' to get the last round";

		return instructions;
	}
}
