package neverstop.manager.main.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import neverstop.manager.adaptor.requester.GatewaySNMPRequester;
import neverstop.manager.entity.sensor.Device;
import neverstop.manager.entity.sensor.DeviceState;
import neverstop.manager.entity.sensor.PowerBalance;
import neverstop.manager.main.model.DeviceModel;

/**
 * MainController
 *
 * @author @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 08/09/2018
 */
public class MainController {

    @FXML private TableView<DeviceModel> managedSensorTableView;
    @FXML private TableColumn<DeviceModel, String> deviceIdTableColumn;
    @FXML private TableColumn<DeviceModel, DeviceState> deviceStateTableColumn;
    @FXML private TableColumn<DeviceModel, PowerBalance> powerBalanceTableColumn;
    @FXML private TableColumn<DeviceModel, Integer> cpuTableColumn;
    @FXML private TableColumn<DeviceModel, Integer> memoryTableColumn;
    @FXML private TableColumn<DeviceModel, String> responseTimeTableColumn;

    @FXML private TextField deviceIdTextField;
    @FXML private Text deviceStateText;
    @FXML private TextField powerBalanceTextField;
    @FXML private TextField cpuTextField;
    @FXML private TextField memoryTextField;
    @FXML private TextField rowDataTextField;

    @FXML private Button checkButton;

    private Properties properties;

    private DeviceModel deviceModel;
    private ObservableList<DeviceModel> deviceModels;

    private GatewaySNMPRequester requester;

    public MainController() {
        //
        this.deviceModels = FXCollections.observableArrayList();
        this.requester = new GatewaySNMPRequester();
    }

    public void initialize() {
        //
        initControls();
        bindEvents();
    }

    private void getData() {
        //
        deviceModels.clear();
//        List<Device> devices = requester.checkAllDevices();
        List<Device> devices = requester.checkDevice();
        for (Device device : devices) {
            deviceModels.add(new DeviceModel(device));
        }
    }

    private void initControls() {
        //
        managedSensorTableView.setItems(deviceModels);
        deviceIdTableColumn.setCellValueFactory(new PropertyValueFactory<DeviceModel, String>("deviceId"));
        deviceStateTableColumn.setCellValueFactory(new PropertyValueFactory<DeviceModel, DeviceState>("deviceState"));
        powerBalanceTableColumn.setCellValueFactory(new PropertyValueFactory<DeviceModel, PowerBalance>("powerBalance"));
        cpuTableColumn.setCellValueFactory(new PropertyValueFactory<DeviceModel, Integer>("cpuUsage"));
        memoryTableColumn.setCellValueFactory(new PropertyValueFactory<DeviceModel, Integer>("memoryUsage"));
        responseTimeTableColumn.setCellValueFactory(new PropertyValueFactory<DeviceModel, String>("responseTimestamp"));

        deviceModel = new DeviceModel();

        rowDataTextField.textProperty().bindBidirectional(deviceModel.rowDataProperty());
        deviceIdTextField.textProperty().bindBidirectional(deviceModel.deviceIdProperty());
    }

    private void bindEvents() {
        //
        managedSensorTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                deviceModel.setValues(newVal);
                drawDetailInfromation();
            }
        });

        checkButton.setOnAction(event -> {
            getData();
        });
    }

    private void addSampleData() {
        //
        deviceModels.clear();

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            DeviceModel model = new DeviceModel();
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
            deviceModels.add(model);
        }

    }

    private void drawDetailInfromation() {
        deviceStateText.setText(deviceModel.deviceStateProperty().getValue().name());
        powerBalanceTextField.setText(deviceModel.powerBalanceProperty().getValue().name());
        cpuTextField.textProperty().set(String.valueOf(deviceModel.cpuUsageProperty().get()));
        memoryTextField.textProperty().set(String.valueOf(deviceModel.memoryUsageProperty().get()));
        System.out.println(deviceModel.rowDataProperty().get());
        rowDataTextField.textProperty().set(deviceModel.rowDataProperty().get());
    }
}