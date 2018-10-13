package neverstop.manager.adaptor.requester;

import neverstop.manager.entity.sensor.Sensor;

/**
 * GatewayRequester
 *
 * @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 10/09/2018
 */
public interface GatewayRequester {

    Sensor checkSensor(String deviceId);
}
