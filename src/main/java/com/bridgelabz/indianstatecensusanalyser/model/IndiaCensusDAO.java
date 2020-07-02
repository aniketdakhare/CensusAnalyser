package com.bridgelabz.indianstatecensusanalyser.model;

public class IndiaCensusDAO
{
    public String StateCode;
    public String StateName;
    public String state;
    public Integer areaInSqKm;
    public Integer population;
    public Integer densityPerSqKm;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV)
    {
        state = indiaCensusCSV.state;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        population = indiaCensusCSV.population;
    }

    public IndiaCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV)
    {
        StateCode = indiaStateCodeCSV.StateCode;
        StateName = indiaStateCodeCSV.StateName;
    }
}