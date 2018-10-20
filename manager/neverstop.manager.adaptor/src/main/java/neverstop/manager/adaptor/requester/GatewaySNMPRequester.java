package neverstop.manager.adaptor.requester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.snmp4j.smi.OID;

import neverstop.manager.adaptor.config.DeviceProperties;
import neverstop.manager.entity.sensor.Device;
import neverstop.manager.entity.sensor.DeviceState;
import neverstop.manager.entity.sensor.PowerBalance;
import neverstop.neverstop.agent.domain.type.StateOID;
import neverstop.neverstop.client.request.SNMPRequester;

/**
 * GatewaySNMPRequester
 *
 * @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 15/09/2018
 */
public class GatewaySNMPRequester {
    //
    private DeviceProperties properties;

    public GatewaySNMPRequester() {
        //
        this.properties = new DeviceProperties();
    }

//    public List<Device> checkAllDevices() {
//
//        String[] managedDeviceIds = properties.getManagedDeviceIds();
//        List<Device> devices = new ArrayList<Device>();
//
//        for (String deviceId : managedDeviceIds) {
//            devices.add(checkDevice(deviceId));
//        }
//        return devices;
//    }

    public List<Device> checkDevice() {
        //
        List<Device> deviceList = new ArrayList<Device>();

        SNMPRequester requester = new SNMPRequester();
        requester.connect();

        List<List<String>> states = requester.getStates(new OID[]{
                new OID(StateOID.InterFace.getOID() + StateOID.DeviceId.getOID()),
                new OID(StateOID.InterFace.getOID() + StateOID.PowerBalance.getOID()),
                new OID(StateOID.InterFace.getOID() + StateOID.Processor.getOID()),
                new OID(StateOID.InterFace.getOID() + StateOID.NetBandWidth.getOID()),
                new OID(StateOID.InterFace.getOID() + StateOID.ErrorHandler.getOID()),
        });

        for(List<String> deviceStates : states){
            Device device = new Device();
//            device.setDeviceId(deviceId);
//            device.setPowerBalance(PowerBalance.markState(Double.valueOf(states.get(0).replaceAll("[^0-9]", ""))));
//            device.setSystemMetric(new Device.SystemMetric(Integer.valueOf(states.get(1).replaceAll("[^0-9]", ""))));
//
//            device.setDeviceState(DeviceState.Connected);
//            device.setResponseTimestamp(new Date().toString());
//            device.setRowDataArray(states);
            device.setDeviceId(deviceStates.get(1));
            device.setPowerBalance(PowerBalance.markState(Double.valueOf(deviceStates.get(2).replaceAll("[^0-9]", ""))));
            device.setSystemMetric(new Device.SystemMetric(Integer.valueOf(deviceStates.get(3).replaceAll("[^0-9]", "")),
                    Integer.valueOf(deviceStates.get(4).replaceAll("[^0-9]", ""))));

            device.setDeviceState(DeviceState.Connected);
            device.setResponseTimestamp(new Date().toString());
            device.setRowDataArray(deviceStates);
            deviceList.add(device);
        }


        try {
            requester.disconnect();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }

        return deviceList;
    }
}
