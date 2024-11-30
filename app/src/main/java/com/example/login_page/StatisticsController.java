package com.example.login_page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {
    // Variables
    @FXML
    private Label revenueLabel;
    @FXML
    private Label thisYearRevenueLabel;
    @FXML
    private Label lastYearRevenueLabel;
    @FXML
    private PieChart pieChart;
    @FXML
    private ImageView arrow;
    @FXML
    private TableView<RoomV2> tableView;
    @FXML
    private TableColumn<RoomV2,String> typeColumn;
    @FXML
    private TableColumn<RoomV2,String> incomeColumn;
    @FXML
    private Label netGainLabel;
    @FXML
    private Label expensesLabel;
    @FXML
    private AreaChart<String , Number> areaChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private BarChart<String,Integer> barChart;
    @FXML
    private Label numberOfEmployeesLabel;
    @FXML
    private Label numberOfClientsThisMonth;
    @FXML
    private Label numberOfClientsLastMonth;

    Model model = new Model();

    // Methods
    public void displayRevenue() {
        String number = model.getRevenueChange();
        Double amount = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        revenueLabel.setText(formatter.format(amount) + "%");
        if (Float.parseFloat(model.getRevenueChange()) < 0) {
            arrow.setImage(new Image("down.png"));
        } else arrow.setImage(new Image("up.png"));
        number = model.getCurrentYearRevenue();
        amount = Double.parseDouble(number);
        formatter = new DecimalFormat("#,###");
        thisYearRevenueLabel.setText(formatter.format(amount) + " DA");
        number = model.getLastYearRevenue();
        amount = Double.parseDouble(number);
        lastYearRevenueLabel.setText(formatter.format(amount) + " DA");
    }
    public void displayExpensePieChart() {
        ArrayList<ExpenseType> arrayList = model.getExpensesTypes();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (ExpenseType expenseType : arrayList) {
            pieChartData.add(new PieChart.Data(expenseType.getType(), model.getCostByExpenseType(expenseType.getNum())));
        }
        pieChartData.add(new PieChart.Data("Salary Payments", model.getTotalSalaryPaymentsThisYear()));
        pieChart.getData().addAll(pieChartData);
    }
    public void displayRevenueByCategory() {
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        incomeColumn.setCellValueFactory(new PropertyValueFactory<>("income"));
        ObservableList<RoomV2> temp = FXCollections.observableArrayList(model.getRevenueByCategory());
        tableView.setItems(temp);
    }
    public void displayExpenses() {
        String number = model.getCurrentYearExpenses();
        Double amount = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("#,###");
        expensesLabel.setText(formatter.format(amount) + " DA");
    }
    public void displayNetGain() {
        String number = Integer.toString(Integer.parseInt(model.getCurrentYearRevenue())-Integer.parseInt(model.getCurrentYearExpenses()));
        Double amount = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("#,###");
        netGainLabel.setText(formatter.format(amount) + " DA");
    }
    public void displayChart() {
        xAxis = new CategoryAxis();
        xAxis.setLabel("Months");
        yAxis = new NumberAxis();
        yAxis.setLabel("Amount in DA");
        ObservableList<XYChart.Data<String, Number>> revenueData = FXCollections.observableArrayList(
                new XYChart.Data<>("January", model.getRevenueByMonth("01")),
                new XYChart.Data<>("February", model.getRevenueByMonth("02")),
                new XYChart.Data<>("March", model.getRevenueByMonth("03")),
                new XYChart.Data<>("April", model.getRevenueByMonth("04")),
                new XYChart.Data<>("May", model.getRevenueByMonth("05")),
                new XYChart.Data<>("June", model.getRevenueByMonth("06")),
                new XYChart.Data<>("July", model.getRevenueByMonth("07")),
                new XYChart.Data<>("August", model.getRevenueByMonth("08")),
                new XYChart.Data<>("September", model.getRevenueByMonth("09")),
                new XYChart.Data<>("October", model.getRevenueByMonth("10")),
                new XYChart.Data<>("November", model.getRevenueByMonth("11")),
                new XYChart.Data<>("December", model.getRevenueByMonth("12"))
        );
        XYChart.Series<String, Number> revenueSeries = new XYChart.Series<>();
        revenueSeries.setName("Monetary Revenue Amount");
        revenueSeries.setData(revenueData);
        areaChart.getData().add(revenueSeries);
        ObservableList<XYChart.Data<String, Number>> expensesData = FXCollections.observableArrayList(
                new XYChart.Data<>("January", model.getExpenseByMonth("01")),
                new XYChart.Data<>("February", model.getExpenseByMonth("02")),
                new XYChart.Data<>("March", model.getExpenseByMonth("03")),
                new XYChart.Data<>("April", model.getExpenseByMonth("04")),
                new XYChart.Data<>("May", model.getExpenseByMonth("05")),
                new XYChart.Data<>("June", model.getExpenseByMonth("06")),
                new XYChart.Data<>("July", model.getExpenseByMonth("07")),
                new XYChart.Data<>("August", model.getExpenseByMonth("08")),
                new XYChart.Data<>("September", model.getExpenseByMonth("09")),
                new XYChart.Data<>("October", model.getExpenseByMonth("10")),
                new XYChart.Data<>("November", model.getExpenseByMonth("11")),
                new XYChart.Data<>("December", model.getExpenseByMonth("12"))
        );
        XYChart.Series<String, Number> expensesSeries = new XYChart.Series<>();
        expensesSeries.setName("Monetary Expenses Amount");
        expensesSeries.setData(expensesData);
        areaChart.getData().add(expensesSeries);
    }
    public void displayBarChart() {
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        ArrayList<ExpenseType> arrayList = model.getExpensesTypes();
        series.setName("expenses by type");
        for (ExpenseType expenseType : arrayList) {
            series.getData().add(new XYChart.Data<>(expenseType.getType(), model.getCostByExpenseType(expenseType.getNum())));
        }
        series.getData().add(new XYChart.Data<>("Salary Payments", model.getTotalSalaryPaymentsThisYear()));
        barChart.getData().add(series);
    }
    public void displayNumberOfEmployees() {
        numberOfEmployeesLabel.setText(model.getNumberOfEmployees());
    }
    public void displayNumberOfClientsThisMonth() {
        numberOfClientsThisMonth.setText(model.getNumberOfClientsThisMonth());
    }
    public void displayNumberOfClientsLastMonth() {
        numberOfClientsLastMonth.setText(model.getNumberOfClientsLastMonth());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayRevenue();
        displayRevenueByCategory();
        displayExpenses();
        displayNetGain();
        displayChart();
        updateSalaryEveryMonth();
        displayExpensePieChart();
        displayBarChart();
        displayNumberOfEmployees();
        displayNumberOfClientsThisMonth();
        displayNumberOfClientsLastMonth();
    }
    public void updateSalaryEveryMonth() {
        Staff staff;
        ArrayList<Staff> arrayList;
        arrayList = model.getStaff();
        LocalDate localDate = java.time.LocalDate.now();
        for (Staff value : arrayList) {
            staff = value;
            if (staff.getLastTimePayed() == null) {
                staff.setLastTimePayed(localDate);
                model.updateLastTimePayed(staff);
                continue;
            }
            if (!((localDate.getYear() == staff.getLastTimePayed().getYear()) && (localDate.getMonth() == staff.getLastTimePayed().getMonth()))) {
                staff.setLastTimePayed(localDate);
                model.updateLastTimePayed(staff);
                model.addToSalaryPayment(staff.getSalary(),localDate);
            }
        }
    }
}