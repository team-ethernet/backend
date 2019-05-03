package teamethernet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teamethernet.api.WebAPI;
import teamethernet.database.NoiseData;
import teamethernet.database.NoiseDataRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NoiseDataController {

    @Autowired
    NoiseDataRepository noiseDataRepository;

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/data", produces = {"application/json"})
    public @ResponseBody
    List<NoiseDataDTO> getData(@RequestParam(name = "ids", required = false, defaultValue = "0") List<String> ids,
                                @RequestParam(name = "startDate", required = false, defaultValue = "0")
                                        long startDate,
                                @RequestParam(name = "endDate", required = false, defaultValue = "9999999999999")
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
                WebAPI.getSort(sortBy, sortOrder)
        );

        if(average){
            noiseData = WebAPI.getAverageNoiseData(noiseData, endDate);
        }

        final List<NoiseDataDTO> noiseDataDTOs = new ArrayList<>();
        noiseData.forEach(data -> noiseDataDTOs.add(NoiseDataDTO.fromEntity(data)));

        return noiseDataDTOs;
    }
}
