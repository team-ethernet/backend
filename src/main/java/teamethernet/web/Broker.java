package teamethernet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;

@Service
public class Broker implements Runnable {
    private Random random = new Random();

    private static final int[] ids = {
            929999847,
            515676268,
            224848244,
            594341611,
            935869279,
            568867946,
            153821318,
            406995257,
            592054277,
            684384942
    };

    @Autowired
    private NoiseDataRepository noiseDataRepository;

    @Autowired
    private TaskExecutor taskExecutor;

    @PostConstruct
    private void init() {
        taskExecutor.execute(this);
    }

    @Override
    public void run() {
        while (true) {
            try {
                final NoiseData noiseData = generateNoiseData();
                noiseDataRepository.save(noiseData);

                Thread.sleep(4000);
            } catch (InterruptedException ex) {
            }
        }
    }

    public NoiseData generateNoiseData() {
        final int id_index = random.nextInt(10);
        final double value = random.nextDouble() * 100;

        return new NoiseData(ids[id_index], value);
    }

}
