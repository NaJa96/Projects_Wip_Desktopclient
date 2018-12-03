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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;

public class LoginviewController {

	ServerAccess serverAccess = new ServerAccess();

	@FXML
	private ImageView imageLogo;

	@FXML
	private TextField inputTxtAccNumber;

	@FXML
	private Button btnSignIn;

	@FXML
	private Text errorText;

	@FXML
	void handleButtonAction(ActionEvent event) throws IOException {

		errorText.setText("Bitte gib eine Kontonummer ein");

		if (!inputTxtAccNumber.getText().isEmpty()) {
			try {

				HttpResponse response = serverAccess.getAccountResponse(inputTxtAccNumber.getText());

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String accountNumber = EntityUtils.toString(response.getEntity());
					Gson gson = new GsonBuilder().create();
					Account account = gson.fromJson(accountNumber, Account.class);
					ServerAccess.setAccount(account);

					FXMLLoader loader = new FXMLLoader(getClass().getResource("clientview.fxml"));
					Parent root = loader.<Parent>load();
					ClientviewController controller = loader.<ClientviewController>getController();
					Scene scene = new Scene(root);
					Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					stage.setScene(scene);
					stage.show();

				} else {
					errorText.setText(EntityUtils.toString(response.getEntity()) + " (Fehler: "
							+ response.getStatusLine().getStatusCode() + ")");
				}
			} catch (IOException e) {
				e.printStackTrace();
				errorText.setText("Server nicht verfügbar");
			}

		}

	}

}
