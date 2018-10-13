

package neverstop.manager.main.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import neverstop.manager.entity.sensor.DeviceState;
import neverstop.manager.entity.sensor.PowerBalance;
import neverstop.manager.entity.sensor.Sensor;

/**
 * SensorModel
 *
 * @author @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 08/09/2018
 */
public class SensorModel {

    private SimpleStringProperty deviceIdProperty;
    private SimpleObjectProperty<DeviceState> deviceStateProperty;
    private SimpleObjectProperty<PowerBalance> powerBalanceProperty;
    private SimpleIntegerProperty cpuUsageProperty;
    private SimpleIntegerProperty memoryUsageProperty;
    private SimpleStringProperty responseTimestampProperty;
    private SimpleStringProperty rowDataProperty;

    public SensorModel() {
        this.deviceIdProperty = new SimpleStringProperty();
        this.deviceStateProperty = new SimpleObjectProperty<>();
        this.powerBalanceProperty = new SimpleObjectProperty<>();
        this.cpuUsageProperty = new SimpleIntegerProperty();
        this.memoryUsageProperty = new SimpleIntegerProperty();
        this.responseTimestampProperty = new SimpleStringProperty();
        this.rowDataProperty = new SimpleStringProperty();
    }

    public SensorModel(Sensor sensor) {
        //
        setValues(sensor);
    }

    public void setValues(Sensor sensor) {
        this.deviceIdProperty.set(sensor.getDeviceId());
        this.deviceStateProperty.set(sensor.getDeviceState());
        this.powerBalanceProperty.set(sensor.getPowerBalance());
        this.cpuUsageProperty.set(sensor.getSystemMetric().getCpuUsage());
        this.memoryUsageProperty.set(sensor.getSystemMetric().getMemoryUsage());
        this.rowDataProperty.set(String.join("", sensor.getRowDataArray()));

    }

    public void setValues(SensorModel sensor) {
        //
        deviceIdProperty.set(sensor.deviceIdProperty().get());
        deviceStateProperty.set(sensor.deviceStateProperty().get());
        powerBalanceProperty.set(sensor.powerBalanceProperty().get());
        cpuUsageProperty.set(sensor.cpuUsageProperty().get());
        memoryUsageProperty.set(sensor.memoryUsageProperty().get());
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

    public SimpleIntegerProperty cpuUsageProperty() {
        return cpuUsageProperty;
    }

    public SimpleIntegerProperty memoryUsageProperty() {
        return memoryUsageProperty;
    }

    public SimpleStringProperty responseTimestampProperty() {
        return responseTimestampProperty;
    }
}
