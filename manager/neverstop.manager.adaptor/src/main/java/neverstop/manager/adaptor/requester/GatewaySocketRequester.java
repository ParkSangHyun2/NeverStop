

package neverstop.manager.adaptor.requester;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;

import neverstop.manager.entity.sensor.Sensor;

/**
 * GatewaySocketRequester
 *
 * @author @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 10/09/2018
 */
public class GatewaySocketRequester implements GatewayRequester {
    //
    private String gatewayIpAddress;

    public GatewaySocketRequester() {
        //
        this.gatewayIpAddress = "localhost";
    }

    public Sensor checkSensor(String deviceId) {
        //
        Socket socket = null;
        try {
            socket = new Socket(gatewayIpAddress, 18080);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            RequestMessage request = new RequestMessage("GET");
            request.getParams().add("test");

            Gson gson = new Gson();
            writer.write(gson.toJson(request));
            writer.flush();

            while (!reader.ready()) {
                String response = reader.readLine();
                reader.close();
                return gson.fromJson(response, Sensor.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }
}
