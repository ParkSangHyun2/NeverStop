package neverstop.manager.adaptor.requester;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.agent.test.TestAgent;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import neverstop.manager.entity.sensor.Sensor;

/**
 * GatewaySNMPRequester
 *
 * @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 15/09/2018
 */
public class GatewaySNMPRequester implements GatewayRequester {
    //
    private static final int POWER_BALANCE_ID = 4;

    private OctetString community;
    private int snmpVersion;
    private UdpAddress udpAddress;
    private String ipAddress;
    private int port;

    public GatewaySNMPRequester() {
        //
        this.community = new OctetString("private");
        this.snmpVersion = SnmpConstants.version2c;
        this.udpAddress = new UdpAddress();
        this.ipAddress = "127.0.0.1";
        this.port = 4700;
    }

    public Sensor checkSensor(String deviceId) {
        //
        try {
            udpAddress.setInetAddress(InetAddress.getByName(ipAddress));
            udpAddress.setPort(port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        CommunityTarget target = new CommunityTarget();
        target.setCommunity(community);
        target.setVersion(snmpVersion);
        target.setAddress(udpAddress);
        target.setRetries(2);
        target.setTimeout(2000);

        String oid = buildDeviceOID(deviceId);

        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GETNEXT);
        pdu.setRequestID(new Integer32(POWER_BALANCE_ID));

        try {
            TransportMapping transportMapping = new DefaultUdpTransportMapping();
            transportMapping.listen();

            Snmp snmp = new Snmp(transportMapping);
            ResponseEvent responseEvent = snmp.send(pdu, target);

            if (responseEvent != null) {
                PDU responsePDU = responseEvent.getResponse();
                Vector<? extends VariableBinding> variableBindings = responsePDU.getVariableBindings();
                System.out.println(variableBindings);
            } else {
                System.out.println("Error.");
            }

            snmp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String buildDeviceOID(String deviceId) {
        //
        return "1";
    }

    public static void main(String[] args) {
        //
        TestAgent.main(args);
//        SampleAgent sampleAgent = new SampleAgent(null);
//        sampleAgent.run();

        GatewaySNMPRequester requester = new GatewaySNMPRequester();
        requester.checkSensor("1");
    }
}
