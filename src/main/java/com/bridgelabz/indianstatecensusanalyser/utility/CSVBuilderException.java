package com.bridgelabz.indianstatecensusanalyser.utility;

public class CSVBuilderException extends Exception
{
    public enum ExceptionType
    {
        CSV_FILE_PROBLEM, INCORRECT_DELIMITER_OR_HEADER
    }

    public ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type)
    {
        super(message);
        this.type = type;
    }
}
