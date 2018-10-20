package neverstop.manager.adaptor.config;

import java.io.IOException;
import java.util.Properties;

/**
 * DeviceProperties
 *
 * @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 2018. 10. 11.
 */
public class DeviceProperties {
    //
    private static final String propertiesPath = "/device.properties";
    private Properties properties;

    public DeviceProperties() {
        //
        this.properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream(propertiesPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getManagedDeviceIds() {
        //
        return properties.getProperty("device.id.array").split(",");
    }
}
