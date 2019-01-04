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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import bertelsbank.transaction.Account;

import java.util.ArrayList;

public class ServerAccess {

	static Account account;

	static BigDecimal accountBalance;
	
	private static String ipAddress;

	public static void setIpAdress(String ipAdress) {
		ServerAccess.ipAddress = ipAdress;
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
		final HttpParams httpParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
	    DefaultHttpClient client = new DefaultHttpClient(httpParams);
		HttpGet get = new HttpGet(String.format("http://%s/rest/account/%s" , ipAddress, accountNumber));
		return response = client.execute(get);

	}

	public HttpResponse postTransaction(String senderNumber, String receiverNumber, String amount, String reference) throws ClientProtocolException, IOException {
		
		final HttpParams httpParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
	    DefaultHttpClient client = new DefaultHttpClient(httpParams);
		HttpPost post = new HttpPost(String.format("http://%s/rest/transaction", ipAddress));
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
