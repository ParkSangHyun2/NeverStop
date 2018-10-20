

package neverstop.manager.main.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import neverstop.manager.entity.sensor.Device;
import neverstop.manager.entity.sensor.DeviceState;
import neverstop.manager.entity.sensor.PowerBalance;

/**
 * DeviceModel
 *
 * @author @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 08/09/2018
 */
public class DeviceModel {
    //
    private SimpleStringProperty deviceIdProperty;
    private SimpleObjectProperty<DeviceState> deviceStateProperty;
    private SimpleObjectProperty<PowerBalance> powerBalanceProperty;
    private SimpleStringProperty processorProperty;
    private SimpleStringProperty memoryUsageProperty;
    private SimpleStringProperty netBandWithProperty;
    private SimpleStringProperty responseTimestampProperty;
    private SimpleStringProperty rowDataProperty;

    public DeviceModel() {
        this.deviceIdProperty = new SimpleStringProperty();
        this.deviceStateProperty = new SimpleObjectProperty<>();
        this.powerBalanceProperty = new SimpleObjectProperty<>();
        this.processorProperty = new SimpleStringProperty();
        this.memoryUsageProperty = new SimpleStringProperty();
        this.netBandWithProperty = new SimpleStringProperty();
        this.responseTimestampProperty = new SimpleStringProperty();
        this.rowDataProperty = new SimpleStringProperty();
    }

    public DeviceModel(Device device) {
        //
        this();
        setValues(device);
    }

    public void setValues(Device device) {
        this.deviceIdProperty.set(device.getDeviceId());
        this.deviceStateProperty.set(device.getDeviceState());
        this.powerBalanceProperty.set(device.getPowerBalance());
        this.processorProperty.set(device.getSystemMetric().getProcessors());
        this.memoryUsageProperty.set(device.getSystemMetric().getMemoryUsage());
        this.netBandWithProperty.set(device.getNetBandWith());
        this.rowDataProperty.set(String.join("", device.getRowDataArray()));
        this.responseTimestampProperty.set(device.getResponseTimestamp());
    }

    public void setValues(DeviceModel sensor) {
        //
        deviceIdProperty.set(sensor.deviceIdProperty().get());
        deviceStateProperty.set(sensor.deviceStateProperty().get());
        powerBalanceProperty.set(sensor.powerBalanceProperty().get());
        processorProperty.set(sensor.processorProperty().get());
        memoryUsageProperty.set(sensor.memoryUsageProperty().get());
        rowDataProperty.set(sensor.rowDataProperty().get());
        responseTimestampProperty.set(sensor.responseTimestampProperty().get());
    }

    public SimpleStringProperty deviceIdProperty() {
        return deviceIdProperty;
    }

    public SimpleObjectProperty<DeviceState> deviceStateProperty() {
        return deviceStateProperty;
    }

    public SimpleObjectProperty<PowerBalance> powerBalanceProperty() {
        return powerBalanceProperty;
    }

    public SimpleStringProperty processorProperty() {
        return processorProperty;
    }

    public SimpleStringProperty memoryUsageProperty() {
        return memoryUsageProperty;
    }

    public SimpleStringProperty netBandWithProperty() {
        return netBandWithProperty;
    }

    public SimpleStringProperty responseTimestampProperty() {
        return responseTimestampProperty;
    }

    public SimpleStringProperty rowDataProperty() {
        return rowDataProperty;
    }
}
