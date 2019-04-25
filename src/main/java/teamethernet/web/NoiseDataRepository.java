package teamethernet.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface NoiseDataRepository extends JpaRepository<NoiseData, Integer> {

    @Query("select row from NoiseData row where ('0' in (:ids) or row.noiseSensorId in (:ids)) and " +
            "row.value <= :maxNoiseLevel and " +
            "row.value >= :minNoiseLevel and " +
            "row.date >= :startDate and " +
            "row.date <= :endDate")
    Iterable<NoiseData> findAllWith(@Param("ids") List<String> ids,
                                    @Param("minNoiseLevel") double minNoiseLevel,
                                    @Param("maxNoiseLevel") double maxNoiseLevel,
                                    @Param("startDate") Date startDate,
                                    @Param("endDate") Date endDate);




}
