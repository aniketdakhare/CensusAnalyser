package com.bridgelabz.indianstatecensusanalyser.services;

import com.bridgelabz.indianstatecensusanalyser.exception.IndianStateCensusAndCodeAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.indianstatecensusanalyser.utility.CSVBuilderFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class IndianStateCensusAndCodeAnalyser
{
    /**
     * METHOD TO LOAD INDIAN STATE CENSUS DATA
     * @param csvFilePath provides the path of file
     * @param separator provides the seperator for records in csv file
     * @return number of records
     * @throws IndianStateCensusAndCodeAnalyserException while handling the occurred exception
     */
    public int loadIndiaCensusData(String csvFilePath, char separator) throws IndianStateCensusAndCodeAnalyserException
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            Iterator<IndiaCensusCSV> censusCSVIterator = CSVBuilderFactory.createCSVBuilder()
                    .getCSVFileIterator(reader, IndiaCensusCSV.class, separator);
            return this.getCount(censusCSVIterator);
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
    }

    /**
     * METHOD TO LOAD INDIAN STATE CENSUS DATA
     * @param csvFilePath provides the path of file
     * @param separator provides the seperator for records in csv file
     * @return number of records
     * @throws IndianStateCensusAndCodeAnalyserException while handling the occurred exception
     */
    public int loadIndiaStateCode(String csvFilePath, char separator) throws IndianStateCensusAndCodeAnalyserException
    {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            Iterator<IndiaStateCodeCSV> codeCSVIterator = CSVBuilderFactory.createCSVBuilder()
                    .getCSVFileIterator(reader, IndiaStateCodeCSV.class, separator);
            return this.getCount(codeCSVIterator);
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
    }

    /**
     * METHOD TO GET COUNT OF NUMBER OF RECORDS
     * @param iterator provides object of iterator for specific csv class
     * @param <E> provides generic class type
     * @return count of records
     */
    private <E> int getCount(Iterator<E> iterator)
    {
        Iterable<E> codeData = () -> iterator;
        int numberOfEntries = (int) StreamSupport.stream(codeData.spliterator(), false).count();
        return numberOfEntries;
    }
}