package pchromic.controller;

import pchromic.domain.Order;
import pchromic.domain.Report;
import pchromic.domain.ReportBuilder;
import pchromic.enums.ReportType;
import pchromic.mapper.XmlOrderMapper;
import pchromic.service.OrderService;
import pchromic.service.ReportService;
import pchromic.validator.Impl.ValidatorImpl;
import pchromic.writer.CsvFileWriter;
import pchromic.writer.XmlFileWriter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controller responsible for GUI layout and input/output management
 */
@Component
public class Controller {

    @FXML private Button filterButton;
    @FXML private TextField filterField;
    @FXML private TableView<Order> orderList;
    @FXML private TableColumn<Order, String> clientIdCol;
    @FXML private TableColumn<Order, Long> requestIdCol;
    @FXML private TableColumn<Order, String> nameCol;
    @FXML private TableColumn<Order, Integer> quantityCol;
    @FXML private TableColumn<Order, Double> priceCol;
    @FXML private Label ordersAmount;
    @FXML private Label ordersValue;
    @FXML private Label ordersAvgValue;
    @FXML private Button orderAmountReport;
    @FXML private Button ordersValueReport;
    @FXML private Button averageOrdersValueReport;
    @FXML private TextArea errorMessages;


    @Autowired
    private
    ValidatorImpl validator;
    @Autowired
    private
    ReportService reportService;
    @Autowired
    private
    OrderService orderService;

    private XmlOrderMapper xmlMapper;
    private CsvFileWriter csvWriter;
    private XmlFileWriter xmlWriter;
    private Report report;
    private String clientId;
    private List<String> logList = new ArrayList<>();

    /**
     * Method responsible for creating instances of utilities needed to manage orders
     */
    @FXML
    private void initialize() {
        xmlMapper = new XmlOrderMapper();
        csvWriter = new CsvFileWriter();
        xmlWriter = new XmlFileWriter();
        setOrderTableColumns();
    }

    // ------------- GUI LAYOUT MANAGEMENT ------------- //


    /**
     * Enables graphic user interface to users
     */
    private void enableGui() {
        filterField.setDisable(false);
        filterButton.setDisable(false);
        orderAmountReport.setDisable(false);
        ordersValueReport.setDisable(false);
        averageOrdersValueReport.setDisable(false);
    }

    /**
     * Resets states and contents of GUI
     */
    private void resetGui() {
        clearOrderReports();
        filterField.setText("");
        errorMessages.clear();
        orderList.getItems().clear();
    }

    /**
     * Disables write to file buttons
     */
    private void disableWriteToFile() {
        orderAmountReport.setDisable(true);
        ordersValueReport.setDisable(true);
        averageOrdersValueReport.setDisable(true);
    }


    // -------------------------------------------------------------------------------------------------- //

    // ------------- FILE INPUT MANAGEMENT ------------- //

    /**
     * Creates File Chooser dialog with specific file types option
     * @return returns instance of file chooser
     */
    private FileChooser createFileChooser(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter csvFilter =
                new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        FileChooser.ExtensionFilter xmlFilter =
                new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(csvFilter);
        fileChooser.getExtensionFilters().add(xmlFilter);

        fileChooser.setTitle("Select CSV or XML files");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        return fileChooser;
    }

    /**
     * Gets file extension from open file
     * @param fileName name of opened file
     * @return returns string containing file extension
     */
    private String getFileExtension(String fileName) {
        String[] split = fileName.split("\\.");
        return split[split.length-1];
    }


