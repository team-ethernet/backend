package teamethernet.database;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoiseDataRepository extends JpaRepository<NoiseData, Integer> {

    @Query("select row from NoiseData row where ('0' in (:ids) or row.name in (:ids)) and " +
            "row.value <= :maxNoiseLevel and " +
            "row.value >= :minNoiseLevel and " +
            "row.unixTime >= :startDate and " +
            "row.unixTime <= :endDate")
    Iterable<NoiseData> findAllWith(@Param("ids") List<String> ids,
                                    @Param("minNoiseLevel") float minNoiseLevel,
                                    @Param("maxNoiseLevel") float maxNoiseLevel,
                                    @Param("startDate") long startDate,
                                    @Param("endDate") long endDate,
                                    Sort sort);

}
