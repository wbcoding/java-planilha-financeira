package com.wbcoding.main.Home;

import com.wbcoding.main.User.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class HomeController {

    @FXML
    private Label dateLabel, chequingLabel, savingsLabel, creditCardLabel, moneyLabel, totalLabel, newDataMsg;
    @FXML
    private TableView<TableModel> table;
    @FXML
    private TableColumn<TableModel, String> descriptionCol, accountCol, dateCol;
    @FXML
    private TableColumn<TableModel, Double> valueCol;
    @FXML
    private ComboBox<String> newAccount;
    @FXML
    private DatePicker newDate;
    @FXML
    private TextField newDescription, newValue;
    @FXML
    private RadioButton newDebit, newCredit;
    @FXML
    private Button deleteBtn;
    @FXML
    private ImageView deleteImg;

    private int userId = UserModel.getId();

    private TableDAO tableDAO;


    public void initialize() {
        initComboBox();
        loadPlaceholderAndTopDate();
        setupTableColumns();
        loadSidebarValues();
        currentMonth();
        checkNumberTyping(newValue);
        loadDebitOrCreditSelected();
        hideDeleteBtn();
    }


    // show the values for the current month on the table
    private void currentMonth() {
        table.setItems(tableDAO.getCurrentMonthData(userId));
    }


    @FXML  // show the values for the next month on the table
    private void nextMonth() {
        dateLabel.setText(DateModel.getNextMonth(dateLabel.getText()));
        table.setItems(tableDAO.getPreviousNextMonthData(dateLabel.getText(), userId));
        hideDeleteBtn();
    }

    @FXML //  show the values for the previous month on the table
    private void previousMonth() {
        dateLabel.setText(DateModel.getPreviousMonth(dateLabel.getText()));
        table.setItems(tableDAO.getPreviousNextMonthData(dateLabel.getText(), userId));
        hideDeleteBtn();
    }

    @FXML // insert new data on the table
    private void insertNewData() {
        String description = newDescription.getText();
        String value = newValue.getText();
        String date = DateModel.formatDateFromDatePicker(newDate.getValue());
        String operation = newDebit.isSelected() ? "-" : "+";
        String account = newAccount.getValue();
        String msg = tableDAO.insertNewData(userId, description, value, date, operation, account);

        newDataMsg.setText(msg);

        if (msg.equals("Dados inseridos com sucesso.")) {
            newDescription.setText("");
            newValue.setText("");
            newDebit.setSelected(true);
            newAccount.getSelectionModel().select("corrente");
            loadSidebarValues();
            if (date != null) dateLabel.setText(DateModel.getMonthFordataAdded(date));
            table.setItems(tableDAO.showMonthWithDataAdded(date, userId));
        }

        hideDeleteBtn();
    }

    // clean message when typing description or clicked on table
    @FXML
    private void cleanMsgNewData() {

        newDataMsg.setText("");
        hideDeleteBtn();
    }

    // RadioButton setup
    private void loadDebitOrCreditSelected() {
        ToggleGroup group = new ToggleGroup();
        newDebit.setToggleGroup(group);
        newDebit.setSelected(true);
        newCredit.setToggleGroup(group);
    }


    // setup table columns
    private void setupTableColumns() {
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        accountCol.setCellValueFactory(new PropertyValueFactory<>("account"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        accountCol.setStyle("-fx-alignment: CENTER;");
        dateCol.setStyle("-fx-alignment: CENTER;");
        valueCol.setStyle("-fx-alignment: CENTER;");
    }

    // no itens on the table
    private void loadPlaceholderAndTopDate() {
        dateLabel.setText(DateModel.getCurrentDate());
        table.setPlaceholder(new Label("Não há itens neste mês."));
    }

    // setup comboBox
    private void initComboBox() {
        newAccount.getItems().removeAll(newAccount.getItems());
        newAccount.getItems().addAll("corrente", "poupança", "cartão de crédito", "dinheiro");
        newAccount.getSelectionModel().select("corrente");
    }


    // load initial values on the sidebar
    private void loadSidebarValues() {
        tableDAO = new TableDAO();
        ArrayList<String> data = tableDAO.showValuesByAccount(userId);
        chequingLabel.setText(data.get(0));
        savingsLabel.setText(data.get(1));
        creditCardLabel.setText(data.get(2));
        moneyLabel.setText(data.get(3));
        totalLabel.setText(data.get(4));
    }

    // test if the user is typing a number in the field value
    private void checkNumberTyping(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                textField.setText(oldValue);
            }
        });
    }

    @FXML // delete row on the table
    private void deleteSelectedRow() {
        try {
            if (tableDAO.deleteTableRow(table.getSelectionModel().getSelectedItem().getId())) {
                table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
                loadSidebarValues();
            }
        } catch (Exception e) {
            System.out.println("Nenhuma linha selecionada");
        }

    }

    @FXML
    private void showDeleteBtn() {
        try {
            int num = table.getSelectionModel().getSelectedItem().getId();
            if (num != 0) {
                deleteBtn.setVisible(true);
                deleteImg.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    @FXML
    private void hideDeleteBtn() {
        deleteBtn.setVisible(false);
        deleteImg.setVisible(false);

    }


}
