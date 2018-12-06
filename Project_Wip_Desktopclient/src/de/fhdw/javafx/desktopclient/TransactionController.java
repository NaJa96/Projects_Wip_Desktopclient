package de.fhdw.javafx.desktopclient;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TransactionController {

	ServerAccess serverAccess = new ServerAccess();
	Account currentAccount;

	@FXML
	private ImageView imageLogo;

	@FXML
	private Text txtAccountBalance;

	@FXML
	private TextField inputTxtValue;

	@FXML
	private TextField inputTxtAccountNumber;

	@FXML
	private TextField inputTextReference;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnForward;

	@FXML
	private Text txtError;

	@FXML
	public void initialize() {
		currentAccount = ServerAccess.getAccount();

		BigDecimal accountBalance = serverAccess.getAccountBalance();
		String accountBalanceAsString = accountBalance.toString();
		txtAccountBalance.setText(accountBalanceAsString);

	}

	@FXML
	void cancelBtnAction(ActionEvent event) {

		try {
			Stage stage;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("clientview.fxml"));
			Parent root = loader.<Parent>load();
			ClientviewController controller = loader.<ClientviewController>getController();
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * description
	 * 
	 * @param event
	 * @author Nadin Janßen
	 */
	@FXML
	void transactionBtnAction(ActionEvent event) {
		String stringValue = inputTxtValue.getText();
		stringValue = stringValue.replace(",", ".");
		String stringReference = inputTextReference.getText();
		String stringAccountNumber = inputTxtAccountNumber.getText();
		System.out.println(stringValue);
		try {

			HttpResponse httpResponse = serverAccess.postTransaction(currentAccount.getNumber(), stringAccountNumber,
					stringValue, stringReference);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			String entityMsg = "";
			if (statusCode != HttpStatus.SC_NO_CONTENT) {
				entityMsg = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				String errorMsg = " (Fehler " + httpResponse.getStatusLine().getStatusCode() + ")";
				txtError.setText(entityMsg + errorMsg);
			}else{
				try {
					Stage stage;
					FXMLLoader loader = new FXMLLoader(getClass().getResource("clientview.fxml"));
					Parent root = loader.<Parent>load();
					ClientviewController controller = loader.<ClientviewController>getController();
					controller.refresh();
					Scene scene = new Scene(root);
					stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			txtError.setText("Server nicht verfügbar. Versuchen Sie es später noch mal.");
		}

		

	}

}
