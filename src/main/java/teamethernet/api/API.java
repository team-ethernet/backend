package teamethernet.api;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import teamethernet.database.NoiseData;

import java.util.*;

public interface API {

    static Iterable<NoiseData> getAverageNoiseData(final Iterable<NoiseData> noiseData, long unixTime) {
        final long today = new Date().getTime();
        unixTime = today < unixTime ? today : unixTime;

        Map<String, List<Float>> idToNoiseData = new HashMap<>();
        for (final NoiseData row : noiseData) {
            if (!idToNoiseData.containsKey(row.getName())) {
                idToNoiseData.put(row.getName(), new ArrayList<>());
            }
            idToNoiseData.get(row.getName()).add(row.getValue());
        }

        final List<NoiseData> averageNoiseData = new ArrayList<>();

        float globalAverage = 0;

        for (final String key : idToNoiseData.keySet()) {
            int sum = 0;
            for (float keyValue : idToNoiseData.get(key)) {
                sum += keyValue;
            }
            final float average = sum / idToNoiseData.get(key).size();
            averageNoiseData.add(new NoiseData(key, "dB", average, unixTime));

            globalAverage += average;
        }

        globalAverage /= idToNoiseData.size();
        averageNoiseData.add(new NoiseData("global average", "dB", globalAverage, unixTime));

        return averageNoiseData;
    }

    static Sort getSort(final String sortBy, final String sortOrder) {
        Sort sort;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            sort = JpaSort.by(sortBy).descending();
        } else {
            sort = JpaSort.by(sortBy).ascending();
        }

        return sort;
    }

}
