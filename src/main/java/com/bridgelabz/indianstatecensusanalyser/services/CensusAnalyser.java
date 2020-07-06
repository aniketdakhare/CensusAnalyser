package com.bridgelabz.indianstatecensusanalyser.services;

import com.bridgelabz.indianstatecensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indianstatecensusanalyser.model.CensusDAO;
import com.bridgelabz.indianstatecensusanalyser.utility.CensusAdapterFactory;
import com.google.gson.Gson;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser
{
    public enum Country
    {
        INDIA, US
    }

    private Country country;
    Map<String, CensusDAO> censusMap;
    private static final String SORTED_BY_POPULATION_JSON_PATH = "./IndiaStateCensusSortedByPopulation.json";
    private static final String SORTED_BY_POPULATION_DENSITY_JSON_PATH = "./IndiaStateCensusSortedByDensity.json";
    private static final String SORTED_BY_AREA_JSON_PATH = "./IndiaStateCensusSortedByArea.json";

    public CensusAnalyser(Country country)
    {
        this.country = country;
    }

    /**
     * METHOD TO LOAD CENSUS DATA
     * @param csvFilePath provides the path of file
     * @param separator provides the seperator for records in csv file
     * @return number of records
     * @throws CensusAnalyserException while handling the occurred exception
     */
    public int loadCensusData(Country country, char separator, String... csvFilePath)
            throws CensusAnalyserException
    {
        censusMap = CensusAdapterFactory.getCensusDataObject(country, separator, csvFilePath);
        return censusMap.size();
    }

    /**
     * METHOD TO CREATE JSON FILE FOR INDIAN STATE CENSUS DATA
     * @return List if Json file
     */
    private List<IndiaCensusCSV> jsonFileCreater(String sortedCensusData, String filePath)
            throws CensusAnalyserException
    {
        BufferedReader bufferedReader;
        try(FileWriter writer = new FileWriter(filePath))
        {
            writer.write(sortedCensusData);
            bufferedReader = new BufferedReader(new FileReader(filePath));
        }
        catch (IOException | NullPointerException e)
        {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
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
        Comparator<CensusDAO> daoComparator = Comparator.comparing(census -> census.state );
        ArrayList censusList = censusMap.values().stream()
                .sorted(daoComparator)
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        String sortedCensusData = new Gson().toJson(censusList);
        return sortedCensusData;
    }

    /**
     * METHOD TO SORT INDIAN STATE CODE DATA AS PER STATE
     */
    public String getSortedStateCodeDataAsPerState()
    {
        ArrayList stateCodeList = censusMap.values().stream()
                .sorted(((Comparator<CensusDAO>) (stateCodeData1, stateCodeData2) -> stateCodeData2
                        .stateCode.compareTo(stateCodeData1.stateCode)).reversed())
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        String sortedStateCodeData = new Gson().toJson(stateCodeList);
        return sortedStateCodeData;
    }

    /**
     * METHOD TO SORT INDIAN STATE CENSUS DATA AS PER POPULATION
     * @return size of sorted Json file
     */
    public int getJsonSortedCensusDataAsPerPopulation() throws CensusAnalyserException
    {
        return jsonFileCreater(this.getSortedCensusDataAsPerPopulation(), SORTED_BY_POPULATION_JSON_PATH).size();
    }

    /**
     * METHOD TO SORT INDIAN STATE CENSUS DATA AS PER POPULATION DENSITY
     * @return List of sorted Json file
     */
    public List<IndiaCensusCSV> getJsonSortedCensusDataAsPerPopulationDensity()
            throws CensusAnalyserException
    {
        return jsonFileCreater(this.getSortedCensusDataAsPerPopulationDensity(), SORTED_BY_POPULATION_DENSITY_JSON_PATH);
    }

    /**
     * METHOD TO SORT INDIAN STATE CENSUS DATA AS PER AREA
     * @return List of sorted Json file
     */
    public List<IndiaCensusCSV> getJsonSortedCensusDataAsPerAreaInSquareKm()
            throws CensusAnalyserException
    {
        return jsonFileCreater(this.getSortedCensusDataAsPerTotalArea(), SORTED_BY_AREA_JSON_PATH);
    }

    /**
     * METHOD TO SORT US STATE CENSUS DATA AS PER POPULATION
     */
    public String getSortedCensusDataAsPerPopulation()
    {
        ArrayList censusList = censusMap.values().stream()
                .sorted((censusData1, censusData2) -> censusData2.population.compareTo(censusData1.population))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        String sortedCensusData = new Gson().toJson(censusList);
        return sortedCensusData;
    }

    /**
     * METHOD TO SORT US STATE CENSUS DATA AS PER POPULATION DENSITY
     */
    public String getSortedCensusDataAsPerPopulationDensity()
    {
        ArrayList censusList = censusMap.values().stream()
                .sorted((censusData1, censusData2) ->
                        (int) (censusData2.populationDensity - censusData1.populationDensity))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        String sortedCensusData = new Gson().toJson(censusList);
        return sortedCensusData;
    }

    /**
     * METHOD TO SORT US STATE CENSUS DATA AS PER TOTAL AREA
     */
    public String getSortedCensusDataAsPerTotalArea()
    {
        ArrayList censusList = censusMap.values().stream()
                .sorted((censusData1, censusData2) -> (int) (censusData2.totalArea - censusData1.totalArea))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        String sortedCensusData = new Gson().toJson(censusList);
        return sortedCensusData;
    }

    /**
     * METHOD TO GET MOST DENSELY POPULATED STATE AMONG INDIA AND US
     * @param csvFilePath provides CSV file path for IndiaCensusCSV and USCensusCSV file
     * @param separator provides the seperator for records in csv file
     * @return most densely populated state
     * @throws CensusAnalyserException while handling the occurred exception
     */
    public String getMostDenselyPopulatedState(char separator, String... csvFilePath)
            throws CensusAnalyserException
    {
        this.loadCensusData(Country.INDIA, separator, csvFilePath[0]);
        CensusDAO[] censusList1 = new Gson().fromJson(this.getSortedCensusDataAsPerPopulationDensity(), CensusDAO[].class);
        this.loadCensusData(Country.US, separator, csvFilePath[1]);
        CensusDAO[] censusList2 = new Gson().fromJson(this.getSortedCensusDataAsPerPopulationDensity(), CensusDAO[].class);
        if (Double.compare(censusList1[0].populationDensity, censusList2[0].populationDensity) > 0)
            return censusList1[0].state;
        return censusList2[0].state;
    }
}