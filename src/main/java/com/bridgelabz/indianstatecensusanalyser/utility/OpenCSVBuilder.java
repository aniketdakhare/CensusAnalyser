package com.bridgelabz.indianstatecensusanalyser.utility;

import com.bridgelabz.indianstatecensusanalyser.exception.IndianStateCensusAndCodeAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder
{
    /**
     * METHOD TO GET CSV CLASS ITERATOR
     * @param reader provides object of Reader to read the file
     * @param csvClass provides type of csv class
     * @param separator provides the separator for records in csv file
     * @param <E> provides generic class type
     * @return csvToBean object
     * @throws IndianStateCensusAndCodeAnalyserException while handling the occurred exception
     */
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass, char separator)
            throws IndianStateCensusAndCodeAnalyserException
    {
        try
        {
            CsvToBean<E> csvToBean = new CsvToBeanBuilder<E>(reader)
                    .withType(csvClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(separator)
                    .build();
            return csvToBean.iterator();
        }
        catch (RuntimeException e)
        {
            throw new IndianStateCensusAndCodeAnalyserException("Entered incorrect Delimiter or incorrect Header",
                    IndianStateCensusAndCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER);
        }
    }
}