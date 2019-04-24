package teamethernet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;

@Service
public class Broker implements Runnable {
    private Random random = new Random();

    @Autowired
    private NoiseDataRepository noiseDataRepository;

    @PostConstruct
    @Override
    public void run() {
        while (true) {
            try {
                final NoiseData noiseData = generateNoiseData();
                System.out.println("Look here --> " + noiseDataRepository);
                noiseDataRepository.save(noiseData);

                System.out.println("Hej");

                Thread.sleep(4000);
            } catch (InterruptedException ex) {
            }
        }
    }

    public NoiseData generateNoiseData() {
        final int id = random.nextInt();
        final double value = random.nextDouble();

        return new NoiseData(id, value);
    }

}
