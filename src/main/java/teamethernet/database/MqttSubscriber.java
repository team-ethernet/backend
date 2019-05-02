package teamethernet.database;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@Service
public class MqttSubscriber implements MqttCallback {
    private MqttClient client;

    @Autowired
    private NoiseDataRepository noiseDataRepository;

    @PostConstruct
    public void connect() {
        try {
            client = new MqttClient("tcp://130.229.171.198:1883", "dbSub" + UUID.randomUUID());
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
        // TODO: Use convertSenMLToNoiseData to create NoiseData
        JsonNode jsonNode = new ObjectMapper().readTree(message.toString());
        NoiseData noiseData = new NoiseData(jsonNode.get("node_id").asText(), "dB", jsonNode.get("db").intValue());

        noiseDataRepository.save(noiseData);
    }

    private NoiseData convertSenMLToNoiseData (final MqttMessage message) throws IOException {
        JsonNode jsonNode = new ObjectMapper().readTree(message.toString());
        final String name = jsonNode.get("n").asText();
        final String unit = jsonNode.get("u").asText();
        final float value = jsonNode.get("v").floatValue();

        return new NoiseData(name, unit, value);
    }

}