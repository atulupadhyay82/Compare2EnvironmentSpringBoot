package com.thomsonreuters.repository.env1;


import com.thomsonreuters.entities.CFGExtract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(Env1CfgExtractRepository.BEAN_NAME)
public interface Env1CfgExtractRepository extends JpaRepository<CFGExtract,Long> {

    /**
     * Denotes the bean name for this component
     */
    public static final String BEAN_NAME = "Env1CfgExtractRepository";

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