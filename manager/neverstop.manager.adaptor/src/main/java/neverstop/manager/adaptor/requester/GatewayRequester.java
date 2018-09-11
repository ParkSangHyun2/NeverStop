

package neverstop.manager.adaptor.requester;

import java.util.List;

import neverstop.manager.entity.sensor.Sensor;

/**
 * GatewayRequester
 *
 * @author @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 10/09/2018
 */
public interface GatewayRequester {

    Sensor checkSensor(String deviceId);
}
