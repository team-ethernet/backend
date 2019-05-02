package teamethernet.api;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import teamethernet.web.NoiseData;

import java.util.*;

public interface API {

    static Iterable<NoiseData> getAverageNoiseData(final Iterable<NoiseData> noiseData, final Date date) {
        date.setTime(getEarliestDate(date, new Date()).getTime());

        Map<String, List<Integer>> idToNoiseData = new HashMap<>();
        for (final NoiseData row : noiseData) {
            if (!idToNoiseData.containsKey(row.getName())) {
                idToNoiseData.put(row.getName(), new ArrayList<>());
            }
            idToNoiseData.get(row.getName()).add(row.getValue());
        }

        final List<NoiseData> averageNoiseData = new ArrayList<>();

        int globalAverage = 0;

        for (final String key : idToNoiseData.keySet()) {
            int sum = 0;
            for (int keyValue : idToNoiseData.get(key)) {
                sum += keyValue;
            }
            final int average = sum / idToNoiseData.get(key).size();
            averageNoiseData.add(new NoiseData(key, "dB", average, date));

            globalAverage += average;
        }

        globalAverage /= idToNoiseData.size();
        averageNoiseData.add(new NoiseData("global average", "dB", globalAverage, date));

        return averageNoiseData;
    }

    static Date getEarliestDate(final Date date1, final Date date2) {
        return date1.before(date2) ? date1 : date2;
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