    /**
     * Opens file(s) and maps to list of orders. Calls onFilter method
     * if it corresponding button was clicked
     * @param event pressing the OpenFile button
     */
    @FXML void onOpenFile(ActionEvent event) {

        resetGui();
        FileChooser fileChooser = createFileChooser();
        List<File> file = fileChooser.showOpenMultipleDialog(new Stage());


        if (!Objects.isNull(file)) {
            for (File aFile : file) {

                String ext = getFileExtension(aFile.getName());

                switch (ext) {
                    default:
                        break;

                    case "csv":
                        try {
                        logList = orderService.mapCsv(Objects.requireNonNull(aFile));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "xml":
                        List<Order> xmlOrders = xmlMapper.map(aFile);
                        for (Order order : xmlOrders)
                            orderService.addOrder(order);

                        break;
                }
            }
            onFilter(event);
        }
        else
            logList.add("File not chosen \n");
        setLogMessages();
    }
    // -------------------------------------------------------------------------------------------------- //

    // ------------- GUI CONTENT MANAGEMENT ------------- //

    /**
     * Views messages in text box on GUI
     */
    private void setLogMessages() {
        for (String log: logList) {
            errorMessages.setWrapText(true);
            errorMessages.appendText(log);
        }
        logList.clear();
    }

    /**
     * Creates headers and data types for rows of table showing list of orders
     */
    private void setOrderTableColumns() {
        clientIdCol.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        requestIdCol.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    /**
     * Set report values empty
     */
    private void clearOrderReports(){
        ordersAmount.setText("");
        ordersValue.setText("");
        ordersAvgValue.setText("");
    }

    /**
     * Sets report values
     * @param report Report concerning orders
     */
    private void setOrderReports (Report report){
        this.ordersAmount.setText(report.getOrdersAmount());
        this.ordersValue.setText(report.getOrdersValue());
        this.ordersAvgValue.setText(report.getOrdersAvgValue());
    }

    /**
     * Adjusts content of order reports and order table. Triggered by click on Filter button,
     * it displays data concerning client of id given in text input.
     * @param event pressing the button
     */
    @FXML void onFilter(ActionEvent event) {
      //  setErrorLog("");
        this.clientId = filterField.getText();
        boolean hasOrders = validator.clientHasOrders(orderService.getAllOrders(), clientId);
        if(hasOrders){
                this.report = reportService.generateReports(clientId);
            setOrderReports(report);
            ObservableList<Order> orders = orderService.setOrderTableContent(clientId);
            orderList.setItems(orders);
            enableGui();
        }
        else {
            resetGui();
            disableWriteToFile();
            logList.add("Client does not exist \n");
            setLogMessages();
        }

    }
    // -------------------------------------------------------------------------------------------------- //


    // ------------- WRITING SPECIFIC REPORT TO FILE ------------- //

    /**
     * Create both CSV and XML files containing report passed as parameter
     * @param report report to be saved
     */
    private void saveReportToFile (Report report){
        boolean isCsvSaved = csvWriter.writeCsv(this.report);
        boolean isXmlSaved = xmlWriter.writeXml(this.report);

        if (isCsvSaved && isXmlSaved)
            logList.add("File saved \n");

        setLogMessages();
    }
    /**
     * Writes report containing average value of orders to XML and CSV files
     * @param event pressing the button
     */
    @FXML
    void onAverageOrdersValue(ActionEvent event) {

        if (this.clientId.isEmpty()) {
            this.report = ReportBuilder.aReport()
                    .withOrdersAvgValue(
                            orderService.getAverageValueOfOrder().toString()
                    )
                    .withReportType(ReportType.AVERAGE_VALUE)
                    .build();
        }
        else
            this.report = ReportBuilder.aReport()
                    .withOrdersAvgValue(
                            orderService.getAverageValueOfOrderForClient(this.clientId).toString()
                    )
                    .withReportType(ReportType.AVERAGE_VALUE)
                    .build();
        saveReportToFile(this.report);
    }

    /**
     * Writes report containing orders amount to XML and CSV files
     * @param event pressing the button
     */
    @FXML
    void onOrderAmountReport(ActionEvent event) {

        if (this.clientId.isEmpty()) {
            this.report = ReportBuilder.aReport()
                    .withOrdersAmount(
                            orderService.getTotalAmountOfOrders().toString()
                    )
                    .withReportType(ReportType.AMOUNT)
                    .build();
        }
        else
            this.report = ReportBuilder.aReport()
                    .withOrdersAmount(
                            orderService.getTotalAmountOfOrdersForClient(this.clientId).toString()
                    )
                    .withReportType(ReportType.AMOUNT)
                    .build();
        saveReportToFile(this.report);

    }

    /**
     * Writes report containing orders value to XML and CSV files
     * @param event pressing the button
     */
    @FXML
    void onValueOfOrders(ActionEvent event) {

        if (this.clientId.isEmpty()) {
            this.report = ReportBuilder.aReport()
                    .withOrdersValue(
                            orderService.getTotalOrdersValue().toString()
                    )
                    .withReportType(ReportType.TOTAL_VALUE)
                    .build();
        }
        else
            this.report = ReportBuilder.aReport()
                    .withOrdersValue(
                            orderService.getTotalOrdersValueForClient(this.clientId).toString()
                    )
                    .withReportType(ReportType.TOTAL_VALUE)
                    .build();
        saveReportToFile(this.report);

    }
    // -------------------------------------------------------------------------------------------------- //

}