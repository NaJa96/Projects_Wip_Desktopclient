package bertelsbank.clientview;

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
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bertelsbank.transaction.Account;
import bertelsbank.transaction.TransactionController;
import de.fhdw.javafx.desktopclient.ServerAccess;
import de.fhdw.javafx.desktopclient.TableRow;
import de.fhdw.javafx.desktopclient.Transaction;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
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
	private TableView<TableRow> tableTransaction;

	@FXML
	private TableColumn<TableRow, String> tabDate;

	@FXML
	private TableColumn<TableRow, String> tabSenderReceiver;

	@FXML
	private TableColumn<TableRow, String> tabAccNumber;

	@FXML
	private TableColumn<TableRow, BigDecimal> tabAmount;

	@FXML
	private TableColumn<TableRow, String> tabReference;

	@FXML
	private Button btnTransactionView;
	
    @FXML
    private Button btnRefresh;
    
    @FXML
    private Text txtError;

	/**
	 * Initializing the current account and calling the methods for creating the table and the welcome text
	 * @author NadinJanﬂen
	 */
	@FXML
	public void initialize() {
		currentAccount = ServerAccess.getAccount();
		setSalutationText();
		fillTable();
	}

	/**
	 * open the transaction overview
	 * @param event
	 * @author NadinJanﬂen
	 */
	@FXML
	void openTransactionViewBtnAction(ActionEvent event) {

		try {
			Stage stage;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/bertelsbank/transaction/transaction.fxml"));
			Parent root = loader.<Parent>load();
			TransactionController controller = loader.<TransactionController>getController();
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Call up the refresh function when pressing the refresh button
	 * @param event
	 * @author NadinJanﬂen
	 */
	@FXML
    void refreshBtnAction(ActionEvent event) {
		refresh();
    }

	/**
	 * Update the account by server query and calling the methods for creating the table and the welcome text
	 * @author NadinJanﬂen
	 */
	public void refresh(){
		currentAccount = refreshAccount();
		setSalutationText();
		fillTable();
	}
	
    /**
     * creating the welcome text
     * @author NadinJanﬂen
     */
    protected void setSalutationText(){
		String now = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
		now = now.substring(11, 13);
		int intNow = Integer.parseInt(now);

		if (intNow <= 12) {
			txtSalutation.setText("Guten Morgen, " + currentAccount.getOwner());
		} else if (intNow <= 17) {
			txtSalutation.setText("Guten Tag, " + currentAccount.getOwner());
		} else {
			txtSalutation.setText("Guten Abend, " + currentAccount.getOwner());
		}
    }
    
    /**
     * creating the table of transactions
     * @author NadinJanﬂen
     */
    protected void fillTable(){
		BigDecimal accountBalance = new BigDecimal(0);
		
		tabDate.setCellValueFactory(new PropertyValueFactory<TableRow, String>("transactionDate"));
		tabSenderReceiver.setCellValueFactory(new PropertyValueFactory<TableRow, String>("senderReceiver"));
		tabAccNumber.setCellValueFactory(new PropertyValueFactory<TableRow, String>("accountNumber"));
		tabAmount.setCellValueFactory(new PropertyValueFactory<TableRow, BigDecimal>("amount"));
		tabReference.setCellValueFactory(new PropertyValueFactory<TableRow, String>("reference"));

		List<TableRow> tableRows = new ArrayList<TableRow>();
		List<Transaction> transactions = currentAccount.getTransactions();
		for (Transaction transaction : transactions) {
			TableRow tableRow = new TableRow();
			tableRow.setTransactionDate(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(transaction.getTransactionDate()));
			//tableRow.setTransactionDate(transactionDate);
			if (transaction.getSender().getNumber().equals(currentAccount.getNumber())) {
				tableRow.setSenderReceiver(transaction.getReceiver().getOwner());
				tableRow.setAccountNumber(transaction.getReceiver().getNumber());
				accountBalance = accountBalance.subtract(transaction.getAmount());
			    BigDecimal negative = new BigDecimal(-1);
			    tableRow.setAmount(transaction.getAmount().multiply(negative));
			    
			} else {
				tableRow.setSenderReceiver(transaction.getSender().getOwner());
				tableRow.setAccountNumber(transaction.getSender().getNumber());
				accountBalance = accountBalance.add(transaction.getAmount());
				tableRow.setAmount(transaction.getAmount());
			}
			
			
			tableRow.setReferenceString(transaction.getReference());
			tableRows.add(tableRow);
			
			String accountBalanceAsString = accountBalance.toString();
			txtAccountBalance.setText(accountBalanceAsString + " Ä");
			
			serverAccess.setAccountBalance(accountBalance);
			
			//ToDo: Spalten pr¸fen 
		}

		ObservableList<TableRow> data = FXCollections.observableList(tableRows);
		tableTransaction.setItems(data);
    }
    
    /**
     * Updating the account by server query and returning the data as an account (id, owner, number and list of transactions)
     * @return Account
     * @author NadinJanﬂen
     */
    protected Account refreshAccount(){
		try {
			HttpResponse response = serverAccess.getAccountResponse(currentAccount.getNumber());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String accountNumber = EntityUtils.toString(response.getEntity());
				Gson gson = new GsonBuilder().create();
				Account account = gson.fromJson(accountNumber, Account.class);;
				ServerAccess.setAccount(account);
				txtError.setText("");
				return account;
			}else{
				txtError.setText(EntityUtils.toString(response.getEntity()) + " (Fehler: " + response.getStatusLine().getStatusCode() + ")");
			}
		} catch (IOException e) {
			e.printStackTrace();
			txtError.setText("Server nicht verf¸gbar");
		}
		return null;
    }

}
