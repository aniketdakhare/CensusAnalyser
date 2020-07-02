package com.bridgelabz.indianstatecensusanalyser.services;

import com.bridgelabz.csvbuilderjar.CSVBuilderException;
import com.bridgelabz.csvbuilderjar.CSVBuilderFactory;
import com.bridgelabz.indianstatecensusanalyser.exception.IndianStateCensusAndCodeAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indianstatecensusanalyser.model.CensusDAO;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.indianstatecensusanalyser.model.USCensusCSV;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IndianStateCensusAndCodeAnalyser
{
    Map<String, CensusDAO> censusMap;
    Map<String, CensusDAO> stateCodeMap;
    private static final String SORTED_BY_POPULATION_JSON_PATH = "./IndiaStateCensusSortedByPopulation.json";
    private static final String SORTED_BY_POPULATION_DENSITY_JSON_PATH = "./IndiaStateCensusSortedByDensity.json";
    private static final String SORTED_BY_AREA_JSON_PATH = "./IndiaStateCensusSortedByArea.json";

    public IndianStateCensusAndCodeAnalyser()
    {
        this.censusMap = new HashMap<>();
        this.stateCodeMap = new HashMap<>();
    }

    /**
     * METHOD TO LOAD INDIAN STATE CENSUS DATA
     * Note:- Pass argument as '0' for OpenCSV and '1' for CommonCSV in createCSVBuilder method
     * @param csvFilePath provides the path of file
     * @param separator provides the seperator for records in csv file
     * @return number of records
     * @throws IndianStateCensusAndCodeAnalyserException while handling the occurred exception
     */
    public int loadIndiaCensusData(String csvFilePath, char separator) throws IndianStateCensusAndCodeAnalyserException
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            Iterator<IndiaCensusCSV> censusIterator = CSVBuilderFactory.createCSVBuilder(0)
                    .getCSVFileIterator(reader, IndiaCensusCSV.class, separator);
            Iterable<IndiaCensusCSV> csvIterable = () -> censusIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .forEach(csvState -> censusMap.put(csvState.state, new CensusDAO(csvState)));
            return censusMap.size();
        }
        catch (NoSuchFileException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException("Entered wrong file name/path or wrong file extension",
                    IndianStateCensusAndCodeAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        }
        catch (IOException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException(e.getMessage(),
                    IndianStateCensusAndCodeAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        }
        catch (RuntimeException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException("Entered incorrect Delimiter or incorrect Header",
                    IndianStateCensusAndCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER);
        }
        catch (CSVBuilderException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException(e.getMessage(), e.type.name());
        }
    }

    /**
     * METHOD TO LOAD INDIAN STATE CENSUS DATA
     * Note:- Pass argument as '0' for OpenCSV and '1' for CommonCSV in createCSVBuilder method
     * @param csvFilePath provides the path of file
     * @param separator provides the seperator for records in csv file
     * @return number of records
     * @throws IndianStateCensusAndCodeAnalyserException while handling the occurred exception
     */
    public int loadIndiaStateCode(String csvFilePath, char separator) throws IndianStateCensusAndCodeAnalyserException
    {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            Iterator<IndiaStateCodeCSV> stateCodeIterator = CSVBuilderFactory.createCSVBuilder(0)
                    .getCSVFileIterator(reader, IndiaStateCodeCSV.class, separator);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCodeIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .forEach(csvState -> stateCodeMap.put(csvState.StateName, new CensusDAO(csvState)));
            return stateCodeMap.size();
        }
        catch (NoSuchFileException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException("Entered wrong file name/path or wrong file extension",
                    IndianStateCensusAndCodeAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        }
        catch (IOException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException(e.getMessage(),
                    IndianStateCensusAndCodeAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        }
        catch (RuntimeException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException("Entered incorrect Delimiter or incorrect Header",
                    IndianStateCensusAndCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER);
        }
        catch (CSVBuilderException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException(e.getMessage(), e.type.name());
        }
    }

    /**
     * METHOD TO LOAD US STATE CENSUS DATA
     * Note:- Pass argument as '0' for OpenCSV and '1' for CommonCSV in createCSVBuilder method
     * @param csvFilePath provides the path of file
     * @param separator provides the seperator for records in csv file
     * @return number of records
     * @throws IndianStateCensusAndCodeAnalyserException while handling the occurred exception
     */
    public int loadUSCensusData(String csvFilePath, char separator) throws IndianStateCensusAndCodeAnalyserException
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            Iterator<USCensusCSV> censusIterator = CSVBuilderFactory.createCSVBuilder(0)
                    .getCSVFileIterator(reader, USCensusCSV.class, separator);
            Iterable<USCensusCSV> csvIterable = () -> censusIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .forEach(csvState -> censusMap.put(csvState.state, new CensusDAO(csvState)));
            return censusMap.size();
        }
        catch (NoSuchFileException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException("Entered wrong file name/path or wrong file extension",
                    IndianStateCensusAndCodeAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        }
        catch (IOException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException(e.getMessage(),
                    IndianStateCensusAndCodeAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        }
        catch (RuntimeException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException("Entered incorrect Delimiter or incorrect Header",
                    IndianStateCensusAndCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER);
        }
        catch (CSVBuilderException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException(e.getMessage(), e.type.name());
        }
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
        List<CensusDAO> stateCodeList = stateCodeMap.values().stream()
                .sorted(((Comparator<CensusDAO>) (stateCodeData1, stateCodeData2) -> stateCodeData2
                .StateCode.compareTo(stateCodeData1.StateCode)).reversed())
                .collect(Collectors.toList());
        String sortedStateCodeData = new Gson().toJson(stateCodeList);
        return sortedStateCodeData;
    }

    /**
     * METHOD TO SORT INDIAN STATE CENSUS DATA AS PER POPULATION
     * @return size of sorted Json file
     */
    public int getSortedCensusDataAsPerPopulation() throws IndianStateCensusAndCodeAnalyserException
    {
        List<CensusDAO> censusList = censusMap.values().stream()
                .sorted((censusData1, censusData2) -> censusData2.population.compareTo(censusData1.population))
                .collect(Collectors.toList());
        String sortedCensusData = new Gson().toJson(censusList);
        return jsonFileCreater(sortedCensusData, SORTED_BY_POPULATION_JSON_PATH).size();
    }

    /**
     * METHOD TO SORT INDIAN STATE CENSUS DATA AS PER POPULATION DENSITY
     * @return List of sorted Json file
     */
    public List<IndiaCensusCSV> getSortedCensusDataAsPerPopulationDensity()
            throws IndianStateCensusAndCodeAnalyserException
    {
        List<CensusDAO> censusList = censusMap.values().stream()
                .sorted((censusData1, censusData2) -> censusData2.densityPerSqKm.compareTo(censusData1.densityPerSqKm))
                .collect(Collectors.toList());
        String sortedCensusData = new Gson().toJson(censusList);
        return jsonFileCreater(sortedCensusData, SORTED_BY_POPULATION_DENSITY_JSON_PATH);
    }

    /**
     * METHOD TO SORT INDIAN STATE CENSUS DATA AS PER AREA
     * @return List of sorted Json file
     */
    public List<IndiaCensusCSV> getSortedCensusDataAsPerAreaInSquareKm()
            throws IndianStateCensusAndCodeAnalyserException
    {
        List<CensusDAO> censusList = censusMap.values().stream()
                .sorted((censusData1, censusData2) -> censusData2.areaInSqKm.compareTo(censusData1.areaInSqKm))
                .collect(Collectors.toList());
        String sortedCensusData = new Gson().toJson(censusList);
        return jsonFileCreater(sortedCensusData, SORTED_BY_AREA_JSON_PATH);
    }

    /**
     * METHOD TO SORT US STATE CENSUS DATA AS PER POPULATION
     */
    public String getSortedUSCensusDataAsPerPopulation()
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
    public String getSortedUSCensusDataAsPerPopulationDensity()
    {
        List<CensusDAO> censusList = censusMap.values().stream()
                .sorted((censusData1, censusData2) -> censusData2.populationDensity
                        .compareTo(censusData1.populationDensity))
                .collect(Collectors.toList());
        String sortedCensusData = new Gson().toJson(censusList);
        return sortedCensusData;
    }
}