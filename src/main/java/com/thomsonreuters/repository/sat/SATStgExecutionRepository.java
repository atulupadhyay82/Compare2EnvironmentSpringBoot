package com.thomsonreuters.repository.sat;

import com.thomsonreuters.entities.StgExecutions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(SATStgExecutionRepository.BEAN_NAME)
public interface SATStgExecutionRepository extends JpaRepository<StgExecutions,Long> {

    /**
     * Denotes the bean name for this component
     */
    public static final String BEAN_NAME = "SATStgExecutionDao";

    /**
     * Retreive taxtype from the database for the extract
     *
     * @param extractID
     *
     * @return
     */
    @Query(value="SELECT STATUS FROM EX_STG_EXECUTIONS WHERE EXTRACT_ID= :extractID "+
            "AND CONTENT_VERSION >= (SELECT max(CONTENT_VERSION) FROM EX_STG_EXECUTIONS ese2 WHERE EXTRACT_ID = :extractID)",nativeQuery = true)
    String getStatus(@Param("extractID") final Long extractID);
}