package neverstop.manager.adaptor.requester;

import java.io.IOException;
import java.util.List;

import org.snmp4j.smi.OID;

import neverstop.manager.entity.sensor.Sensor;
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

    public Sensor checkSensor(String deviceId) {
        //
        SNMPRequester requester = new SNMPRequester();
        requester.connect();
        List<String> results = requester.getStates(new OID[]{
                new OID(StateOID.InterFace.getOID() + StateOID.ErrorHandler.getOID())
        });

        for (String result : results) {
            System.out.println("RESULT : " + result);
        }

        try {
            requester.disconnect();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }

        // TODO: parse result;
        System.out.println(results.size());

        return new Sensor();
    }

}
