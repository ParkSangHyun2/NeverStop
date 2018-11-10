package neverstop.manager.main.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import neverstop.manager.adaptor.listener.TrapListener;
import neverstop.manager.adaptor.listener.TrapMessageHolder;
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
    //
    @FXML private TableView<DeviceModel> managedSensorTableView;
    @FXML private TableColumn<DeviceModel, String> agentAddressTableColumn;
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
    @FXML private Slider autoRequestIntervalSlider;
    @FXML private ToggleButton autoRequestEnabledButton;

    private DeviceModel deviceModel;
    private ObservableList<DeviceModel> deviceModels;

    private GatewaySNMPRequester requester;

    private TrapListener trapListener;

    private Map<Class, Thread> tasks;

    public MainController() {
        //
        this.deviceModels = FXCollections.observableArrayList();
        this.requester = new GatewaySNMPRequester();
        this.trapListener = new TrapListener();
        this.tasks = new HashMap<>();
    }

    public void initialize() {
        //
        initControls();
        bindEvents();
        if (tasks.isEmpty()) {
            listenTrapMessage();
        }
    }

    private void getData() {
        //
        deviceModels.clear();
        List<Device> devices;
        try {
            devices = requester.checkDevice();
            for (Device device : devices) {
                deviceModels.add(new DeviceModel(device));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initControls() {
        //
        managedSensorTableView.setItems(deviceModels);
        deviceIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("deviceId"));
        deviceStateTableColumn.setCellValueFactory(new PropertyValueFactory<>("deviceState"));
        powerBalanceTableColumn.setCellValueFactory(new PropertyValueFactory<>("powerBalance"));
        processorTableColumn.setCellValueFactory(new PropertyValueFactory<>("processor"));
        memoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("memoryUsage"));
        netBandWithTableColumn.setCellValueFactory(new PropertyValueFactory<>("netBandWith"));
        agentAddressTableColumn.setCellFactory(cellData -> new EditableAddressTableCell());
        responseTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("responseTimestamp"));

        deviceModel = new DeviceModel();

        rowDataTextField.textProperty().bindBidirectional(deviceModel.rowDataProperty());
        deviceIdTextField.textProperty().bindBidirectional(deviceModel.deviceIdProperty());
        autoRequestEnabledButton.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            //
            List<String> agentAddresses = new ArrayList<>();
            for (DeviceModel deviceModel : deviceModels) {
                agentAddresses.add(deviceModel.addressProperty().get());
            }

            Double sliderValue = autoRequestIntervalSlider.valueProperty().get();
            RequestAgentStateTask requestAgentStateTask = new RequestAgentStateTask(sliderValue.intValue() * 1000 * 60);
            tasks.put(RequestAgentStateTask.class, requestAgentStateTask);
        }));
    }

    private void bindEvents() {
        //
        managedSensorTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                deviceModel.setValues(newVal);
                drawDetailInformation();
            }
        });

        checkButton.setOnAction(event -> getData());
    }

    private void listenTrapMessage() {
        //
        TrapListenTask trapListenTask = new TrapListenTask();
        trapListenTask.start();

        TrapMessageCheckTask trapMessageCheckTask = new TrapMessageCheckTask(10000);
        trapMessageCheckTask.start();

        tasks.put(TrapListenTask.class, trapListenTask);
        tasks.put(TrapMessageCheckTask.class, trapMessageCheckTask);
    }

    private void drawDetailInformation() {
        //
        deviceStateText.setText(deviceModel.deviceStateProperty().getValue().name());
        powerBalanceTextField.setText(deviceModel.powerBalanceProperty().getValue().name());
        cpuTextField.textProperty().set(String.valueOf(deviceModel.processorProperty().get()));
        memoryTextField.textProperty().set(String.valueOf(deviceModel.memoryUsageProperty().get()));
        System.out.println(deviceModel.rowDataProperty().get());
        rowDataTextField.textProperty().set(deviceModel.rowDataProperty().get());
    }

    private class EditableAddressTableCell extends TableCell<DeviceModel, String> {
        //
        @Override
        protected void updateItem(String item, boolean empty) {
            Object rowItem = getTableRow().getItem();
            if (!empty && rowItem != null) {
                this.setGraphic(new TextField(item));
            }
        }
    }

    private class TrapListenTask extends Thread {
        //

        public TrapListenTask() {
            //
            this.setName(this.getClass().getSimpleName());
            this.setDaemon(true);
        }

        @Override
        public void run() {
            //
            try {
                for (String agentAddress : agentAddresses) {
                    // agentAddress = "127.0.0.1/18888"
                    trapListener.listen(new UdpAddress(agentAddress));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class TrapMessageCheckTask extends Thread {
        //
        private int checkInterval;

        public TrapMessageCheckTask(int checkInterval) {
            //
            this.checkInterval = checkInterval;
        }

        @Override
        public void run() {
            //
            trapMessageTextField.setText("checking... ");
            while (!hasBeenInterrupted()) {
                //
                if (TrapMessageHolder.shareInstance().hasMessage()) {
                    Vector<? extends VariableBinding> message = TrapMessageHolder.shareInstance().poll();
                    System.out.println(message);

                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Trap Alert");
                        alert.setHeaderText("Trap Occurred!");
                        alert.setContentText("Agent Sent a Trap Message. Check the Agent.");
                        alert.initOwner(trapMessageTextField.getScene().getWindow());

                        Label label = new Label("The trap message was:");
                        trapMessageTextField.setText(message.toString());

                        TextArea textArea = new TextArea();
                        textArea.setEditable(false);
                        textArea.setWrapText(true);
                        textArea.setText(message.toString());

                        textArea.setMaxWidth(Double.MAX_VALUE);
                        textArea.setMaxHeight(Double.MAX_VALUE);
                        GridPane.setVgrow(textArea, Priority.ALWAYS);
                        GridPane.setHgrow(textArea, Priority.ALWAYS);

                        GridPane expContent = new GridPane();
                        expContent.setMaxWidth(Double.MAX_VALUE);
                        expContent.add(label, 0, 0);
                        expContent.add(textArea, 0, 1);

                        // Set expandable Exception into the dialog pane.
                        alert.getDialogPane().setExpandableContent(expContent);

                        // run JavaFX UI Thread
                        alert.showAndWait();
                    });

                }
            }
        }

        private boolean hasBeenInterrupted() {
            //
            try {
                Thread.sleep(checkInterval);
                return false;
            } catch (InterruptedException e) {
                return true;
            }
        }
    }

    private class RequestAgentStateTask extends Thread {
        //
        private int requestInterval;

        public RequestAgentStateTask(int requestInterval) {
            //
            this.requestInterval = requestInterval;
        }

        @Override
        public void run() {
            //
            while (!hasBeenInterrupted()) {
                try {
                    List<Device> devices = requester.checkDevice();
                    deviceModels.clear();
                    for (Device device : devices) {
                        deviceModels.addAll(new DeviceModel(device));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean hasBeenInterrupted() {
            //
            try {
                Thread.sleep(requestInterval);
                return false;
            } catch (InterruptedException e) {
                return true;
            }
        }
    }

    public void dispose() {
        //
        for (Thread task : tasks.values()) {
            task.interrupt();
        }
        System.out.println("controller disposed.");
    }
}