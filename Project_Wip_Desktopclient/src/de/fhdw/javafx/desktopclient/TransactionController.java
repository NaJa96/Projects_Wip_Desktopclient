package de.fhdw.javafx.desktopclient;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.TextField;
	import javafx.scene.image.ImageView;
	import javafx.scene.text.Text;

	public class TransactionController {

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
	    	
	    }
	    
	    @FXML
	    void cancel(ActionEvent event) {

	    }

	    @FXML
	    void transaction(ActionEvent event) {

	    }

	}

