package teamethernet.database;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamethernet.web.NoiseData;
import teamethernet.web.NoiseDataRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class MqttSubscriber implements MqttCallback {
    private MqttClient client;

    @Autowired
    private NoiseDataRepository noiseDataRepository;

    @PostConstruct
    public void connect() {
        try {
            client = new MqttClient("tcp://130.229.171.198:1883", "Subscribing");
            client.connect();
            client.setCallback(this);
            client.subscribe("noisesensor/+/sensors");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws IOException {
        JsonNode jsonNode = new ObjectMapper().readTree(message.toString());

        NoiseData noiseData = new NoiseData(jsonNode.get("node_id").asText(), jsonNode.get("db").doubleValue());
        noiseDataRepository.save(noiseData);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub

    }

}