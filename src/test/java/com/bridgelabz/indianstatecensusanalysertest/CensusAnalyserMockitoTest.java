package com.bridgelabz.indianstatecensusanalysertest;

import com.bridgelabz.indianstatecensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.services.CensusAnalyser;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.when;

public class CensusAnalyserMockitoTest
{
    @Mock
    CensusAnalyser censusAnalyser;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void givenIndianCensusCSVFile_ReturnsCorrectRecords()
    {
        try
        {
            when(censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv")).thenReturn(29);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv");
            Assert.assertEquals(29, numOfRecords);
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeCSVFile_ReturnsCorrectRecords()
    {
        try
        {
            when(censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/test/resources/IndiaStateCode.csv")).thenReturn(29);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/test/resources/IndiaStateCode.csv");
            Assert.assertEquals(29, numOfRecords);
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFile_ReturnsCorrectRecords()
    {
        try
        {
            when(censusAnalyser.loadCensusData(CensusAnalyser.Country.US, ',',
                    "./src/test/resources/USCensusData.csv")).thenReturn(51);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, ',',
                    "./src/test/resources/USCensusData.csv");
            Assert.assertEquals(51, numOfRecords);
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByTotalArea_ShouldReturnLastStateInLis()
    {
        try
        {
            when(censusAnalyser.getMostDenselyPopulatedState
                    (',', "./src/test/resources/IndiaStateCensusData.csv",
                            "./src/test/resources/USCensusData.csv")).thenReturn("District of Columbia");
            String mostDenselyPopulated = censusAnalyser.getMostDenselyPopulatedState
                    (',', "./src/test/resources/IndiaStateCensusData.csv",
                            "./src/test/resources/USCensusData.csv");
            Assert.assertEquals("District of Columbia", mostDenselyPopulated);
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }
}
