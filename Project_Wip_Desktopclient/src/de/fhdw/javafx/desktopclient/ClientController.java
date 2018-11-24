package de.fhdw.javafx.desktopclient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

public class ClientController {
	
		ServerAccess serverAccess = new ServerAccess();

	    @FXML
	    private Text txtSalutation;

	    @FXML
	    private Text txtAccountBalance;

	    @FXML
	    private Button btnTransaction;
	    
	    @FXML
	    public void initialize(){
	    	
	    	txtSalutation.setText(ServerAccess.getAccount().getOwner());
	    }

	    @FXML
	    void transaction(ActionEvent event) {
	    	
	    	

	    }

	}
