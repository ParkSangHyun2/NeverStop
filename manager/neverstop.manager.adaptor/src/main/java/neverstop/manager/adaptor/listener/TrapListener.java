package neverstop.manager.adaptor.listener;

import java.io.IOException;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.CommunityTarget;
import org.snmp4j.MessageDispatcher;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.MessageException;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.StateReference;
import org.snmp4j.mp.StatusInformation;
import org.snmp4j.security.Priv3DES;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.TransportIpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

/**
 * TrapListener
 *
 * @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 2018. 10. 27.
 */
public class TrapListener implements CommandResponder {
    //
    public synchronized void listen(TransportIpAddress address) throws IOException {
        //
        AbstractTransportMapping transport;
        if (address instanceof TcpAddress) {
            transport = new DefaultTcpTransportMapping((TcpAddress) address);
        } else {
            transport = new DefaultUdpTransportMapping((UdpAddress) address);
        }

        ThreadPool threadPool = ThreadPool.create("DispatcherPool", 10);
        MessageDispatcher mtDispatcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());

        // add message processing models
        mtDispatcher.addMessageProcessingModel(new MPv1());
        mtDispatcher.addMessageProcessingModel(new MPv2c());

        // add all security protocols
        SecurityProtocols.getInstance().addDefaultProtocols();
        SecurityProtocols.getInstance().addPrivacyProtocol(new Priv3DES());

        //Create Target
        CommunityTarget target = new CommunityTarget();
        target.setCommunity( new OctetString("public"));

        Snmp snmp = new Snmp(mtDispatcher, transport);
        snmp.addCommandResponder(this);

        transport.listen();
        System.out.println("Listening on " + address);

        try {
            this.wait();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void processPdu(CommandResponderEvent commandResponderEvent) {
        //
        System.out.println("Received PDU...");
        PDU pdu = commandResponderEvent.getPDU();
        if (pdu != null) {
            try {
                TrapMessageHolder.shareInstance().add(pdu.getVariableBindings());
                System.out.println(TrapMessageHolder.shareInstance().hasMessage());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            int pduType = pdu.getType();
            if ((pduType != PDU.TRAP) && (pduType != PDU.V1TRAP) && (pduType != PDU.REPORT)
                    && (pduType != PDU.RESPONSE)) {
                pdu.setErrorIndex(0);
                pdu.setErrorStatus(0);
                pdu.setType(PDU.RESPONSE);
                StatusInformation statusInformation = new StatusInformation();
                StateReference ref = commandResponderEvent.getStateReference();
                try {
                    System.out.println(commandResponderEvent.getPDU());
                    commandResponderEvent.getMessageDispatcher().returnResponsePdu(commandResponderEvent.getMessageProcessingModel(),
                            commandResponderEvent.getSecurityModel(), commandResponderEvent.getSecurityName(), commandResponderEvent.getSecurityLevel(),
                            pdu, commandResponderEvent.getMaxSizeResponsePDU(), ref, statusInformation);
                } catch (MessageException ex) {
                    System.err.println("Error while sending response: " + ex.getMessage());
                }
            }
        }
    }
}
