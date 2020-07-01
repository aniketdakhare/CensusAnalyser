package com.bridgelabz.indianstatecensusanalyser.services;

import com.bridgelabz.csvbuilderjar.CSVBuilderException;
import com.bridgelabz.csvbuilderjar.CSVBuilderFactory;
import com.bridgelabz.indianstatecensusanalyser.exception.IndianStateCensusAndCodeAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaStateCodeCSV;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class IndianStateCensusAndCodeAnalyser
{
    List<IndiaCensusCSV> censusCSVList = null;
    List<IndiaStateCodeCSV> stateCodeCSVList = null;
    private static final String SORTED_BY_POPULATION_JSON_PATH = "./IndiaStateCensusSortedByPopulation.json";
    private static final String SORTED_BY_POPULATION_DENSITY_JSON_PATH = "./IndiaStateCensusSortedByDensity.json";

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
            censusCSVList = CSVBuilderFactory.createCSVBuilder(0)
                    .getCSVFileList(reader, IndiaCensusCSV.class, separator);
            return censusCSVList.size();
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
            stateCodeCSVList = CSVBuilderFactory.createCSVBuilder(0)
                    .getCSVFileList(reader, IndiaStateCodeCSV.class, separator);
            return stateCodeCSVList.size();
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

    public List<IndiaCensusCSV> jsonFileCreater(String sortedCensusData, String filePath)
            throws IndianStateCensusAndCodeAnalyserException
    {
        BufferedReader bufferedReader;
        try(FileWriter writer = new FileWriter(filePath);)
        {
            writer.write(sortedCensusData);
            bufferedReader = new BufferedReader(new FileReader(filePath));
        }
        catch (IOException | NullPointerException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException(e.getMessage(),
                    IndianStateCensusAndCodeAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        }
        IndiaCensusCSV[] censusList = new Gson().fromJson(bufferedReader, IndiaCensusCSV[].class);
        List<IndiaCensusCSV> censusCSVList = Arrays.asList(censusList);
        return censusCSVList;
    }

    public String getSortedCensusDataAsPerState()
    {
        censusCSVList.sort(((Comparator<IndiaCensusCSV>)
                (censusData1, censusData2) -> censusData2.state.compareTo(censusData1.state)).reversed());
        String sortedCensusData = new Gson().toJson(censusCSVList);
        return sortedCensusData;
    }

    public String getSortedStateCodeDataAsPerState()
    {
        stateCodeCSVList.sort(((Comparator<IndiaStateCodeCSV>) (stateCodeData1, stateCodeData2) -> stateCodeData2
                .StateCode.compareTo(stateCodeData1.StateCode)).reversed());
        String sortedStateCodeData = new Gson().toJson(stateCodeCSVList);
        return sortedStateCodeData;
    }

    public int getSortedCensusDataAsPerPopulation() throws IndianStateCensusAndCodeAnalyserException
    {
        censusCSVList.sort((censusData1, censusData2) -> censusData2.population.compareTo(censusData1.population));
        String sortedCensusData = new Gson().toJson(censusCSVList);
        return jsonFileCreater(sortedCensusData, SORTED_BY_POPULATION_JSON_PATH).size();
    }

    public List getSortedCensusDataAsPerPopulationDensity() throws IndianStateCensusAndCodeAnalyserException
    {
        censusCSVList.sort((censusData1, censusData2) -> censusData2.densityPerSqKm
                .compareTo(censusData1.densityPerSqKm));
        String sortedCensusData = new Gson().toJson(censusCSVList);
        return jsonFileCreater(sortedCensusData, SORTED_BY_POPULATION_DENSITY_JSON_PATH);
    }

    public String getSortedCensusDataAsPerAreaInSquareKm()
    {
        censusCSVList.sort((censusData1, censusData2) -> censusData2.areaInSqKm.compareTo(censusData1.areaInSqKm));
        String sortedCensusData = new Gson().toJson(censusCSVList);
        return sortedCensusData;
    }
}