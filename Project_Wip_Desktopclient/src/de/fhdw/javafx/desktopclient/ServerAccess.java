package de.fhdw.javafx.desktopclient;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class ServerAccess {

	static Account account;

	static BigDecimal accountBalance;
	
	private static String ipAdress;

	public static void setIpAdress(String ipAdress) {
		ServerAccess.ipAdress = ipAdress;
	}

	public static BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public static void setAccountBalance(BigDecimal accountBalance) {
		ServerAccess.accountBalance = accountBalance;
	}

	public static Account getAccount() {
		return account;
	}

	public static void setAccount(Account loginAccount) {
		account = loginAccount;
	}

	public HttpResponse getAccountResponse(String accountNumber) throws ClientProtocolException, IOException {

		HttpResponse response;
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(String.format("http://%s/rest/account/%s" , ipAdress, accountNumber));
		return response = client.execute(get);

	}

	public HttpResponse postTransaction(String senderNumber, String receiverNumber, String amount, String reference) throws ClientProtocolException, IOException {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(String.format("http://%s/rest/transaction", ipAdress));
		List<NameValuePair> parameterList = new ArrayList<>();
		parameterList.add(new BasicNameValuePair("senderNumber", senderNumber));
		parameterList.add(new BasicNameValuePair("receiverNumber", receiverNumber));
		parameterList.add(new BasicNameValuePair("amount", amount));
		parameterList.add(new BasicNameValuePair("reference", reference));
		UrlEncodedFormEntity form = new UrlEncodedFormEntity(parameterList, "UTF-8");

		post.setEntity(form);
		HttpResponse httpResponse = client.execute(post);
		return httpResponse;

	}
}
