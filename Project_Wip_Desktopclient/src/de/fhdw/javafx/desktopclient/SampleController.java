package de.fhdw.javafx.desktopclient;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SampleController {

	@FXML
	private ImageView image;

	@FXML
	private TextField accNumberInput;

	@FXML
	private Button signInBtn;

	@FXML
	void handleButtonAction(ActionEvent event) {
		try {
			HttpClient client = HttpClients.createDefault();
			HttpGet get = new HttpGet(String.format("http://localhost:9998/rest/account/" + accNumberInput.getText())); //
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String accountNumber = EntityUtils.toString(response.getEntity());
				Gson gson = new GsonBuilder().create();
				Account account = gson.fromJson(accountNumber, Account.class);
				System.out.println(account.getOwner());
				// return resultData.getResult();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
