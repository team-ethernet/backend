package teamethernet.api;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import teamethernet.web.NoiseData;

import java.util.*;

public interface API {

    static Iterable<NoiseData> getAverageNoiseData(final Iterable<NoiseData> noiseData, final Date date) {
        date.setTime(getEarliestDate(date, new Date()).getTime());

        Map<String, List<Double>> idToNoiseData = new HashMap<>();
        for (final NoiseData row : noiseData) {
            if (!idToNoiseData.containsKey(row.getNoiseSensorId())) {
                idToNoiseData.put(row.getNoiseSensorId(), new ArrayList<>());
            }
            idToNoiseData.get(row.getNoiseSensorId()).add(row.getValue());
        }

        final List<NoiseData> averageNoiseData = new ArrayList<>();

        double globalAverage = 0;

        for (final String key : idToNoiseData.keySet()) {
            double sum = 0;
            for(Double keyValue : idToNoiseData.get(key)){
                sum += keyValue;
            }
            final double average = sum / idToNoiseData.get(key).size();
            averageNoiseData.add(new NoiseData(key, date, average));

            globalAverage += average;
        }

        globalAverage /= idToNoiseData.size();
        averageNoiseData.add(new NoiseData("global average", date, globalAverage));

        return averageNoiseData;
    }

    static Date getEarliestDate(final Date date1, final Date date2) {
        return date1.before(date2) ? date1 : date2;
    }

    static Sort getSort(final String sortBy, final String sortOrder) {
        Sort sort;
        switch (sortOrder) {
            case "desc":
                sort = JpaSort.by(sortBy).descending();
                break;
            default:
                sort = JpaSort.by(sortBy).ascending();
                break;
        }

        return sort;
    }

}
