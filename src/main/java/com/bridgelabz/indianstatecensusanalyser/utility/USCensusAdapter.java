package com.bridgelabz.indianstatecensusanalyser.utility;

import com.bridgelabz.indianstatecensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.CensusDAO;
import com.bridgelabz.indianstatecensusanalyser.model.USCensusCSV;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter
{
    /**
     * METHOD TO LOAD US STATE CENSUS DATA
     * @param separator provides the seperator for records in csv file
     * @param csvFilePath provides the path of file
     * @return map of loaded data
     * @throws CensusAnalyserException while handling the occurred exception
     */
    @Override
    public Map<String, CensusDAO> loadCensusData(char separator, String... csvFilePath)
            throws CensusAnalyserException
    {
        return super.loadCensusData(separator, USCensusCSV.class, csvFilePath);
    }
}