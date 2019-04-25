package teamethernet.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface NoiseDataRepository extends JpaRepository<NoiseData, Integer> {

    @Query("select row from NoiseData row where row.date <= :date")
    List<NoiseData> findAllWithDateTimeBefore(@Param("date") Date date);

    @Query("select row from NoiseData row where row.date >= :date")
    List<NoiseData> findAllWithDateTimeAfter(@Param("date") Date date);


}
