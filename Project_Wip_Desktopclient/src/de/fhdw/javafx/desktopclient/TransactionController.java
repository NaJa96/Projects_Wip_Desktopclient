package de.fhdw.javafx.desktopclient;

import java.io.IOException;
import java.math.BigDecimal;

import javax.swing.plaf.synth.SynthSeparatorUI;

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
	    private TextField inputTextPurpose;

	    @FXML
	    private Button btnCancel;

	    @FXML
	    private Button btnForward;
	    
	    @FXML
	    public void initialize(){
	    	currentAccount = ServerAccess.getAccount();
	    	
	    	
	    	BigDecimal accountBalance = serverAccess.getAccountBalance();
			String accountBalanceAsString = accountBalance.toString();
	    	txtAccountBalance.setText(accountBalanceAsString);
	    	
	    }
	    
	    @FXML
	    void cancel(ActionEvent event) {
	    	
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

	    @FXML
	    void transaction(ActionEvent event) {
	    	String stringValue = inputTxtValue.getText();
	    	String stringPurpose = inputTextPurpose.getText();
	    	String stringAccountNumber = inputTxtAccountNumber.getText();    	
	    	serverAccess.postTransaction(currentAccount.getNumber(), stringAccountNumber, stringValue, stringPurpose);
	    }

	}

