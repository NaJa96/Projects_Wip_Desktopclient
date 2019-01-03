package bertelsbank.loginview;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bertelsbank.clientview.ClientviewController;
import bertelsbank.transaction.Account;
import de.fhdw.javafx.desktopclient.ServerAccess;
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
	private Text txtError;
	
    @FXML
    private TextField inputTxtIpAddress;

	/**
	 * Set the IP address and check the account number with querying the account information from the server
	 * 
	 * @param event
	 * @throws IOException
	 * @author NadinJan�en
	 */
	@FXML
	void signInButtonAction(ActionEvent event) throws IOException {

		if (!inputTxtAccNumber.getText().isEmpty()) {
			try {
				
				ServerAccess.setIpAdress(inputTxtIpAddress.getText().toString());
				HttpResponse response = serverAccess.getAccountResponse(inputTxtAccNumber.getText());

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String accountNumber = EntityUtils.toString(response.getEntity());
					Gson gson = new GsonBuilder().create();
					Account account = gson.fromJson(accountNumber, Account.class);
					ServerAccess.setAccount(account);

					FXMLLoader loader = new FXMLLoader(getClass().getResource("/bertelsbank/clientview/clientview.fxml"));
					Parent root = loader.<Parent>load();
					ClientviewController controller = loader.<ClientviewController>getController();
					Scene scene = new Scene(root);
					Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					stage.setScene(scene);
					stage.show();

				} else {
					txtError.setText(EntityUtils.toString(response.getEntity()) + " (Fehler: "
							+ response.getStatusLine().getStatusCode() + ")");
				}
			} catch (IOException e) {
				e.printStackTrace();
				txtError.setText("Server nicht verf�gbar");
			}

		}

	}

}
