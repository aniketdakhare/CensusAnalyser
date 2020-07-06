package com.bridgelabz.indianstatecensusanalyser.model;

import com.bridgelabz.indianstatecensusanalyser.services.CensusAnalyser;

public class CensusDAO
{
    public String stateCode;
    public String state;
    public Integer population;
    public double totalArea;
    public double populationDensity;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV)
    {
        state = indiaCensusCSV.state;
        populationDensity = indiaCensusCSV.densityPerSqKm;
        totalArea = indiaCensusCSV.areaInSqKm;
        population = indiaCensusCSV.population;
    }

    public CensusDAO(USCensusCSV usCensusCSV)
    {
        population = usCensusCSV.population;
        state = usCensusCSV.state;
        totalArea = usCensusCSV.totalArea;
        populationDensity = usCensusCSV.populationDensity;
        stateCode = usCensusCSV.stateId;
    }

    public Object getCensusDTO(CensusAnalyser.Country country)
    {
        if (country.equals((CensusAnalyser.Country.INDIA)))
            return new IndiaCensusCSV(state, population, (int) populationDensity, (int) totalArea);
        return new USCensusCSV(stateCode, state, population, totalArea, populationDensity);
    }
}