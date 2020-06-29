package com.bridgelabz.indianstatecensusanalysertest;

import com.bridgelabz.indianstatecensusanalyser.exception.IndianStateCensusAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.services.IndianStateCensusAnalyser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IndianStateCensusAnalyserTest
{
    IndianStateCensusAnalyser censusAnalyser;
    @Before
    public void reusableObjects()
    {
        censusAnalyser = new IndianStateCensusAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(IndianStateCensusAnalyserException.class);
    }

    @Test
    public void givenIndianCensusCSVFile_ReturnsCorrectRecords()
    {
        try
        {
            int numOfRecords = censusAnalyser.loadIndiaCensusData("./src/test/resources/" +
                    "IndiaStateCensusData.csv", ',');
            Assert.assertEquals(29,numOfRecords);
        }
        catch (IndianStateCensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException()
    {
        try
        {
            censusAnalyser.loadIndiaCensusData("./src/main/resources/IndiaStateCensusData.csv", ',');
        }
        catch (IndianStateCensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException()
    {
        try
        {
            censusAnalyser.loadIndiaCensusData("./src/test/resources/IndiaStateCensusData.java", ',');
        }
        catch (IndianStateCensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithCorrectFile_ButIncorrectDelimiter_ShouldThrowException()
    {
        try
        {
            censusAnalyser.loadIndiaCensusData("./src/test/resources/IndiaStateCensusData.csv", ';');
        }
        catch (IndianStateCensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithCorrectFile_ButIncorrectCsvHeader_ShouldThrowException()
    {
        try
        {
            censusAnalyser.loadIndiaCensusData("./src/test/resources/IndiaStateCensussData.csv", ',');
        }
        catch (IndianStateCensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }
}