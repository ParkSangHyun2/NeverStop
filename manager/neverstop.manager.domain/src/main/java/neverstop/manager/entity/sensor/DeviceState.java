
package neverstop.manager.entity.sensor;

/**
 * DeviceState
 *
 * @author @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 08/09/2018
 */
public enum DeviceState {

    Connected(1), Lost(0);

    private int code;

    DeviceState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public DeviceState valueOf(int code)  {
        for (DeviceState deviceState : DeviceState.values()) {
            if (deviceState.getCode() == code) {
                return deviceState;
            }
        }

        return null;
    }
}
