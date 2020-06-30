package com.bridgelabz.indianstatecensusanalyser.utility;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder implements ICSVBuilder
{
    /**
     * METHOD TO GET CSV CLASS ITERATOR
     * @param reader provides object of Reader to read the file
     * @param csvClass provides type of csv class
     * @param separator provides the separator for records in csv file
     * @param <E> provides generic class type
     * @return csvToBean object
     * @throws CSVBuilderException while handling the occurred exception
     */
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass, char separator)
            throws CSVBuilderException
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
            throw new CSVBuilderException("Entered incorrect Delimiter or incorrect Header",
                    CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER);
        }
    }
}