package teamethernet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teamethernet.database.NoiseDataRepository;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("data")
public class NoiseDataController {

    @Autowired
    NoiseDataRepository noiseDataRepository;

    @Autowired
    WebAPI webAPI;

    @CrossOrigin(origins = "*")
    @GetMapping(path = {"", "/{path}"}, produces = {"application/json"})
    public @ResponseBody
    Iterable<NoiseDataDTO> getNoiseData(
            @PathVariable(required = false)
                    Optional<String> path,
            @RequestParam(name = "ids", required = false, defaultValue = "")
                    final Collection<String> ids,
            @RequestParam(name = "startDate", required = false, defaultValue = "0")
                    final long startDate,
            @RequestParam(name = "endDate", required = false, defaultValue = "9999999999999")
                    final long endDate,
            @RequestParam(name = "minNoiseLevel", required = false, defaultValue = "0")
                    final float minNoiseLevel,
            @RequestParam(name = "maxNoiseLevel", required = false, defaultValue = "200")
                    final float maxNoiseLevel,
            @RequestParam(name = "limit", required = false, defaultValue = "2147483647")
                    final int limit,
            @RequestParam(name = "standardDeviationFilter", required = false, defaultValue = "2147483647")
                    final int standardDeviationFilter,
            @RequestParam(name = "sortBy", required = false, defaultValue = "t")
                    final WebAPI.SortBy sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "desc")
                    final WebAPI.SortOrder sortOrder) {

        return webAPI.findAllWith(
                path.orElse(""), ids, startDate, endDate, minNoiseLevel, maxNoiseLevel,
                limit, standardDeviationFilter, sortBy, sortOrder);
    }
}
