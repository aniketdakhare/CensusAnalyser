package com.bridgelabz.indianstatecensusanalysertest;

import com.bridgelabz.indianstatecensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indianstatecensusanalyser.model.USCensusCSV;
import com.bridgelabz.indianstatecensusanalyser.services.CensusAnalyser;
import com.google.gson.Gson;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

public class CensusAnalyserTest
{
    CensusAnalyser indiaCensusAnalyser;
    CensusAnalyser usCensusAnalyser;
    @Before
    public void reusableObjects()
    {
        indiaCensusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        usCensusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CensusAnalyserException.class);
    }

    @Test
    public void givenIndianCensusCSVFile_ReturnsCorrectRecords()
    {
        try
        {
            int numOfRecords = indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv");
            Assert.assertEquals(29, numOfRecords);
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/main/resources/IndiaStateCensusData.csv");
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.java");
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithCorrectFile_ButIncorrectDelimiter_ShouldThrowException()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ';',
                    "./src/test/resources/IndiaStateCensusData.csv");
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithCorrectFile_ButIncorrectCsvHeader_ShouldThrowException()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensussData.csv");
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
            int numOfRecords = indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
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
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/main/resources/IndiaStateCode.csv");
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileType_ShouldThrowException()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/test/resources/IndiaStateCode.java");
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithCorrectFile_ButIncorrectDelimiter_ShouldThrowException()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ';',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/test/resources/IndiaStateCode.csv");
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithCorrectFile_ButIncorrectCsvHeader_ShouldThrowException()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/test/resources/IndianStateCode.csv");
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnFirstStateInList()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv");
            String sortedCensus = indiaCensusAnalyser.getSortedCensusDataAsPerState();
            IndiaCensusCSV[] censusList = new Gson().fromJson(sortedCensus, IndiaCensusCSV[].class);
            Assert.assertThat(censusList[0].state, CoreMatchers.is("Andhra Pradesh"));
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnLastStateInList()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv");
            String sortedCensus = indiaCensusAnalyser.getSortedCensusDataAsPerState();
            IndiaCensusCSV[] censusList = new Gson().fromJson(sortedCensus, IndiaCensusCSV[].class);
            Assert.assertThat(censusList[censusList.length-1].state, CoreMatchers.is("West Bengal"));
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnFirstStateCodeInList()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/test/resources/IndiaStateCode.csv");
            String sortedStateCode = indiaCensusAnalyser.getSortedStateCodeDataAsPerState();
            IndiaCensusCSV[] stateCodeList = new Gson().fromJson(sortedStateCode, IndiaCensusCSV[].class);
            Assert.assertThat(stateCodeList[0].state, CoreMatchers.is("Andhra Pradesh"));
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnLastStateCodeInList()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/test/resources/IndiaStateCode.csv");
            String sortedStateCode = indiaCensusAnalyser.getSortedStateCodeDataAsPerState();
            IndiaCensusCSV[] stateCodeList = new Gson().fromJson(sortedStateCode, IndiaCensusCSV[].class);
            Assert.assertThat(stateCodeList[stateCodeList.length-1].state, CoreMatchers.is("West Bengal"));
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByPopulation_ShouldReturnCorrectRecords()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv");
            int sortedCensus = indiaCensusAnalyser.getJsonSortedCensusDataAsPerPopulation();
            Assert.assertEquals(29, sortedCensus);
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByPopulationDensity_ShouldReturnCorrectRecords()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv");
            List sortedCensus = indiaCensusAnalyser.getJsonSortedCensusDataAsPerPopulationDensity();
            Assert.assertEquals(29, sortedCensus.size());
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByArea_ShouldReturnCorrectRecords()
    {
        try
        {
            indiaCensusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, ',',
                    "./src/test/resources/IndiaStateCensusData.csv");
            List sortedCensus = indiaCensusAnalyser.getJsonSortedCensusDataAsPerAreaInSquareKm();
            Assert.assertEquals(29, sortedCensus.size());
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
            int numOfRecords = usCensusAnalyser.loadCensusData(CensusAnalyser.Country.US, ',',
                    "./src/test/resources/USCensusData.csv");
            Assert.assertEquals(51, numOfRecords);
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByPopulation_ShouldReturnFirstStateInList()
    {
        try
        {
            usCensusAnalyser.loadCensusData(CensusAnalyser.Country.US, ',',
                    "./src/test/resources/USCensusData.csv");
            String sortedCensus = usCensusAnalyser.getSortedCensusDataAsPerPopulation();
            USCensusCSV[] censusList = new Gson().fromJson(sortedCensus, USCensusCSV[].class);
            Assert.assertThat(censusList[0].state, CoreMatchers.is("California"));
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByPopulation_ShouldReturnLastStateInList()
    {
        try
        {
            usCensusAnalyser.loadCensusData(CensusAnalyser.Country.US, ',',
                    "./src/test/resources/USCensusData.csv");
            String sortedCensus = usCensusAnalyser.getSortedCensusDataAsPerPopulation();
            USCensusCSV[] censusList = new Gson().fromJson(sortedCensus, USCensusCSV[].class);
            Assert.assertThat(censusList[censusList.length-1].state, CoreMatchers.is("Wyoming"));
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByPopulationDensity_ShouldReturnFirstStateInList()
    {
        try
        {
            usCensusAnalyser.loadCensusData(CensusAnalyser.Country.US, ',',
                    "./src/test/resources/USCensusData.csv");
            String sortedCensus = usCensusAnalyser.getSortedCensusDataAsPerPopulationDensity();
            USCensusCSV[] censusList = new Gson().fromJson(sortedCensus, USCensusCSV[].class);
            Assert.assertThat(censusList[0].state, CoreMatchers.is("District of Columbia"));
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByPopulationDensity_ShouldReturnLastStateInList()
    {
        try
        {
            usCensusAnalyser.loadCensusData(CensusAnalyser.Country.US, ',',
                    "./src/test/resources/USCensusData.csv");
            String sortedCensus = usCensusAnalyser.getSortedCensusDataAsPerPopulationDensity();
            USCensusCSV[] censusList = new Gson().fromJson(sortedCensus, USCensusCSV[].class);
            Assert.assertThat(censusList[censusList.length-1].state, CoreMatchers.is("Alaska"));
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByTotalArea_ShouldReturnFirstStateInList()
    {
        try
        {
            usCensusAnalyser.loadCensusData(CensusAnalyser.Country.US, ',',
                    "./src/test/resources/USCensusData.csv");
            String sortedCensus = usCensusAnalyser.getSortedCensusDataAsPerTotalArea();
            USCensusCSV[] censusList = new Gson().fromJson(sortedCensus, USCensusCSV[].class);
            Assert.assertThat(censusList[0].state, CoreMatchers.is("Alaska"));
        }
        catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByTotalArea_ShouldReturnLastStateInList()
    {
        try
        {
            usCensusAnalyser.loadCensusData(CensusAnalyser.Country.US, ',',
                    "./src/test/resources/USCensusData.csv");
            String sortedCensus = usCensusAnalyser.getSortedCensusDataAsPerTotalArea();
            USCensusCSV[] censusList = new Gson().fromJson(sortedCensus, USCensusCSV[].class);
            Assert.assertThat(censusList[censusList.length-1].state, CoreMatchers.is("District of Columbia"));
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
            String mostDenselyPopulated = usCensusAnalyser.getMostDenselyPopulatedState
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