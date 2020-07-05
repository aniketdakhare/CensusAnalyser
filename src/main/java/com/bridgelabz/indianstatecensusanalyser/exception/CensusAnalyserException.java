package com.bridgelabz.indianstatecensusanalyser.exception;

public class CensusAnalyserException extends Exception
{
    public enum ExceptionType
    {
        CSV_FILE_PROBLEM, INVALID_COUNTRY, INCORRECT_DELIMITER_OR_HEADER
    }

    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type)
    {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, String name)
    {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }
}