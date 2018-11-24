package de.fhdw.javafx.desktopclient;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerAccess {
	
	static Account account;
	
	public static Account getAccount() {
		return account;
	}

	public static void setAccount(Account loginAccount) {
		account = loginAccount;
	}
	
	public HttpResponse getAccountResponse(String accountNumber) throws ClientProtocolException, IOException{
		
		HttpResponse response;
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(String.format("http://localhost:9998/rest/account/" + accountNumber)); //
		return response = client.execute(get);
	
	}

}
