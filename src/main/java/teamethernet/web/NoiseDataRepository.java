package teamethernet.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface NoiseDataRepository extends JpaRepository<NoiseData, Integer> {

    @Query("select row from NoiseData row where (:id is null or row.noiseSensorId = :id) and " +
            "row.value <= :maxNoiseLevel and " +
            "row.value >= :minNoiseLevel and " +
            "row.date >= :startDate and " +
            "row.date <= :endDate")
    Iterable<NoiseData> findAllWith(@Param("id") String id,
                                    @Param("minNoiseLevel") double minNoiseLevel,
                                    @Param("maxNoiseLevel") double maxNoiseLevel,
                                    @Param("startDate") Date startDate,
                                    @Param("endDate") Date endDate);




}
