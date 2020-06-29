package com.bridgelabz.indianstatecensusanalyser.services;

import com.bridgelabz.indianstatecensusanalyser.exception.IndianStateCensusAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaCensusCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class IndianStateCensusAnalyser
{
    public int loadIndiaCensusData(String csvFilePath, char separator) throws IndianStateCensusAnalyserException
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath, "State","Population")))
        {
            CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true).withSeparator(separator);
            CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvToBean.iterator();
            Iterable<IndiaCensusCSV> censusData = () -> censusCSVIterator;
            int numberOfEntries = (int) StreamSupport.stream(censusData.spliterator(), false).count();
            return numberOfEntries;
        }
        catch (NoSuchFileException e)
        {
            throw new IndianStateCensusAnalyserException("Entered wrong file name/path or wrong file extension",
                    IndianStateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);

        }
        catch (IOException e)
        {
            throw new IndianStateCensusAnalyserException(e.getMessage(),
                    IndianStateCensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (RuntimeException e)
        {
            throw new IndianStateCensusAnalyserException("Entered incorrect Delimiter or incorrect Header",
                    IndianStateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER);
        }
    }
}