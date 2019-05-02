package teamethernet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import teamethernet.api.API;
import teamethernet.database.NoiseData;
import teamethernet.database.NoiseDataRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class NoiseDataController {

    private static final Date beginning = new Date(0);

    @Autowired
    NoiseDataRepository noiseDataRepository;

    @GetMapping(path = "/")
    public String getStartPage() {
        return "index";
    }

    @GetMapping(path = "/api")
    public String getAPIPage() {
        return "API";
    }

    @GetMapping(path = "/visualization")
    public String getVisualization(Model model) {
        List<NoiseData> noiseData = getNoiseData();
        model.addAttribute("NoiseData", noiseData);
        return "visual";
    }

    private List<NoiseData> getNoiseData() {
        List<NoiseData> noiseData = new ArrayList<>();
        noiseDataRepository.findAll().addAll(noiseData);

        return noiseData;
    }

    @GetMapping(path = "/data", produces = {"application/json", "application/xml"})
    public @ResponseBody
    Iterable<NoiseData> getData(@RequestParam(name = "ids", required = false, defaultValue = "0") List<String> ids,
                                @RequestParam(name = "startDate", required = false, defaultValue = "0")
                                        long startDate,
                                @RequestParam(name = "endDate", required = false, defaultValue = "999999999999")
                                // TODO: change to today's date
                                        long endDate,
                                @RequestParam(name = "minNoiseLevel", required = false, defaultValue = "0")
                                        float minNoiseLevel,
                                @RequestParam(name = "maxNoiseLevel", required = false, defaultValue = "200")
                                        float maxNoiseLevel,
                                @RequestParam(name = "sortBy", required = false, defaultValue = "unixTime")
                                        String sortBy,
                                @RequestParam(name = "sortOrder", required = false, defaultValue = "asc")
                                        String sortOrder,
                                @RequestParam(name = "average", required = false, defaultValue = "false")
                                        boolean average) {

        Iterable<NoiseData> noiseData = noiseDataRepository.findAllWith(
                ids,
                minNoiseLevel,
                maxNoiseLevel,
                startDate,
                endDate,
                API.getSort(sortBy, sortOrder)
        );

        if(average){
            noiseData = API.getAverageNoiseData(noiseData, endDate);
        }

        return noiseData;
    }
}
