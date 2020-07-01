package com.bridgelabz.indianstatecensusanalysertest;

import com.bridgelabz.indianstatecensusanalyser.exception.IndianStateCensusAndCodeAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indianstatecensusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.indianstatecensusanalyser.services.IndianStateCensusAndCodeAnalyser;
import com.google.gson.Gson;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IndianStateCensusAndCodeAnalyserTest
{
    IndianStateCensusAndCodeAnalyser stateAnalyser;
    @Before
    public void reusableObjects()
    {
        stateAnalyser = new IndianStateCensusAndCodeAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(IndianStateCensusAndCodeAnalyserException.class);
    }

    @Test
    public void givenIndianCensusCSVFile_ReturnsCorrectRecords()
    {
        try
        {
            int numOfRecords = stateAnalyser.loadIndiaCensusData("./src/test/resources/" +
                    "IndiaStateCensusData.csv", ',');
            Assert.assertEquals(29, numOfRecords);
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException()
    {
        try
        {
            stateAnalyser.loadIndiaCensusData("./src/main/resources/IndiaStateCensusData.csv", ',');
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException()
    {
        try
        {
            stateAnalyser.loadIndiaCensusData("./src/test/resources/IndiaStateCensusData.java", ',');
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithCorrectFile_ButIncorrectDelimiter_ShouldThrowException()
    {
        try
        {
            stateAnalyser.loadIndiaCensusData("./src/test/resources/IndiaStateCensusData.csv", ';');
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithCorrectFile_ButIncorrectCsvHeader_ShouldThrowException()
    {
        try
        {
            stateAnalyser.loadIndiaCensusData("./src/test/resources/IndiaStateCensussData.csv", ',');
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeCSVFile_ReturnsCorrectRecords()
    {
        try
        {
            int numOfRecords = stateAnalyser.loadIndiaStateCode("./src/test/resources" +
                    "/IndiaStateCode.csv", ',');
            Assert.assertEquals(37, numOfRecords);
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException()
    {
        try
        {
            stateAnalyser.loadIndiaStateCode("./src/main/resources/IndiaStateCode.csv", ',');
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileType_ShouldThrowException()
    {
        try
        {
            stateAnalyser.loadIndiaStateCode("./src/test/resources/IndiaStateCode.java", ',');
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithCorrectFile_ButIncorrectDelimiter_ShouldThrowException()
    {
        try
        {
            stateAnalyser.loadIndiaStateCode("./src/test/resources/IndiaStateCode.csv", ';');
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithCorrectFile_ButIncorrectCsvHeader_ShouldThrowException()
    {
        try
        {
            stateAnalyser.loadIndiaStateCode("./src/test/resources/IndianStateCode.csv", ',');
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnFirstStateInList()
    {
        try
        {
            stateAnalyser.loadIndiaCensusData("./src/test/resources/IndiaStateCensusData.csv", ',');
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerState();
            IndiaCensusCSV[] censusList = new Gson().fromJson(sortedCensus, IndiaCensusCSV[].class);
            Assert.assertThat(censusList[0].state, CoreMatchers.is("Andhra Pradesh"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnLastStateInList()
    {
        try
        {
            stateAnalyser.loadIndiaCensusData("./src/test/resources/IndiaStateCensusData.csv", ',');
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerState();
            IndiaCensusCSV[] censusList = new Gson().fromJson(sortedCensus, IndiaCensusCSV[].class);
            Assert.assertThat(censusList[censusList.length-1].state, CoreMatchers.is("West Bengal"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnFirstStateCodeInList()
    {
        try
        {
            stateAnalyser.loadIndiaStateCode("./src/test/resources/IndiaStateCode.csv", ',');
            String sortedStateCode = stateAnalyser.getSortedStateCodeDataAsPerState();
            IndiaStateCodeCSV[] stateCodeList = new Gson().fromJson(sortedStateCode, IndiaStateCodeCSV[].class);
            Assert.assertThat(stateCodeList[0].StateCode, CoreMatchers.is("AD"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnLastStateCodeInList()
    {
        try
        {
            stateAnalyser.loadIndiaStateCode("./src/test/resources/IndiaStateCode.csv", ',');
            String sortedStateCode = stateAnalyser.getSortedStateCodeDataAsPerState();
            IndiaStateCodeCSV[] stateCodeList = new Gson().fromJson(sortedStateCode, IndiaStateCodeCSV[].class);
            Assert.assertThat(stateCodeList[stateCodeList.length-1].StateCode, CoreMatchers.is("WB"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByPopulation_ShouldReturnFirstStateInList()
    {
        try
        {
            stateAnalyser.loadIndiaCensusData("./src/test/resources/IndiaStateCensusData.csv", ',');
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerPopulation();
            IndiaCensusCSV[] censusList = new Gson().fromJson(sortedCensus, IndiaCensusCSV[].class);
            Assert.assertThat(censusList[0].state, CoreMatchers.is("Uttar Pradesh"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByPopulation_ShouldReturnLastStateInList()
    {
        try
        {
            stateAnalyser.loadIndiaCensusData("./src/test/resources/IndiaStateCensusData.csv", ',');
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerPopulation();
            IndiaCensusCSV[] censusList = new Gson().fromJson(sortedCensus, IndiaCensusCSV[].class);
            Assert.assertThat(censusList[censusList.length-1].state, CoreMatchers.is("Sikkim"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByPopulationDensity_ShouldReturnFirstStateInList()
    {
        try
        {
            stateAnalyser.loadIndiaCensusData("./src/test/resources/IndiaStateCensusData.csv", ',');
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerPopulationDensity();
            IndiaCensusCSV[] censusList = new Gson().fromJson(sortedCensus, IndiaCensusCSV[].class);
            Assert.assertThat(censusList[0].state, CoreMatchers.is("Bihar"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByPopulationDensity_ShouldReturnLastStateInList()
    {
        try
        {
            stateAnalyser.loadIndiaCensusData("./src/test/resources/IndiaStateCensusData.csv", ',');
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerPopulationDensity();
            IndiaCensusCSV[] censusList = new Gson().fromJson(sortedCensus, IndiaCensusCSV[].class);
            Assert.assertThat(censusList[censusList.length-1].state, CoreMatchers.is("Arunachal Pradesh"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }
}