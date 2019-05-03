package teamethernet.database;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            client = new MqttClient("tcp://130.229.180.105:1883", "dbSub" + UUID.randomUUID());
            client.connect();
            client.setCallback(this);
            client.subscribe("noisesensor/+/sensors");
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
        // TODO: Change this line tp use SenMLAPI.convertCBORToJSON(string)
        JsonNode jsonNodes = new ObjectMapper().readTree(message.toString());

        final List<NoiseData> noiseData = new ArrayList<>();
        for (JsonNode jsonNode : jsonNodes) {
            final String name = jsonNode.get("bn").asText();
            final String unit = jsonNode.get("u").asText();
            final float value = jsonNode.get("v").floatValue();

            noiseData.add(new NoiseData(name, unit, value));
        }

        return noiseData;
    }

}