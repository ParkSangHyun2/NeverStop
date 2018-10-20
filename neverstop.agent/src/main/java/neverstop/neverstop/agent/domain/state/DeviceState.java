package neverstop.neverstop.agent.domain.state;

import java.util.Random;

public class DeviceState {
    //
    private String deviceId;
    private String powerBalance;
    private String processor;
    private String memory;
    private String netBandWith;
    private String errorHandler;

    public DeviceState(String deviceId) {
        //
        this.deviceId = deviceId;
        Random random = new Random();
        powerBalance = String.valueOf(random.nextInt(100));
        processor = String.valueOf(random.nextInt(4));
        memory = String.valueOf(random.nextInt(100));
        netBandWith = String.valueOf(random.nextInt(100));
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getPowerBalance() {
        return powerBalance;
    }

    public String getProcessor() {
        return processor;
    }

    public String getMemory() {
        return memory;
    }

    public String getNetBandWith() {
        return netBandWith;
    }

    public String getErrorHandler() {
        return errorHandler;
    }
}
