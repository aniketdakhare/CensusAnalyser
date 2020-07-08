package com.bridgelabz.indianstatecensusanalyser.services;

import com.bridgelabz.indianstatecensusanalyser.exception.CensusAnalyserException;
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
    private List<CensusDAO> jsonFileCreater(String sortedCensusData, String filePath)
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
        CensusDAO[] stateCensusMap = new Gson().fromJson(bufferedReader, CensusDAO[].class);
        List<CensusDAO> censusCSVList = Arrays.asList(stateCensusMap);
        return censusCSVList;
    }

    /**
     * METHOD TO SORT STATE CENSUS DATA
     * @param fieldName provides field name for sorting
     * @return sorted data
     */
    public String getSortedCensusData(String fieldName)
    {
        Comparator<CensusDAO> daoComparator;
        ArrayList censusList = null;
        switch (fieldName)
        {
            case "state":
                daoComparator = Comparator.comparing(census -> census.state );
                censusList = getSortedInAccendindOrder(daoComparator);
                break;
            case "stateCode":
                daoComparator = Comparator.comparing(census -> census.stateCode );
                censusList = getSortedInAccendindOrder(daoComparator);
                break;
            case "population":
                daoComparator = Comparator.comparing(census -> census.population );
                censusList = getSortedInDescendingOrder(daoComparator);
                break;
            case "populationDensity":
                daoComparator = Comparator.comparing(census -> census.populationDensity );
                censusList = getSortedInDescendingOrder(daoComparator);
                break;
            case "totalArea":
                daoComparator = Comparator.comparing(census -> census.totalArea );
                censusList = getSortedInDescendingOrder(daoComparator);
                break;
        }
        String sortedCensusData = new Gson().toJson(censusList);
        return sortedCensusData;
    }

    /**
     * METHOD TO SORT STATE CENSUS DATA IN ACCENDING ORDER
     * @param daoComparator provides data in Comparator format
     * @return sorted list
     */
    private ArrayList<CensusDAO> getSortedInAccendindOrder(Comparator<CensusDAO> daoComparator)
    {
        ArrayList censusList = censusMap.values().stream()
                .sorted(daoComparator)
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        return censusList;
    }

    /**
     * METHOD TO SORT STATE CENSUS DATA IN DESCENDING ORDER
     * @param daoComparator provides data in Comparator format
     * @return sorted list
     */
    private ArrayList<CensusDAO> getSortedInDescendingOrder(Comparator<CensusDAO> daoComparator)
    {
        ArrayList censusList = censusMap.values().stream()
                .sorted(daoComparator.reversed())
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        return censusList;
    }

    /**
     * METHOD TO SORT INDIAN STATE CENSUS DATA AND STORE IN JSON FILE
     * @param fieldName provides field name for process selection
     * @return sorted list
     * @throws CensusAnalyserException while handling the occurred exception
     */
    public List getJsonSortedCensusData(String fieldName) throws CensusAnalyserException
    {
        List censusList = null;
        switch (fieldName)
        {
            case "population":
                censusList = jsonFileCreater(this.getSortedCensusData(fieldName), SORTED_BY_POPULATION_JSON_PATH);
                break;
            case "populationDensity":
                censusList = jsonFileCreater(this.getSortedCensusData(fieldName), SORTED_BY_POPULATION_DENSITY_JSON_PATH);
            break;
            case "totalArea":
                censusList = jsonFileCreater(this.getSortedCensusData(fieldName), SORTED_BY_AREA_JSON_PATH);
            break;
        }
        return censusList;
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
        CensusDAO[] censusList1 = new Gson().fromJson(this.getSortedCensusData("populationDensity"), CensusDAO[].class);
        this.loadCensusData(Country.US, separator, csvFilePath[1]);
        CensusDAO[] censusList2 = new Gson().fromJson(this.getSortedCensusData("populationDensity"), CensusDAO[].class);
        if (Double.compare(censusList1[0].populationDensity, censusList2[0].populationDensity) > 0)
            return censusList1[0].state;
        return censusList2[0].state;
    }
}