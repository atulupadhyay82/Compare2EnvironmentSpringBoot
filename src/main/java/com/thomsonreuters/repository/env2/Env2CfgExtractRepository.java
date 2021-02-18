package com.thomsonreuters.repository.env2;

import com.thomsonreuters.entities.CFGExtract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(Env2CfgExtractRepository.BEAN_NAME)
public interface Env2CfgExtractRepository extends JpaRepository<CFGExtract,Long> {

    /**
     * Denotes the bean name for this component
     */
    public static final String BEAN_NAME = "Env2CfgExtractRepository";

    /**
     * Retreive taxtype from the database for the extract
     *
     * @param extractName
     *
     * @return
     */
    @Query(value="SELECT EXTRACT_ID FROM EX_CFG_EXTRACTS WHERE NAME = :extractName",nativeQuery = true)
    Long getExtractID(@Param("extractName") final String extractName);
}