package neverstop.manager.adaptor.requester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import neverstop.manager.adaptor.config.DeviceProperties;
import neverstop.manager.entity.sensor.Device;

/**
 * GatewaySNMPRequester
 *
 * @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 15/09/2018
 */
public class GatewaySNMPRequester {
    //
    private DeviceProperties properties;
    private static String agentIpAddress = "192.168.0.100";

    private static int agentPort = 4700;

    // OID of MIB RFC 1213; Scalar Object = .iso.org.dod.internet.mgmt.mib-2.system.sysDescr.0
    private static String oidValue = ".1.3.6.1.2.1.1.1.0";  // ends with 0 for scalar object

    // sysContact OID of MIB RFC 1213; Scalar Object = .iso.org.dod.internet.mgmt.mib-2.system.sysContact.0
    private static String sysContactOid = ".1.3.6.1.2.1.1.4.0";  // ends with 0 for scalar object

    private static String  sysContactValue  = "TechDive.in";

    private static int snmpVersion = SnmpConstants.version1;

    private static String community = "public";

    public GatewaySNMPRequester() {
        //
        this.properties = new DeviceProperties();
    }

    public void setDevice(Device device) throws IOException {
        //
        System.out.println("SNMP SET Demo");

        // Create TransportMapping and Listen
        TransportMapping transport = new DefaultUdpTransportMapping();
        transport.listen();

        // Create Target Address object
        CommunityTarget comtarget = new CommunityTarget();
        comtarget.setCommunity(new OctetString(community));
        comtarget.setVersion(snmpVersion);
        comtarget.setAddress(new UdpAddress(agentIpAddress + "/" + agentPort));
        comtarget.setRetries(2);
        comtarget.setTimeout(1000);

        // Create the PDU object
        PDU pdu = new PDU();

        // Setting the Oid and Value for sysContact variable
        OID oid = new OID(sysContactOid);
        Variable var = new OctetString(sysContactValue);
        VariableBinding varBind = new VariableBinding(oid,var);
        pdu.add(varBind);

        pdu.setType(PDU.SET);
        pdu.setRequestID(new Integer32(1));

        // Create Snmp object for sending data to Agent
        Snmp snmp = new Snmp(transport);

        System.out.println("\nRequest:\n[ Note: Set Request is sent for sysContact oid in RFC 1213 MIB.");
        System.out.println("Set operation will change the sysContact value to " + sysContactValue );
        System.out.println("Once this operation is completed, Querying for sysContact will poll the value = " + sysContactValue + " ]");

        System.out.println("Request:\nSending Snmp Set Request to Agent...");
        ResponseEvent response = snmp.set(pdu, comtarget);

        // Process Agent Response
        if (response != null)
        {
            System.out.println("\nResponse:\nGot Snmp Set Response from Agent");
            PDU responsePDU = response.getResponse();

            if (responsePDU != null)
            {
                int errorStatus = responsePDU.getErrorStatus();
                int errorIndex = responsePDU.getErrorIndex();
                String errorStatusText = responsePDU.getErrorStatusText();

                if (errorStatus == PDU.noError)
                {
                    System.out.println("Snmp Set Response = " + responsePDU.getVariableBindings());
                }
                else
                {
                    System.out.println("Error: Request Failed");
                    System.out.println("Error Status = " + errorStatus);
                    System.out.println("Error Index = " + errorIndex);
                    System.out.println("Error Status Text = " + errorStatusText);
                }
            }
            else
            {
                System.out.println("Error: Response PDU is null");
            }
        }
        else
        {
            System.out.println("Error: Agent Timeout... ");
        }
        snmp.close();
    }

    public List<Device> checkDevice() throws IOException {
        //
        System.out.println("SNMP GET Demo");

        // Create TransportMapping and Listen
        TransportMapping transport = new DefaultUdpTransportMapping();
        transport.listen();

        // Create Target Address object
        CommunityTarget comtarget = new CommunityTarget();
        comtarget.setCommunity(new OctetString(community));
        comtarget.setVersion(snmpVersion);
        comtarget.setAddress(new UdpAddress(agentIpAddress + "/" + agentPort));
        comtarget.setRetries(2);
        comtarget.setTimeout(1000);

        // Create the PDU object
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oidValue)));
        pdu.setType(PDU.GET);
        pdu.setRequestID(new Integer32(1));

        // Create Snmp object for sending data to Agent
        Snmp snmp = new Snmp(transport);

        System.out.println("Sending Request to Agent...");
        ResponseEvent response = snmp.get(pdu, comtarget);

        List<Device> devices = new ArrayList<Device>();
        // Process Agent Response
        if (response != null)
        {
            System.out.println("Got Response from Agent");
            PDU responsePDU = response.getResponse();

            if (responsePDU != null)
            {
                int errorStatus = responsePDU.getErrorStatus();
                int errorIndex = responsePDU.getErrorIndex();
                String errorStatusText = responsePDU.getErrorStatusText();

                if (errorStatus == PDU.noError)
                {
                    System.out.println("Snmp Get Response = " + responsePDU.getVariableBindings());
                    for (Object variable : responsePDU.getVariableBindings()) {
                        System.out.println(variable.toString());
                        Device device = new Device();
                        device.setDeviceId(variable.toString());
                        devices.add(device);
                    }
                }
                else
                {
                    System.out.println("Error: Request Failed");
                    System.out.println("Error Status = " + errorStatus);
                    System.out.println("Error Index = " + errorIndex);
                    System.out.println("Error Status Text = " + errorStatusText);
                }
            }
            else
            {
                System.out.println("Error: Response PDU is null");
            }
        }
        else
        {
            System.out.println("Error: Agent Timeout... ");
        }
        snmp.close();

        return devices;

//        List<Device> deviceList = new ArrayList<Device>();
//
//        SNMPRequester requester = new SNMPRequester();
//        requester.connect();
//
//        List<List<String>> states = requester.getStates(new OID[]{
//                new OID(StateOID.InterFace.getOID() + StateOID.DeviceId.getOID()),
//                new OID(StateOID.InterFace.getOID() + StateOID.PowerBalance.getOID()),
//                new OID(StateOID.InterFace.getOID() + StateOID.Processor.getOID()),
//                new OID(StateOID.InterFace.getOID() + StateOID.Memory.getOID()),
//                new OID(StateOID.InterFace.getOID() + StateOID.NetBandWidth.getOID()),
//        });
//
//        for(List<String> deviceStates : states) {
//            Device device = new Device();
//            device.setDeviceId(deviceStates.poll(0));
//            device.setPowerBalance(PowerBalance.markState(Double.valueOf(deviceStates.poll(1).replaceAll("[^0-9]", ""))));
//            device.setSystemMetric(new Device.SystemMetric(deviceStates.poll(2), deviceStates.poll(3)));
//            device.setNetBandWith(deviceStates.poll(4));
//            device.setDeviceState(DeviceState.Connected);
//            device.setResponseTimestamp(new Date().toString());
//            device.setRowDataArray(deviceStates);
//            deviceList.add(device);
//        }
//
//        try {
//            requester.disconnect();
//        } catch (IOException e) {
//            //
//            e.printStackTrace();
//        }
//
//        return deviceList;
    }
}
