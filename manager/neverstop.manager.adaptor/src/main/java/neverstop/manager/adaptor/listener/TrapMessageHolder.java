package neverstop.manager.adaptor.listener;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import org.snmp4j.smi.VariableBinding;

/**
 * TrapMessageHolder
 *
 * @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 2018. 10. 27.
 */
public class TrapMessageHolder {
    //
    private static TrapMessageHolder instance;
    private static Queue<Vector<? extends VariableBinding>> trapMessageList;

    private TrapMessageHolder() {
        //
        trapMessageList = new LinkedList<Vector<? extends VariableBinding>>();
    }

    public static TrapMessageHolder shareInstance() {
        //
        if (instance == null) {
            instance = new TrapMessageHolder();
        }

        return instance;
    }

    public Vector<? extends VariableBinding> poll() {
        //
        return trapMessageList.poll();
    }

    public void add(Vector<? extends VariableBinding> message) {
        //
        trapMessageList.add(message);
    }

    public boolean hasMessage() {
        //
        return trapMessageList.size() > 0;
    }
}
