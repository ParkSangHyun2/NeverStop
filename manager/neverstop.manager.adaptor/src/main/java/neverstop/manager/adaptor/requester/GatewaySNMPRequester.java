package neverstop.manager.adaptor.requester;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.snmp4j.smi.OID;

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
public class GatewaySNMPRequester implements GatewayRequester {
    //
    public GatewaySNMPRequester() {
        //
    }

    public Device checkSensor(String deviceId) {
        //
        SNMPRequester requester = new SNMPRequester();
        requester.connect();

        List<String> states = requester.getStates(new OID[]{
                new OID(StateOID.InterFace.getOID() + StateOID.PowerBalance.getOID()),
                new OID(StateOID.InterFace.getOID() + StateOID.Memory.getOID()),
        });

        Device device = new Device();
        device.setDeviceId(deviceId);
        device.setPowerBalance(PowerBalance.markState(Double.valueOf(states.get(0).replaceAll("[^0-9]", ""))));
        device.setSystemMetric(new Device.SystemMetric(Integer.valueOf(states.get(1).replaceAll("[^0-9]", ""))));

        device.setDeviceState(DeviceState.Connected);
        device.setResponseTimestamp(new Date().toString());
        device.setRowDataArray(states);

        try {
            requester.disconnect();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }

        return device;
    }
}
