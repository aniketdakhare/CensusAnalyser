package com.bridgelabz.indianstatecensusanalyser.utility;

import com.bridgelabz.indianstatecensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.CensusDAO;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaCensusCSV;

import java.util.Map;

public class IndiaCensusAdapter extends CensusAdapter
{
    /**
     * METHOD TO LOAD INDIA STATE CENSUS DATA
     * @param separator provides the seperator for records in csv file
     * @param csvFilePath provides the path of file
     * @return map of loaded data
     * @throws CensusAnalyserException while handling the occurred exception
     */
    @Override
    public Map<String, CensusDAO> loadCensusData(char separator, String... csvFilePath)
            throws CensusAnalyserException
    {
        return super.loadCensusData(separator, IndiaCensusCSV.class, csvFilePath);
    }
}