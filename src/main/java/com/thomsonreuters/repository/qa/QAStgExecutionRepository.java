package com.thomsonreuters.repository.qa;

import com.thomsonreuters.entities.StgExecutions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(QAStgExecutionRepository.BEAN_NAME)
public interface QAStgExecutionRepository extends JpaRepository<StgExecutions,Long> {

    /**
     * Denotes the bean name for this component
     */
    public static final String BEAN_NAME = "QAStgExecutionRepository";

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