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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private Button btnTransaction;

	@FXML
	public void initialize() {

		currentAccount = ServerAccess.getAccount();

		String now = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
		now = now.substring(11, 13);
		System.out.println(now);
		int intNow = Integer.parseInt(now);

		if (intNow <= 12) {
			txtSalutation.setText("Guten Morgen, " + currentAccount.getOwner());
		} else if (intNow <= 17) {
			txtSalutation.setText("Guten Tag, " + currentAccount.getOwner());
		} else {
			txtSalutation.setText("Guten Abend, " + currentAccount.getOwner());
		}

		txtAccountBalance.setText(calcBalance());

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
			if (transaction.getSender().getNumber().equals(currentAccount.getNumber())) {
				tableRow.setSenderReceiver(transaction.getReceiver().getOwner());
				tableRow.setAccountNumber(transaction.getReceiver().getNumber());
			} else {
				tableRow.setSenderReceiver(transaction.getSender().getOwner());
				tableRow.setAccountNumber(transaction.getSender().getNumber());
			}
			tableRow.setAmount(transaction.getAmount());
			tableRow.setReferenceString(transaction.getReference());
			tableRows.add(tableRow);

			//ToDo: Spalte "Datum" breiter machen
		}

		ObservableList<TableRow> data = FXCollections.observableList(tableRows);
		tableTransaction.setItems(data);

	}

	@FXML
	void openTransactionView(ActionEvent event) {

		try {
			Stage stage;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("transaction.fxml"));
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

	private String calcBalance() {
		List<Transaction> transactions = currentAccount.getTransactions();
		for (Transaction element : transactions) {
			System.out.println(element.getAmount());
		}
		return null;
	}

}
