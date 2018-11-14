package de.fhdw.javafx.desktopclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SampleController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnSub;

    @FXML
    private Button btnMul;

    @FXML
    private Button btnDiv;

    @FXML
    private Button btnClear;

    @FXML
    private Label lblResult;

    @FXML
    private TextField txtNum1;

    @FXML
    private TextField txtNum2;

    @FXML
    void handleButtonAction(ActionEvent event) {
    	double num1 = Double.parseDouble(txtNum1.getText());
    	double num2 = Double.parseDouble(txtNum2.getText());
    	double result = 0;

    	if(event.getSource().equals(btnAdd)) {
    		result = num1 + num2;
    	}
    	else if(event.getSource().equals(btnSub)) {
    		result = num1 - num2;
    	}
    	else if(event.getSource().equals(btnMul)) {
    		result = num1 * num2;
    	}
    	else if(event.getSource().equals(btnDiv)) {
    		result = num1 / num2;
    	}
    	else if(event.getSource().equals(btnClear)) {
    		txtNum1.clear();
    		txtNum2.clear();
    		txtNum1.requestFocus();
    	}
    	lblResult.setText(String.valueOf(result));
    }























}
