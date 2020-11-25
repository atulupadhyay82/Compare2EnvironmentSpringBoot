package com.thomsonreuters.regressionTool.jsonParser;

import com.thomsonreuters.regressionTool.excelOperations.ExcelWriter;
import com.thomsonreuters.regressionTool.pojoClasses.*;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class StoreMapper {

        Root root;
        HashMap<String, String> storeMapperMap = new HashMap<String,String>();
        HashMap<String, String> addressMapperMap = new HashMap<String, String>();


        public StoreMapper(Root root) {
            this.root = root;
            hashMapGenerator();
        }

        public void hashMapGenerator() {
            try {
                addressHashMapGenerator();
                storeHashMapGenerator();
                writeStoreMappingToFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        void storeHashMapGenerator() {

            for (Location loc : root.getmLocations()) {

                storeMapperMap.put(loc.getName(), addressMapperMap.get(loc.getAddressKey().toString()));
            }

}



        void addressHashMapGenerator() {
            List<Address> addr=root.getAddresses();
            Collections.sort(addr);
            String value="";
            for (Address a : addr){
                value=  a.getState()+"-"+a.getCounty()+"-"+a.getCity();
                if(a.getPostalRange()!=null)
                    value+="-"+a.getPostalRange().getBegin()+"-"+a.getPostalRange().getEnd();
                else
                    value+="-"+a.getPostalCode()+"-" + a.getGeocode();
                addressMapperMap.put(a.getAddressKey(), value);
            }
//            System.out.println(addressMapperMap);
        }



        void writeStoreMappingToFile() throws IOException, InvalidFormatException {
            String fileName = root.getExtractName()+"_Stores";
            List<String> keylist = new ArrayList<String>(storeMapperMap.keySet());
            Collections.sort(keylist);

            try {
                File myObj = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\jsonFiles\\" + fileName + ".txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            try {
                FileWriter myWriter = new FileWriter(System.getProperty("user.dir") + "\\src\\main\\resources\\jsonFiles\\" + fileName + ".txt");
                for (String key : keylist) {
                    String value= storeMapperMap.get(key);
                    myWriter.write(key + " : " + value);
                    myWriter.write(System.getProperty("line.separator"));
                }
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }


    }

