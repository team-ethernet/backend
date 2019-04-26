package teamethernet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class NoiseDataController {

    private static final Date beginning = new Date(0);

    @Autowired
    NoiseDataRepository noiseDataRepository;

    @GetMapping(path = "/visualization")
    public String getVisualization(Model model) {
        List<NoiseData> noiseData = getNoiseData();
        model.addAttribute("NoiseData", noiseData);
        return "visual";
    }

    private List<NoiseData> getNoiseData() {
        List<NoiseData> noiseData = new ArrayList<>();
        noiseDataRepository.findAll().forEach(noiseData::add);

        return noiseData;
    }

    @GetMapping(path = "/data", produces = {"application/json", "application/xml"})
    public @ResponseBody
    Iterable<NoiseData> getData(@RequestParam(name = "ids", required = false, defaultValue = "0") List<String> ids,
                                @RequestParam(name = "startDate", required = false, defaultValue = "2000-01-01_00:00")
                                @DateTimeFormat(pattern = "yyyy-MM-dd_HH:mm") Date startDate,
                                @RequestParam(name = "endDate", required = false, defaultValue = "9999-12-31_23:59")
                                // TODO: change to today's date
                                @DateTimeFormat(pattern = "yyyy-MM-dd_HH:mm") Date endDate,
                                @RequestParam(name = "minNoiseLevel", required = false, defaultValue = "0")
                                        Double minNoiseLevel,
                                @RequestParam(name = "maxNoiseLevel", required = false, defaultValue = "200")
                                        Double maxNoiseLevel,
                                @RequestParam(name = "sortBy", required = false, defaultValue = "date")
                                        String sortBy,
                                @RequestParam(name = "sortOrder", required = false, defaultValue = "asc")
                                        String sortOrder,
                                @RequestParam(name = "average", required = false, defaultValue = "false")
                                        boolean average) {

        Iterable<NoiseData> noiseData = noiseDataRepository.findAllWith(
                ids, minNoiseLevel, maxNoiseLevel, startDate, endDate, getSort(sortBy, sortOrder));

        if(average){
            noiseData = getAverageNoiseData(noiseData, endDate);
        }

        return noiseData;
    }

    private Date getEarliestDate(final Date date1, final Date date2) {
        return date1.before(date2) ? date1 : date2;
    }

    private Iterable<NoiseData> getAverageNoiseData(final Iterable<NoiseData> noiseData, final Date date) {
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

    private Sort getSort(final String sortBy, final String sortOrder) {
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
