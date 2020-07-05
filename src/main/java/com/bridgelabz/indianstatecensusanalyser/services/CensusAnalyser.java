package com.bridgelabz.indianstatecensusanalyser.services;

import com.bridgelabz.indianstatecensusanalyser.exception.IndianStateCensusAndCodeAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indianstatecensusanalyser.model.CensusDAO;
import com.bridgelabz.indianstatecensusanalyser.model.USCensusCSV;
import com.google.gson.Gson;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser
{
    Map<String, CensusDAO> censusMap;
    private static final String SORTED_BY_POPULATION_JSON_PATH = "./IndiaStateCensusSortedByPopulation.json";
    private static final String SORTED_BY_POPULATION_DENSITY_JSON_PATH = "./IndiaStateCensusSortedByDensity.json";
    private static final String SORTED_BY_AREA_JSON_PATH = "./IndiaStateCensusSortedByArea.json";

    /**
     * METHOD TO LOAD INDIAN STATE CENSUS DATA
     * @param csvFilePath provides the path of file
     * @param separator provides the seperator for records in csv file
     * @return number of records
     * @throws IndianStateCensusAndCodeAnalyserException while handling the occurred exception
     */
    public int loadIndiaCensusData(char separator, String... csvFilePath)
            throws IndianStateCensusAndCodeAnalyserException
    {
        censusMap = new CensusLoader().loadCensusData(separator, IndiaCensusCSV.class, csvFilePath);
        return censusMap.size();
    }

    /**
     * METHOD TO LOAD US STATE CENSUS DATA
     * @param csvFilePath provides the path of file
     * @param separator provides the seperator for records in csv file
     * @return number of records
     * @throws IndianStateCensusAndCodeAnalyserException while handling the occurred exception
     */
    public int loadUSCensusData(char separator, String... csvFilePath) throws IndianStateCensusAndCodeAnalyserException
    {
        censusMap = new CensusLoader().loadCensusData(separator, USCensusCSV.class, csvFilePath);
        return censusMap.size();
    }


    /**
     * METHOD TO CREATE JSON FILE FOR INDIAN STATE CENSUS DATA
     * @return List if Json file
     */
    private List<IndiaCensusCSV> jsonFileCreater(String sortedCensusData, String filePath)
            throws IndianStateCensusAndCodeAnalyserException
    {
        BufferedReader bufferedReader;
        try(FileWriter writer = new FileWriter(filePath))
        {
            writer.write(sortedCensusData);
            bufferedReader = new BufferedReader(new FileReader(filePath));
        }
        catch (IOException | NullPointerException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException(e.getMessage(),
                    IndianStateCensusAndCodeAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        }
        IndiaCensusCSV[] stateCensusMap = new Gson().fromJson(bufferedReader, IndiaCensusCSV[].class);
        List<IndiaCensusCSV> censusCSVMap = Arrays.asList(stateCensusMap);
        return censusCSVMap;
    }

    /**
     * METHOD TO SORT INDIAN STATE CENSUS DATA AS PER STATE
     */
    public String getSortedCensusDataAsPerState()
    {
        List<CensusDAO> censusList = censusMap.values().stream()
                .sorted(((Comparator<CensusDAO>) (censusData1, censusData2) -> censusData2
                        .state.compareTo(censusData1.state)).reversed())
                .collect(Collectors.toList());
        String sortedCensusData = new Gson().toJson(censusList);
        return sortedCensusData;
    }

    /**
     * METHOD TO SORT INDIAN STATE CODE DATA AS PER STATE
     */
    public String getSortedStateCodeDataAsPerState()
    {
        List<CensusDAO> stateCodeList = censusMap.values().stream()
                .sorted(((Comparator<CensusDAO>) (stateCodeData1, stateCodeData2) -> stateCodeData2
                        .stateCode.compareTo(stateCodeData1.stateCode)).reversed())
                .collect(Collectors.toList());
        String sortedStateCodeData = new Gson().toJson(stateCodeList);
        return sortedStateCodeData;
    }

    /**
     * METHOD TO SORT INDIAN STATE CENSUS DATA AS PER POPULATION
     * @return size of sorted Json file
     */
    public int getJsonSortedCensusDataAsPerPopulation() throws IndianStateCensusAndCodeAnalyserException
    {
        return jsonFileCreater(this.getSortedCensusDataAsPerPopulation(), SORTED_BY_POPULATION_JSON_PATH).size();
    }

    /**
     * METHOD TO SORT INDIAN STATE CENSUS DATA AS PER POPULATION DENSITY
     * @return List of sorted Json file
     */
    public List<IndiaCensusCSV> getJsonSortedCensusDataAsPerPopulationDensity()
            throws IndianStateCensusAndCodeAnalyserException
    {
        return jsonFileCreater(this.getSortedCensusDataAsPerPopulationDensity(), SORTED_BY_POPULATION_DENSITY_JSON_PATH);
    }

    /**
     * METHOD TO SORT INDIAN STATE CENSUS DATA AS PER AREA
     * @return List of sorted Json file
     */
    public List<IndiaCensusCSV> getJsonSortedCensusDataAsPerAreaInSquareKm()
            throws IndianStateCensusAndCodeAnalyserException
    {
        return jsonFileCreater(this.getSortedCensusDataAsPerTotalArea(), SORTED_BY_AREA_JSON_PATH);
    }

    /**
     * METHOD TO SORT US STATE CENSUS DATA AS PER POPULATION
     */
    public String getSortedCensusDataAsPerPopulation()
    {
        List<CensusDAO> censusList = censusMap.values().stream()
                .sorted((censusData1, censusData2) -> censusData2.population.compareTo(censusData1.population))
                .collect(Collectors.toList());
        String sortedCensusData = new Gson().toJson(censusList);
        return sortedCensusData;
    }

    /**
     * METHOD TO SORT US STATE CENSUS DATA AS PER POPULATION DENSITY
     */
    public String getSortedCensusDataAsPerPopulationDensity()
    {
        List<CensusDAO> censusList = censusMap.values().stream()
                .sorted((censusData1, censusData2) ->
                        (int) (censusData2.populationDensity - censusData1.populationDensity))
                .collect(Collectors.toList());
        String sortedCensusData = new Gson().toJson(censusList);
        return sortedCensusData;
    }

    /**
     * METHOD TO SORT US STATE CENSUS DATA AS PER TOTAL AREA
     */
    public String getSortedCensusDataAsPerTotalArea()
    {
        List<CensusDAO> censusList = censusMap.values().stream()
                .sorted((censusData1, censusData2) -> (int) (censusData2.totalArea - censusData1.totalArea))
                .collect(Collectors.toList());
        String sortedCensusData = new Gson().toJson(censusList);
        return sortedCensusData;
    }

    /**
     * METHOD TO GET MOST DENSELY POPULATED STATE AMONG INDIA AND US
     * @param csvFilePath provides CSV file path for IndiaCensusCSV and USCensusCSV file
     * @param separator provides the seperator for records in csv file
     * @return most densely populated state
     * @throws IndianStateCensusAndCodeAnalyserException while handling the occurred exception
     */
    public String getMostDenselyPopulatedState(char separator, String... csvFilePath)
            throws IndianStateCensusAndCodeAnalyserException
    {
        this.loadIndiaCensusData(separator, csvFilePath[0]);
        CensusDAO[] censusList1 = new Gson().fromJson(this.getSortedCensusDataAsPerPopulationDensity(), CensusDAO[].class);
        this.loadUSCensusData(separator, csvFilePath[1]);
        CensusDAO[] censusList2 = new Gson().fromJson(this.getSortedCensusDataAsPerPopulationDensity(), CensusDAO[].class);
        if (Double.compare(censusList1[0].populationDensity, censusList2[0].populationDensity) > 0)
            return censusList1[0].state;
        return censusList2[0].state;
    }
}