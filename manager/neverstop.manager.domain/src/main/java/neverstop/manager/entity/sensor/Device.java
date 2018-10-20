

package neverstop.manager.entity.sensor;

import java.util.List;

/**
 * Device
 *
 * @author @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 08/09/2018
 */
public class Device {

    private String deviceId;
    private SystemMetric systemMetric;
    private PowerBalance powerBalance;
    private DeviceState deviceState;
    private List<String> rowDataArray;
    private String responseTimestamp;

    public Device() {
        //
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public SystemMetric getSystemMetric() {
        return systemMetric;
    }

    public void setSystemMetric(SystemMetric systemMetric) {
        this.systemMetric = systemMetric;
    }

    public PowerBalance getPowerBalance() {
        return powerBalance;
    }

    public void setPowerBalance(PowerBalance powerBalance) {
        this.powerBalance = powerBalance;
    }

    public DeviceState getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(DeviceState deviceState) {
        this.deviceState = deviceState;
    }

    public List<String> getRowDataArray() {
        return rowDataArray;
    }

    public void setRowDataArray(List<String> rowDataArray) {
        this.rowDataArray = rowDataArray;
    }

    public String getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(String responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public static class SystemMetric {

        private int cpuUsage;
        private int memoryUsage;

        public SystemMetric(int memoryUsage) {
            this.memoryUsage = memoryUsage;
        }

        public int getCpuUsage() {
            return cpuUsage;
        }

        public int getMemoryUsage() {
            return memoryUsage;
        }
    }
}