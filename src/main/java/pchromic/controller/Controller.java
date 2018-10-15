package pchromic.controller;

import pchromic.domain.Order;
import pchromic.domain.Report;
import pchromic.domain.ReportBuilder;
import pchromic.exception.WrongOrderFormatException;
import pchromic.mapper.CsvOrderMapper;
import pchromic.mapper.XmlOrderMapper;
import pchromic.repository.OrderRepository;
import pchromic.service.OrderService;
import pchromic.service.ReportService;
import pchromic.validator.Impl.ValidatorImpl;
import pchromic.writer.CsvFileWriter;
import pchromic.writer.XmlFileWriter;
import javafx.collections.FXCollections;
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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Controller {

    @FXML private Button filterButton;
    @FXML private TextField filterField;
    @FXML private Label errorMessage;
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
    @FXML private Label wrongOrderFormat;


    @Autowired
    ValidatorImpl validator;
    @Autowired
    ReportService reportService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository repository;

    @Autowired
    CsvOrderMapper csvOrderMapper;


    private XmlOrderMapper xmlMapper;
    private CsvFileWriter csvWriter;
    private XmlFileWriter xmlWriter;
    private List<Order> orders = new ArrayList<>();
    private Report report;
    private String clientId;

    /**
     * Method responsible for creating instances of utilities needed to manage orders
     */
    @FXML
    private void initialize() {
        xmlMapper = new XmlOrderMapper();
        csvWriter = new CsvFileWriter();
        xmlWriter = new XmlFileWriter();
        validator = new ValidatorImpl();
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
        this.orders = new ArrayList<>();
        filterField.setText("");
        wrongOrderFormat.setText("");
        orderList.getItems().clear();
    }

    /**
     * Disables possibility to write reports to file
     */
    private void disableWriteToFile() {
        orderAmountReport.setDisable(true);
        ordersValueReport.setDisable(true);
        averageOrdersValueReport.setDisable(true);
    }

    /**
     * Sets label with information concerning error
     * @param log message describing error
     */
    private void setErrorLog(String log) {
        this.errorMessage.setText(log);
    }

    /**
     * Sets label with information concerning inappropriate order format
     */
    private void setWrongOrderLog(){
        this.wrongOrderFormat.setText("Order contains incorrect lines");
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
     * Gets buffered reader for given file
     * @param csvFile CSV file
     * @return returns buffered reader
     * @throws FileNotFoundException throws an exception when file cannot be found
     */
    private BufferedReader openFileToBuffReader(File csvFile) throws FileNotFoundException {
        return new BufferedReader(new FileReader(csvFile));
    }

    /**
     * Opens file / list of files and maps it / them to list of orders. Calls onFilter method
     * if it corresponding button was clicked
     * @param event pressing the button
     */
    @FXML void onOpenFile(ActionEvent event) {

        resetGui();
        FileChooser fileChooser = createFileChooser();
        List<File> file = fileChooser.showOpenMultipleDialog(new Stage());
        Charset charset = StandardCharsets.UTF_8;

        if (!Objects.isNull(file)) {
            for (File aFile : file) {

                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(
                            new InputStreamReader(new FileInputStream(aFile), charset));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    setErrorLog("Could not open the file");
                }

                String ext = getFileExtension(aFile.getName());

                switch (ext) {
                    default:
                        break;

                    case "csv":
                        try {
                            orderService.mapCsv(Objects.requireNonNull(bufferedReader));

                        } catch (IOException e) {//
                            e.printStackTrace();
                        } catch (WrongOrderFormatException e) {
                            setWrongOrderLog();
                        }
                        break;

                    case "xml":
                        this.orders.addAll(xmlMapper.map(aFile));

                        break;
                }
            }
            onFilter(event);
            enableGui();
        }
        else
            setErrorLog("File not chosen");
    }
    // -------------------------------------------------------------------------------------------------- //

    // ------------- TABLE AND REPORT MANAGEMENT ------------- //

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
        setErrorLog("");
        this.clientId = filterField.getText();
        boolean hasOrders = validator.customerHasOrders(repository.findAll(), clientId);
        if(hasOrders){
            this.report = reportService.setOrderReports(clientId);
            setOrderReports(report);
            ObservableList<Order> orders = orderService.setOrderTableContent(clientId);
            orderList.setItems(orders);
        }
        else {
            resetGui();
            disableWriteToFile();
            setErrorLog("Client does not exist");
        }

    }
    // -------------------------------------------------------------------------------------------------- //


    // ------------- WRITING SPECIFIC REPORT TO FILE ------------- //

    /**
     * Writes data to XML and CSV files data concerning average orders value
     * @param event pressing the button
     */
    @FXML
    void onAverageOrdersValue(ActionEvent event) {

        if (this.clientId.isEmpty()) {
            this.report = ReportBuilder.aReport()
                    .withOrdersAvgValue(reportService.getAverageValueOfOrder()
                            .toString())
                    .build();
        }
        else
            this.report = ReportBuilder.aReport()
                    .withOrdersAvgValue(reportService.getAverageValueOfOrderForCustomer(this.clientId)
                            .toString())
                    .build();
        csvWriter.writeCsv(this.report);
        xmlWriter.writeXml(this.report);

    }

    /**
     * Writes data to XML and CSV files data concerning orders amount
     * @param event pressing the button
     */
    @FXML
    void onOrderAmountReport(ActionEvent event) {

        if (this.clientId.isEmpty()) {
            this.report = ReportBuilder.aReport()
                    .withOrdersAmount(reportService.getTotalAmountOfOrders()
                            .toString())
                    .build();
        }
        else
            this.report = ReportBuilder.aReport()
                    .withOrdersAmount(reportService.getTotalAmountOfOrdersForCustomer(this.clientId)
                            .toString())
                    .build();
        csvWriter.writeCsv(this.report);
        xmlWriter.writeXml(this.report);

    }

    /**
     * Writes data to XML and CSV files data concerning orders value
     * @param event pressing the button
     */
    @FXML
    void onValueOfOrders(ActionEvent event) {

        if (this.clientId.isEmpty()) {
            this.report = ReportBuilder.aReport()
                    .withOrdersValue(reportService.getTotalOrdersValue()
                            .toString())
                    .build();
        }
        else
            this.report = ReportBuilder.aReport()
                    .withOrdersValue(reportService.getTotalOrdersValueForCustomer(this.clientId)
                            .toString())
                    .build();
        csvWriter.writeCsv(this.report);
        xmlWriter.writeXml(this.report);
    }
    // -------------------------------------------------------------------------------------------------- //

}