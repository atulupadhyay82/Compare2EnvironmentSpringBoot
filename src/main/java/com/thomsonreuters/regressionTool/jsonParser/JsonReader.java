package com.thomsonreuters.regressionTool.jsonParser;

import com.google.gson.Gson;
import com.thomsonreuters.Services.StagingService;
import com.thomsonreuters.regressionTool.pojoClasses.Root;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;

public class JsonReader {
    private static Logger logger = LoggerFactory.getLogger(JsonReader.class);

    public static File getProcessedJson(String jsonFile, String env) throws Exception {

            Gson gson = new Gson();
            File processedExtract =null;
            Root root = gson.fromJson(jsonFile, Root.class);
            boolean eval=validateExtract(root,env);
            if(!eval){
                return processedExtract;
            }
            if (root.getGroupingRule().equalsIgnoreCase("taxType")) {
                processedExtract= new HashMapForTreatmentComparsionByTaxTypeAndProductCategoryName(root).hashMapGenerator(env);

            } else if (root.getGroupingRule().equalsIgnoreCase("authority")) {
                //new HashMapGeneratorGroupByAuthority(root);
                processedExtract=  new HashMapForTreatmentComparsionByAuthorityAndProductCategoryName(root).hashMapGenerator(env);

            }
//            else if (root.getGroupingRule().equalsIgnoreCase("authorityType")) {
//                new HashMapForTreatmentComparsionByAuthorityTypeAndProductCategoryKey(root);
//            }
//            if(root.getmLocations()!=null){
//                new StoreMapper(root);
//            }
        return processedExtract;

    }

    private static boolean validateExtract(Root root,String env) {
        String extractName= root.getExtractName();
        String groupingRule=root.getGroupingRule();
        boolean eval=true;
        if(root.getAddresses()==null){
            logger.info(extractName+" does not have addresses data in "+env);
            eval=false;
        }
        else if(root.getProducts() ==null){
            logger.info(extractName+" does not have products data in "+env);
            eval=false;
        }
        else if(root.getTreatments()==null){
            logger.info(extractName+" does not have products data in "+env);
            eval=false;
        }
        else if((groupingRule.equalsIgnoreCase("Authority") || groupingRule.equalsIgnoreCase("AuthorityType")) && root.getmAuthorityTreatmentMappings()==null) {
            logger.info(extractName + " does not have authorities treatment data in " + env);
            eval = false;
        }
        else if(groupingRule.equalsIgnoreCase("taxType") && root.getJurisdictionTreatmentMappings()==null){
            logger.info(extractName+" does not have jurisdictions treatment data in "+env);
            eval=false;
        }
        return eval;

    }

}
