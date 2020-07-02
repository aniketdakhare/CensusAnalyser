package com.bridgelabz.indianstatecensusanalyser.services;

import com.bridgelabz.csvbuilderjar.CSVBuilderException;
import com.bridgelabz.csvbuilderjar.CSVBuilderFactory;
import com.bridgelabz.indianstatecensusanalyser.exception.IndianStateCensusAndCodeAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaCensusDAO;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaStateCodeCSV;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

public class IndianStateCensusAndCodeAnalyser
{
    List<IndiaCensusDAO> censusList;
    List<IndiaCensusDAO> stateCodeList;
    private static final String SORTED_BY_POPULATION_JSON_PATH = "./IndiaStateCensusSortedByPopulation.json";
    private static final String SORTED_BY_POPULATION_DENSITY_JSON_PATH = "./IndiaStateCensusSortedByDensity.json";
    private static final String SORTED_BY_AREA_JSON_PATH = "./IndiaStateCensusSortedByArea.json";

    public IndianStateCensusAndCodeAnalyser()
    {
        this.censusList = new ArrayList<>();
        this.stateCodeList = new ArrayList<>();
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
            while (censusIterator.hasNext())
            {
                this.censusList.add(new IndiaCensusDAO(censusIterator.next()));
            }
            return censusList.size();
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
            while (stateCodeIterator.hasNext())
            {
                this.stateCodeList.add(new IndiaCensusDAO(stateCodeIterator.next()));
            }
            return stateCodeList.size();
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
        IndiaCensusCSV[] stateCensusList = new Gson().fromJson(bufferedReader, IndiaCensusCSV[].class);
        List<IndiaCensusCSV> censusCSVList = Arrays.asList(stateCensusList);
        return censusCSVList;
    }

    /**
     * METHOD TO SORT INDIAN STATE CENSUS DATA AS PER STATE
     */
    public String getSortedCensusDataAsPerState()
    {
        censusList.sort(((Comparator<IndiaCensusDAO>)
                (censusData1, censusData2) -> censusData2.state.compareTo(censusData1.state)).reversed());
        String sortedCensusData = new Gson().toJson(censusList);
        return sortedCensusData;
    }

    /**
     * METHOD TO SORT INDIAN STATE CODE DATA AS PER STATE
     */
    public String getSortedStateCodeDataAsPerState()
    {
        stateCodeList.sort(((Comparator<IndiaCensusDAO>) (stateCodeData1, stateCodeData2) -> stateCodeData2
                .StateCode.compareTo(stateCodeData1.StateCode)).reversed());
        String sortedStateCodeData = new Gson().toJson(stateCodeList);
        return sortedStateCodeData;
    }

    /**
     * METHOD TO SORT INDIAN STATE CENSUS DATA AS PER POPULATION
     * @return size of sorted Json file
     */
    public int getSortedCensusDataAsPerPopulation() throws IndianStateCensusAndCodeAnalyserException
    {
        censusList.sort((censusData1, censusData2) -> censusData2.population.compareTo(censusData1.population));
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
        censusList.sort((censusData1, censusData2) -> censusData2.densityPerSqKm
                .compareTo(censusData1.densityPerSqKm));
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
        censusList.sort((censusData1, censusData2) -> censusData2.areaInSqKm.compareTo(censusData1.areaInSqKm));
        String sortedCensusData = new Gson().toJson(censusList);
        return jsonFileCreater(sortedCensusData, SORTED_BY_AREA_JSON_PATH);
    }
}