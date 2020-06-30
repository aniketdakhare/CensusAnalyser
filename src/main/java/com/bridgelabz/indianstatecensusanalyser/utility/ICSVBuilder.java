package com.bridgelabz.indianstatecensusanalyser.utility;

import com.bridgelabz.indianstatecensusanalyser.exception.IndianStateCensusAndCodeAnalyserException;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder
{
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass, char separator)
            throws CSVBuilderException;
}
