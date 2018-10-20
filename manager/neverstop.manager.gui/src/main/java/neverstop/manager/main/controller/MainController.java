package neverstop.manager.main.controller;


import java.util.List;
import java.util.Properties;

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
    @FXML private TableColumn<DeviceModel, Integer> processorTableColumn;
    @FXML private TableColumn<DeviceModel, String> netBandWithTableColumn;
    @FXML private TableColumn<DeviceModel, Integer> memoryTableColumn;
    @FXML private TableColumn<DeviceModel, String> responseTimeTableColumn;

    @FXML private TextField deviceIdTextField;
    @FXML private Text deviceStateText;
    @FXML private TextField powerBalanceTextField;
    @FXML private TextField cpuTextField;
    @FXML private TextField memoryTextField;
    @FXML private TextField rowDataTextField;

    @FXML private TextField trapMessageTextField;

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
        processorTableColumn.setCellValueFactory(new PropertyValueFactory<DeviceModel, Integer>("processor"));
        memoryTableColumn.setCellValueFactory(new PropertyValueFactory<DeviceModel, Integer>("memoryUsage"));
        netBandWithTableColumn.setCellValueFactory(new PropertyValueFactory<DeviceModel, String>("netBandWith"));
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

    private void listenTrapMessage() {
        // TODO:
    }

    private void drawDetailInfromation() {
        deviceStateText.setText(deviceModel.deviceStateProperty().getValue().name());
        powerBalanceTextField.setText(deviceModel.powerBalanceProperty().getValue().name());
        cpuTextField.textProperty().set(String.valueOf(deviceModel.processorProperty().get()));
        memoryTextField.textProperty().set(String.valueOf(deviceModel.memoryUsageProperty().get()));
        System.out.println(deviceModel.rowDataProperty().get());
        rowDataTextField.textProperty().set(deviceModel.rowDataProperty().get());
    }
}