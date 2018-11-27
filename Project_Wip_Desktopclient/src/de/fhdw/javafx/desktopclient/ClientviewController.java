package de.fhdw.javafx.desktopclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

public class ClientviewController {
	
		ServerAccess serverAccess = new ServerAccess();
		Account currentAccount;


		    @FXML
		    private Text txtSalutation;

		    @FXML
		    private Text txtAccountBalance;

		    @FXML
		    private ImageView imageLogo;

		    @FXML
		    private TableView<Transaction> tableTransaction;
		    
		    @FXML
		    private TableColumn<Transaction, Date> tabDate;

		    @FXML
		    private TableColumn<Transaction, String> tabSenderReceiver;

		    @FXML
		    private TableColumn<Transaction, Integer> tabAccNumber;

		    @FXML
		    private TableColumn<Transaction, Integer> tabAmount;

		    @FXML
		    private TableColumn<Transaction, String> tabReference;

		    @FXML
		    private Button btnTransaction;
	
	    
	    @FXML
	    public void initialize(){
	    	
	    	currentAccount = ServerAccess.getAccount();
	    	
	    	String now = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
	    	now = now.substring(11,13);
	    	System.out.println(now);
	    	int intNow = Integer.parseInt(now);
	    	
	    	if(intNow<=12){
	    		txtSalutation.setText("Guten Morgen, " + currentAccount.getOwner());
	    	}else if(intNow<=17){
	    		txtSalutation.setText("Guten Tag, " + currentAccount.getOwner());
	    	}else{
	    		txtSalutation.setText("Guten Abend, " + currentAccount.getOwner());
	    	}
	    
	    	txtAccountBalance.setText(calcBalance());
	    	
	    	tabReference.setCellValueFactory(new PropertyValueFactory<Transaction, String>("reference"));
	    	tabSenderReceiver.setCellValueFactory(new PropertyValueFactory<Transaction, String>(""));
	    	ObservableList data = FXCollections.observableList(currentAccount.getTransactions());
	    	tableTransaction.setItems(data);
	    	
	    	
	    	
	    }
	    //currentAccount.getTransactions().get(1).getTransactionDate()

	    @FXML
	    void openTransactionView(ActionEvent event) {
	    	
	    try{
	    	Stage stage;
   			FXMLLoader loader = new FXMLLoader(getClass().getResource("transaction.fxml"));
   			Parent root = loader.<Parent>load();
   			TransactionController controller =  loader.<TransactionController>getController();
   			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
	    } catch (IOException e) {
			e.printStackTrace();
		}

	    }
	    
	    private String calcBalance(){
	    	List<Transaction> transactions = currentAccount.getTransactions();
	    	for(Transaction element : transactions)
	    	{
	    		System.out.println(element.getAmount());
	    	}    	
			return null;
	    }

	}
