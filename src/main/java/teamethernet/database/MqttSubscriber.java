package teamethernet.database;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamethernet.senmlapi.CborFormatter;
import teamethernet.senmlapi.Label;
import teamethernet.senmlapi.SenMLAPI;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MqttSubscriber implements MqttCallback {
    private MqttClient client;

    @Autowired
    private NoiseDataRepository noiseDataRepository;

    @PostConstruct
    public void connect() {
        try {
            client = new MqttClient("tcp://130.229.142.52:1883", "dbSub" + UUID.randomUUID());
            client.connect();
            client.setCallback(this);
            client.subscribe("#");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        // TODO: Auto-generated method stub
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO: Auto-generated method stub
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws IOException {
        final List<NoiseData> noiseData = convertSenMLToNoiseData(message);
        noiseDataRepository.saveAll(noiseData);
    }

    private List<NoiseData> convertSenMLToNoiseData (final MqttMessage message) throws IOException {
        final SenMLAPI<CborFormatter> senMLAPI = SenMLAPI.initCborDecode(message.getPayload());

        final List<NoiseData> noiseData = new ArrayList<>();
        for (int i = 0; i < senMLAPI.getRecords().size(); i++) {
            final String name = senMLAPI.getValue(Label.BASE_NAME, i);
            final String unit = senMLAPI.getValue(Label.UNIT, i);
            final double value = senMLAPI.getValue(Label.VALUE, i);

            noiseData.add(new NoiseData(name, unit, value));
        }

        return noiseData;
    }

}