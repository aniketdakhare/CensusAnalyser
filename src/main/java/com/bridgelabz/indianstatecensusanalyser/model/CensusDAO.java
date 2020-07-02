package com.bridgelabz.indianstatecensusanalyser.model;

public class CensusDAO
{
    public String StateCode;
    public String StateName;
    public String state;
    public Integer areaInSqKm;
    public Integer population;
    public Integer densityPerSqKm;

    public String stateId;
    //public String state;
    //public Integer population;
    public Integer housingUnits;
    public Double totalArea;
    public Double waterArea;
    public Double landArea;
    public Double populationDensity;
    public Double housingDensity;


    public CensusDAO(IndiaCensusCSV indiaCensusCSV)
    {
        state = indiaCensusCSV.state;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        population = indiaCensusCSV.population;
    }

    public CensusDAO(IndiaStateCodeCSV indiaStateCodeCSV)
    {
        StateCode = indiaStateCodeCSV.StateCode;
        StateName = indiaStateCodeCSV.StateName;
    }

    public CensusDAO(USCensusCSV usCensusCSV)
    {
        housingDensity = usCensusCSV.housingDensity;
        housingUnits = usCensusCSV.housingUnits;
        landArea = usCensusCSV.landArea;
        population = usCensusCSV.population;
        state = usCensusCSV.state;
        totalArea = usCensusCSV.totalArea;
        waterArea = usCensusCSV.waterArea;
        populationDensity = usCensusCSV.populationDensity;
        stateId = usCensusCSV.stateId;
    }
}