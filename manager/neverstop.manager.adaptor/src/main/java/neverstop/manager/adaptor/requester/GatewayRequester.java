package neverstop.manager.adaptor.requester;

import java.util.List;

import neverstop.manager.entity.sensor.Device;

/**
 * GatewayRequester
 *
 * @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 10/09/2018
 */
public interface GatewayRequester {

    List<Device> checkAllDevices();
    Device checkDevice(String deviceId);
}
