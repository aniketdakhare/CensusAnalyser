package com.bridgelabz.indianstatecensusanalysertest;

import com.bridgelabz.indianstatecensusanalyser.exception.IndianStateCensusAndCodeAnalyserException;
import com.bridgelabz.indianstatecensusanalyser.model.CensusDAO;
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
    CensusAnalyser stateAnalyser;
    @Before
    public void reusableObjects()
    {
        stateAnalyser = new CensusAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(IndianStateCensusAndCodeAnalyserException.class);
    }

    @Test
    public void givenIndianCensusCSVFile_ReturnsCorrectRecords()
    {
        try
        {
            int numOfRecords = stateAnalyser.loadIndiaCensusData(',',
                    "./src/test/resources/IndiaStateCensusData.csv");
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
            stateAnalyser.loadIndiaCensusData(',', "./src/main/resources/IndiaStateCensusData.csv");
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
            stateAnalyser.loadIndiaCensusData(',', "./src/test/resources/IndiaStateCensusData.java");
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
            stateAnalyser.loadIndiaCensusData(';', "./src/test/resources/IndiaStateCensusData.csv");
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
            stateAnalyser.loadIndiaCensusData(',', "./src/test/resources/IndiaStateCensussData.csv");
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
            int numOfRecords = stateAnalyser.loadIndiaCensusData(',',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/test/resources/IndiaStateCode.csv");
            Assert.assertEquals(29, numOfRecords);
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
            stateAnalyser.loadIndiaCensusData(',',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/main/resources/IndiaStateCode.csv");
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
            stateAnalyser.loadIndiaCensusData(',',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/test/resources/IndiaStateCode.java");
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
            stateAnalyser.loadIndiaCensusData(';',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/test/resources/IndiaStateCode.csv");
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
            stateAnalyser.loadIndiaCensusData(',',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/test/resources/IndianStateCode.csv");        }
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
            stateAnalyser.loadIndiaCensusData(',', "./src/test/resources/IndiaStateCensusData.csv");
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerState();
            CensusDAO[] censusList = new Gson().fromJson(sortedCensus, CensusDAO[].class);
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
            stateAnalyser.loadIndiaCensusData(',', "./src/test/resources/IndiaStateCensusData.csv");
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerState();
            CensusDAO[] censusList = new Gson().fromJson(sortedCensus, CensusDAO[].class);
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
            stateAnalyser.loadIndiaCensusData(',',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/test/resources/IndiaStateCode.csv");
            String sortedStateCode = stateAnalyser.getSortedStateCodeDataAsPerState();
            CensusDAO[] stateCodeList = new Gson().fromJson(sortedStateCode, CensusDAO[].class);
            Assert.assertThat(stateCodeList[0].stateCode, CoreMatchers.is("AP"));
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
            stateAnalyser.loadIndiaCensusData(',',
                    "./src/test/resources/IndiaStateCensusData.csv",
                    "./src/test/resources/IndiaStateCode.csv");
            String sortedStateCode = stateAnalyser.getSortedStateCodeDataAsPerState();
            CensusDAO[] stateCodeList = new Gson().fromJson(sortedStateCode, CensusDAO[].class);
            Assert.assertThat(stateCodeList[stateCodeList.length-1].stateCode, CoreMatchers.is("WB"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByPopulation_ShouldReturnCorrectRecords()
    {
        try
        {
            stateAnalyser.loadIndiaCensusData(',', "./src/test/resources/IndiaStateCensusData.csv");
            int sortedCensus = stateAnalyser.getJsonSortedCensusDataAsPerPopulation();
            Assert.assertEquals(29, sortedCensus);
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByPopulationDensity_ShouldReturnCorrectRecords()
    {
        try
        {
            stateAnalyser.loadIndiaCensusData(',', "./src/test/resources/IndiaStateCensusData.csv");
            List sortedCensus = stateAnalyser.getJsonSortedCensusDataAsPerPopulationDensity();
            Assert.assertEquals(29, sortedCensus.size());
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByArea_ShouldReturnCorrectRecords()
    {
        try
        {
            stateAnalyser.loadIndiaCensusData(',', "./src/test/resources/IndiaStateCensusData.csv");
            List sortedCensus = stateAnalyser.getJsonSortedCensusDataAsPerAreaInSquareKm();
            Assert.assertEquals(29, sortedCensus.size());
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFile_ReturnsCorrectRecords()
    {
        try
        {
            int numOfRecords = stateAnalyser.loadUSCensusData(',',
                    "./src/test/resources/USCensusData.csv");
            Assert.assertEquals(51, numOfRecords);
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByPopulation_ShouldReturnFirstStateInList()
    {
        try
        {
            stateAnalyser.loadUSCensusData(',', "./src/test/resources/USCensusData.csv");
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerPopulation();
            CensusDAO[] censusList = new Gson().fromJson(sortedCensus, CensusDAO[].class);
            Assert.assertThat(censusList[0].state, CoreMatchers.is("California"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByPopulation_ShouldReturnLastStateInList()
    {
        try
        {
            stateAnalyser.loadUSCensusData(',', "./src/test/resources/USCensusData.csv");
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerPopulation();
            CensusDAO[] censusList = new Gson().fromJson(sortedCensus, CensusDAO[].class);
            Assert.assertThat(censusList[censusList.length-1].state, CoreMatchers.is("Wyoming"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByPopulationDensity_ShouldReturnFirstStateInList()
    {
        try
        {
            stateAnalyser.loadUSCensusData(',', "./src/test/resources/USCensusData.csv");
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerPopulationDensity();
            CensusDAO[] censusList = new Gson().fromJson(sortedCensus, CensusDAO[].class);
            Assert.assertThat(censusList[0].state, CoreMatchers.is("District of Columbia"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByPopulationDensity_ShouldReturnLastStateInList()
    {
        try
        {
            stateAnalyser.loadUSCensusData(',', "./src/test/resources/USCensusData.csv");
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerPopulationDensity();
            CensusDAO[] censusList = new Gson().fromJson(sortedCensus, CensusDAO[].class);
            Assert.assertThat(censusList[censusList.length-1].state, CoreMatchers.is("Alaska"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByTotalArea_ShouldReturnFirstStateInList()
    {
        try
        {
            stateAnalyser.loadUSCensusData(',', "./src/test/resources/USCensusData.csv");
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerTotalArea();
            CensusDAO[] censusList = new Gson().fromJson(sortedCensus, CensusDAO[].class);
            Assert.assertThat(censusList[0].state, CoreMatchers.is("Alaska"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByTotalArea_ShouldReturnLastStateInList()
    {
        try
        {
            stateAnalyser.loadUSCensusData(',', "./src/test/resources/USCensusData.csv");
            String sortedCensus = stateAnalyser.getSortedCensusDataAsPerTotalArea();
            CensusDAO[] censusList = new Gson().fromJson(sortedCensus, CensusDAO[].class);
            Assert.assertThat(censusList[censusList.length-1].state, CoreMatchers.is("District of Columbia"));
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByTotalArea_ShouldReturnLastStateInLis()
    {
        try
        {
            String mostDenselyPopulated = stateAnalyser.getMostDenselyPopulatedState
                    (',', "./src/test/resources/IndiaStateCensusData.csv",
                            "./src/test/resources/USCensusData.csv");
            Assert.assertEquals("District of Columbia", mostDenselyPopulated);
        }
        catch (IndianStateCensusAndCodeAnalyserException e)
        {
            e.printStackTrace();
        }
    }
}