package com.bridgelabz.indianstatecensusanalyser.exception;

public class IndianStateCensusAnalyserException extends Exception
{
    public enum ExceptionType
    {
        CENSUS_FILE_PROBLEM, INCORRECT_DELIMITER
    }

    public ExceptionType type;

    public IndianStateCensusAnalyserException(String message, ExceptionType type)
    {
        super(message);
        this.type = type;
    }

    public IndianStateCensusAnalyserException(String message, ExceptionType type, Throwable cause)
    {
        super(message, cause);
        this.type = type;
    }
}