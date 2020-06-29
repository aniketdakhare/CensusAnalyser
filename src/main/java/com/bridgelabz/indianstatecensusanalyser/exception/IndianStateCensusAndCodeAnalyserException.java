package com.bridgelabz.indianstatecensusanalyser.exception;

public class IndianStateCensusAndCodeAnalyserException extends Exception
{
    public enum ExceptionType
    {
        CENSUS_FILE_PROBLEM, INCORRECT_DELIMITER_OR_HEADER
    }

    public ExceptionType type;

    public IndianStateCensusAndCodeAnalyserException(String message, ExceptionType type)
    {
        super(message);
        this.type = type;
    }
}