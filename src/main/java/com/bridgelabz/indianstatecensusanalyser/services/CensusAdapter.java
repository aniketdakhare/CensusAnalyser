package com.bridgelabz.indianstatecensusanalyser.services;

import com.bridgelabz.csvbuilderjar.CSVBuilderException;
import com.bridgelabz.csvbuilderjar.CSVBuilderFactory;
import com.bridgelabz.indianstatecensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.CensusDAO;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.indianstatecensusanalyser.model.USCensusCSV;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter
{
    public abstract Map<String, CensusDAO> loadCensusData(char separator, String... csvFilePath)
            throws CensusAnalyserException;

    /**
     * METHOD TO LOAD STATE CENSUS DATA
     * Note:- Pass argument as '0' for OpenCSV and '1' for CommonCSV in createCSVBuilder method
     * @param <E> gives generic class type
     * @param separator provides the seperator for records in csv file
     * @param csvFilePath provides the path of file
     * @return map of loaded data
     * @throws CensusAnalyserException while handling the occurred exception
     */
    public <E> Map<String, CensusDAO> loadCensusData(char separator, Class<E> censusCSVClass, String... csvFilePath)
            throws CensusAnalyserException
    {
        Map<String, CensusDAO> censusMap = new HashMap<>();
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0])))
        {
            Iterator<E> censusIterator = CSVBuilderFactory.createCSVBuilder(0)
                    .getCSVFileIterator(reader, censusCSVClass, separator);
            Iterable<E> csvIterable = () -> censusIterator;
            switch (censusCSVClass.getSimpleName())
            {
                case "IndiaCensusCSV":
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(IndiaCensusCSV.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.state, new CensusDAO(csvState)));
                    if (csvFilePath.length == 2)
                    {
                        try (Reader codeReader = Files.newBufferedReader(Paths.get(csvFilePath[1])))
                        {
                            Iterator<IndiaStateCodeCSV> stateCodeIterator = CSVBuilderFactory.createCSVBuilder(0)
                                    .getCSVFileIterator(codeReader, IndiaStateCodeCSV.class, separator);
                            Iterable<IndiaStateCodeCSV> codeCSVIterable = () -> stateCodeIterator;
                            StreamSupport.stream(codeCSVIterable.spliterator(), false)
                                    .filter(csvState -> censusMap.get(csvState.StateName) != null)
                                    .forEach(csvState -> censusMap.get(csvState.StateName).stateCode = csvState.StateCode);
                        }
                    }
                    break;
                case "USCensusCSV":
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(USCensusCSV.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.state, new CensusDAO(csvState)));
                    break;
            }
            return censusMap;
        }
        catch (NoSuchFileException e)
        {
            throw new CensusAnalyserException("Entered wrong file name/path or wrong file extension",
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        }
        catch (IOException e)
        {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
        }
        catch (RuntimeException e)
        {
            throw new CensusAnalyserException("Entered incorrect Delimiter or incorrect Header",
                    CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER);
        }
        catch (CSVBuilderException e)
        {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }
}