package neverstop.manager.main.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import neverstop.manager.adaptor.requester.GatewayRequester;
import neverstop.manager.entity.sensor.DeviceState;
import neverstop.manager.entity.sensor.PowerBalance;
import neverstop.manager.entity.sensor.Sensor;
import neverstop.manager.main.model.SensorModel;

/**
 * MainController
 *
 * @author @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 08/09/2018
 */
public class MainController {

    @FXML private TableView<SensorModel> managedSensorTableView;
    @FXML private TableColumn<SensorModel, String> deviceIdTableColumn;
    @FXML private TableColumn<SensorModel, DeviceState> deviceStateTableColumn;
    @FXML private TableColumn<SensorModel, PowerBalance> powerBalanceTableColumn;
    @FXML private TableColumn<SensorModel, Integer> cpuTableColumn;
    @FXML private TableColumn<SensorModel, Integer> memoryTableColumn;
    @FXML private TableColumn<SensorModel, String> responseTimeTableColumn;

    @FXML private TextField deviceIdTextField;
    @FXML private Text deviceStateText;
    @FXML private TextField powerBalanceTextField;
    @FXML private TextField cpuTextField;
    @FXML private TextField memoryTextField;
    @FXML private TextField rowDataTextField;

    @FXML private Button checkButton;

    private Properties properties;

    private SensorModel sensorModel;
    private ObservableList<SensorModel> sensorModels;

    private GatewayRequester requester;

    public MainController() {
        //
        this.sensorModels = FXCollections.observableArrayList();
    }

    public void initialize() {
        //
        initControls();
        bindEvents();
//        addSampleData();
    }

    private void initControls() {
        //
        managedSensorTableView.setItems(sensorModels);
        deviceIdTableColumn.setCellValueFactory(new PropertyValueFactory<SensorModel, String>("deviceId"));
        deviceStateTableColumn.setCellValueFactory(new PropertyValueFactory<SensorModel, DeviceState>("deviceState"));
        powerBalanceTableColumn.setCellValueFactory(new PropertyValueFactory<SensorModel, PowerBalance>("powerBalance"));
        cpuTableColumn.setCellValueFactory(new PropertyValueFactory<SensorModel, Integer>("cpuUsage"));
        memoryTableColumn.setCellValueFactory(new PropertyValueFactory<SensorModel, Integer>("memoryUsage"));
        responseTimeTableColumn.setCellValueFactory(new PropertyValueFactory<SensorModel, String>("responseTimestamp"));

        sensorModel = new SensorModel();
        deviceIdTextField.textProperty().bindBidirectional(sensorModel.deviceIdProperty());
    }

    private void bindEvents() {
        //
        managedSensorTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                sensorModel.setValues(newVal);
                drawDetailInfromation();
            }
        });

        checkButton.setOnAction(event -> {
            for (SensorModel sensorModel : sensorModels) {
                Sensor sensor = requester.checkSensor(sensorModel.deviceIdProperty().get());
                sensorModel.setValues(sensor);
            }
        });

    }

    private void addSampleData() {
        //
        sensorModels.clear();

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            SensorModel model = new SensorModel();
            int randomValue = random.nextInt(10000);
            model.deviceIdProperty().set(String.format("00%02X%n", randomValue));
            model.deviceStateProperty().set(DeviceState.Connected);
            PowerBalance powerBalance = PowerBalance.HalfFull;
            if ((i + random.nextInt()) % 2 == 0) {
                powerBalance = PowerBalance.Full;
            }
            model.powerBalanceProperty().set(powerBalance);
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            model.responseTimestampProperty().set(format.format(now));
            model.cpuUsageProperty().set(random.nextInt(10));
            model.memoryUsageProperty().set(random.nextInt(10));
            sensorModels.add(model);
        }

    }

    private void drawDetailInfromation() {
        deviceStateText.setText(sensorModel.deviceStateProperty().getValue().name());
        powerBalanceTextField.setText(sensorModel.powerBalanceProperty().getValue().name());
        cpuTextField.textProperty().set(String.valueOf(sensorModel.cpuUsageProperty().get()));
        memoryTextField.textProperty().set(String.valueOf(sensorModel.memoryUsageProperty().get()));
    }
}