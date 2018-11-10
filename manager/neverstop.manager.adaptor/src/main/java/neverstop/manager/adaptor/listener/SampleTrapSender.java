package neverstop.manager.adaptor.listener;

import java.util.Date;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 * SampleTrapSender
 *
 * @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 2018. 10. 27.
 */
public class SampleTrapSender {
    //
    public static final String community = "public";
    public static final String trapOid = ".1.3.6.1.2.1.1.6";
    public static final String ipAddress = "127.0.0.1";
    public static final int port = 18888;

    public static void main(String[] args)
    {
        SampleTrapSender sampleTrapSender = new SampleTrapSender();

        /* Sending V1 Trap */
        //sampleTrapSender.sendSnmpV1Trap();

        /* Sending V2 Trap */
        sampleTrapSender.sendSnmpV2Trap();
    }

    public void sendSnmpV1Trap()
    {
        try {
            //Create Transport Mapping
            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();

            //Create Target
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(community));
            target.setVersion(SnmpConstants.version1);
            target.setAddress(new UdpAddress(ipAddress + "/" + port));
            target.setRetries(2);
            target.setTimeout(5000);

            //Create PDU for V1
            PDUv1 pdu = new PDUv1();
            pdu.setType(PDU.V1TRAP);
            pdu.setEnterprise(new OID(trapOid));
            pdu.setGenericTrap(PDUv1.ENTERPRISE_SPECIFIC);
            pdu.setSpecificTrap(1);
            pdu.setAgentAddress(new IpAddress(ipAddress));

            //Send the PDU
            Snmp snmp = new Snmp(transport);
            System.out.println("Sending V1 Trap to " + ipAddress + " on Port " + port);
            snmp.send(pdu, target);
            snmp.close();
        } catch (Exception e) {
            System.err.println("Error in Sending V1 Trap to " + ipAddress + " on Port " + port);
            System.err.println("Exception Message = " + e.getMessage());
        }
    }


    /**
     * This methods sends the V2 trap to the Localhost in port 163
     */
    public void sendSnmpV2Trap() {
        try {
            //Create Transport Mapping
            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();

            //Create Target
            CommunityTarget comtarget = new CommunityTarget();
            comtarget.setCommunity(new OctetString(community));
            comtarget.setVersion(SnmpConstants.version2c);
            comtarget.setAddress(new UdpAddress(ipAddress + "/" + port));
            comtarget.setRetries(2);
            comtarget.setTimeout(5000);

            //Create PDU for V2
            PDU pdu = new PDU();

            // need to specify the system up time
            pdu.add(new VariableBinding(SnmpConstants.sysUpTime, new OctetString(new Date().toString())));
            pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID, new OID(trapOid)));
            pdu.add(new VariableBinding(SnmpConstants.snmpTrapAddress, new IpAddress(ipAddress)));

            // variable binding for Enterprise Specific objects, Severity (should be defined in MIB file)
            pdu.add(new VariableBinding(new OID(trapOid), new OctetString("Major")));
            pdu.setType(PDU.NOTIFICATION);

            //Send the PDU
            Snmp snmp = new Snmp(transport);
            System.out.println("Sending V2 Trap to " + ipAddress + " on Port " + port);
            snmp.send(pdu, comtarget);
            snmp.close();
        } catch (Exception e) {
            System.err.println("Error in Sending V2 Trap to " + ipAddress + " on Port " + port);
            System.err.println("Exception Message = " + e.getMessage());
        }
    }
}
