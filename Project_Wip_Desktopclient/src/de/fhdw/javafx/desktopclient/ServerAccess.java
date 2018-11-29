package de.fhdw.javafx.desktopclient;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


public class ServerAccess {
	
	static Account account;
	
	static BigDecimal accountBalance;
	
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
	
	public HttpResponse getAccountResponse(String accountNumber) throws ClientProtocolException, IOException{
		
		HttpResponse response;
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(String.format("http://localhost:9998/rest/account/" + accountNumber)); 
		return response = client.execute(get);
	
	}

	public void postTransaction(String senderNumber, String receiverNumber, String amount, String reference ){
    	
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://localhost:9998/rest/transaction");
                List<NameValuePair> parameterList = new ArrayList<>();
                parameterList.add(new BasicNameValuePair("senderNumber", senderNumber));
                parameterList.add(new BasicNameValuePair("receiverNumber", receiverNumber));
                parameterList.add(new BasicNameValuePair("amount", amount));
                parameterList.add(new BasicNameValuePair("reference", reference));
                System.out.println("läuft?");
                try {

                    UrlEncodedFormEntity form = new UrlEncodedFormEntity(parameterList,"UTF-8");

                    post.setEntity(form);
                    HttpResponse httpResponse = client.execute(post);
                    
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    String entityMsg = "";
                    if(statusCode != HttpStatus.SC_NO_CONTENT){
                            entityMsg = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
                        String errorMsg = " (Fehler " + httpResponse.getStatusLine().getStatusCode() + ")";
                        System.out.println(entityMsg + errorMsg);
                       }
                } catch (IOException e) {
                    e.printStackTrace();
                }
         }
}
