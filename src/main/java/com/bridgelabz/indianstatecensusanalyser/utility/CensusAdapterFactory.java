package com.bridgelabz.indianstatecensusanalyser.utility;

import com.bridgelabz.indianstatecensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.CensusDAO;
import com.bridgelabz.indianstatecensusanalyser.services.CensusAnalyser;

import java.util.Map;

public class CensusAdapterFactory
{
    /**
     * METHOD TO INVOKE THE CENSUS ADAPTER AS PER COUNTRY
     * @param country provides country to load data
     * @return object of a required adapter class
     * @throws CensusAnalyserException while handling the occurred exception
     */
    public static Map<String, CensusDAO> getCensusDataObject(CensusAnalyser.Country country, char separator,
                                                             String... csvFilePath) throws CensusAnalyserException
    {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(separator, csvFilePath);
        return new USCensusAdapter().loadCensusData(separator, csvFilePath);
    }
}