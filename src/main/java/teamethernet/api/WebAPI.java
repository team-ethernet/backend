package teamethernet.api;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class WebAPI {

    public enum SortBy {bn, v, t}

    public enum SortOrder {asc, desc}

    @PersistenceContext
    private EntityManager entityManager;

    private static final String NO_PATH =
            "select r.bn, r.u, r.v, r.t %s";
    private static final String AVERAGE =
            "select r.bn, r.u, avg(r.v) v %s group by r.bn, r.u";
    private static final String MIN =
            "select r.bn, r.u, min(r.v) v, r.t %s group by r.bn, r.u";
    private static final String MAX =
            "select r.bn, r.u, max(r.v) v, r.t %s group by r.bn, r.u";

    @SuppressWarnings("unchecked")
    public List<NoiseDataDTO> findAllWith(final String path,
                                          final Collection<String> ids,
                                          final long startDate,
                                          final long endDate,
                                          final float minNoiseLevel,
                                          final float maxNoiseLevel,
                                          final int limit,
                                          final int standardDeviationFilter,
                                          final SortBy sortBy,
                                          final SortOrder sortOrder) {

        final String whereClause =
                (ids.isEmpty() ? "" : "r.bn in (:ids) and \n") +
                        "r.t >= :startDate and \n" +
                        "r.t <= :endDate and \n" +
                        "r.v >= :minNoiseLevel and \n" +
                        "r.v <= :maxNoiseLevel \n";

        final String template =
                "from noise_data r inner join \n" +
                        "(select r.bn, avg(r.v) avg, std(r.v) std from noise_data r \n" +
                        "where " + whereClause + " group by r.bn) r2 on r.bn = r2.bn \n" +
                        "where (abs(r.v - r2.avg) <= r2.std * :standardDeviationFilter) and \n" +
                        whereClause;

        final String queryType;
        switch (path) {
            case "average":
                queryType = AVERAGE;
                break;
            case "min":
                queryType = MIN;
                break;
            case "max":
                queryType = MAX;
                break;
            default:
                queryType = NO_PATH;
                break;
        }

        final String queryString = String.format(queryType, template) + " order by r." + sortBy + " " + sortOrder;
        final String resultSetMapping = "average".equals(path) ? "TimelessNoiseDataDTO" : "NoiseDataDTO";
        final Query query = entityManager.createNativeQuery(queryString, resultSetMapping);

        if (!ids.isEmpty()) {
            query.setParameter("ids", ids);
        }
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("minNoiseLevel", minNoiseLevel);
        query.setParameter("maxNoiseLevel", maxNoiseLevel);
        query.setParameter("standardDeviationFilter", standardDeviationFilter);


        return query.setMaxResults(limit).getResultList();
    }

}
