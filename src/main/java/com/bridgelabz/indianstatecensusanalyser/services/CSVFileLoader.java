package com.bridgelabz.indianstatecensusanalyser.services;

import com.bridgelabz.csvbuilderjar.CSVBuilderException;
import com.bridgelabz.csvbuilderjar.CSVBuilderFactory;
import com.bridgelabz.indianstatecensusanalyser.exception.IndianStateCensusAndCodeAnalyserException;
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

public class CSVFileLoader
{
    Map<String, CensusDAO> censusMap;

    public CSVFileLoader()
    {
        this.censusMap = new HashMap<>();
    }

    /**
     * METHOD TO LOAD INDIAN STATE CENSUS DATA
     * @param csvFilePath provides the path of file
     * @param separator provides the seperator for records in csv file
     * @return number of records
     * @throws IndianStateCensusAndCodeAnalyserException while handling the occurred exception
     */
    public int loadIndiaCensusData(String csvFilePath, char separator) throws IndianStateCensusAndCodeAnalyserException
    {
        return this.loadCensusData(csvFilePath, separator, IndiaCensusCSV.class);
    }

    /**
     * METHOD TO LOAD US STATE CENSUS DATA
     * @param csvFilePath provides the path of file
     * @param separator provides the seperator for records in csv file
     * @return number of records
     * @throws IndianStateCensusAndCodeAnalyserException while handling the occurred exception
     */
    public int loadUSCensusData(String csvFilePath, char separator) throws IndianStateCensusAndCodeAnalyserException
    {
        return this.loadCensusData(csvFilePath, separator, USCensusCSV.class);
    }

    /**
     * METHOD TO LOAD INDIAN STATE CENSUS DATA
     * Note:- Pass argument as '0' for OpenCSV and '1' for CommonCSV in createCSVBuilder method
     * @param csvFilePath provides the path of file
     * @param separator provides the seperator for records in csv file
     * @param <E> gives generic class type
     * @return number of records
     * @throws IndianStateCensusAndCodeAnalyserException while handling the occurred exception
     */
    private <E> int loadCensusData(String csvFilePath, char separator, Class<E> censusCSVClass)
            throws IndianStateCensusAndCodeAnalyserException
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
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
                    break;
                case "USCensusCSV":
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(USCensusCSV.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.state, new CensusDAO(csvState)));
                    break;
            }
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
     * METHOD TO LOAD INDIAN STATE CODE DATA
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
                    .filter(csvState -> censusMap.get(csvState.StateName) != null)
                    .forEach(csvState -> censusMap.get(csvState.StateName).stateCode = csvState.StateCode);
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
}