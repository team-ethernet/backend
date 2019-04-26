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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                                @RequestParam(name = "startDate", required = false, defaultValue = "2000-01-01")
                                @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                @RequestParam(name = "endDate", required = false, defaultValue = "9999-12-31")
                                // TODO: change to today's date
                                @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
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



        return noiseData;
    }

    private Date getEarliestDate(final Date date1, final Date date2){
        return date1.before(date2) ? date1 : date2;
    }

    private Iterable<NoiseData> getAverageNoiseData(final Iterable<NoiseData> noiseData, final Date date){
        date.setTime(getEarliestDate(date, new Date()).getTime());
        noiseData.forEach(data -> data.setDate(date));



        return noiseData;
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
