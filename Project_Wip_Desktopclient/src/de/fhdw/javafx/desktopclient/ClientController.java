package de.fhdw.javafx.desktopclient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.List;

import javafx.event.ActionEvent;

public class ClientController {
	
		ServerAccess serverAccess = new ServerAccess();
		Account currentAccount;

	    @FXML
	    private Text txtSalutation;

	    @FXML
	    private Text txtAccountBalance;

	    @FXML
	    private Button btnTransaction;
	    
	    @FXML
	    public void initialize(){
	    	
	    	currentAccount = ServerAccess.getAccount();
	    	
	    	txtSalutation.setText(currentAccount.getOwner());
	    	//txtAccountBalance.setText(calcBalance());
	    }

	    @FXML
	    void transaction(ActionEvent event) {
	    	
	    	

	    }
	    
	    private String calcBalance(){
	    	List<Transaction> transactions = currentAccount.getTransactions();
	    	
			return null;
	    }

	}
