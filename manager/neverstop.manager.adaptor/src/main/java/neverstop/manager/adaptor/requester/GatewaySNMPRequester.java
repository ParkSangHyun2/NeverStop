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

    public List<Device> checkDevice() {
        //
        List<Device> deviceList = new ArrayList<Device>();

        SNMPRequester requester = new SNMPRequester();
        requester.connect();

        List<List<String>> states = requester.getStates(new OID[]{
                new OID(StateOID.InterFace.getOID() + StateOID.DeviceId.getOID()),
                new OID(StateOID.InterFace.getOID() + StateOID.PowerBalance.getOID()),
                new OID(StateOID.InterFace.getOID() + StateOID.Processor.getOID()),
                new OID(StateOID.InterFace.getOID() + StateOID.Memory.getOID()),
                new OID(StateOID.InterFace.getOID() + StateOID.NetBandWidth.getOID()),
        });

        for(List<String> deviceStates : states) {
            Device device = new Device();
            device.setDeviceId(deviceStates.get(0));
            device.setPowerBalance(PowerBalance.markState(Double.valueOf(deviceStates.get(1).replaceAll("[^0-9]", ""))));
            device.setSystemMetric(new Device.SystemMetric(deviceStates.get(2), deviceStates.get(3)));
            device.setNetBandWith(deviceStates.get(4));
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
