package com.bridgelabz.indianstatecensusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV
{
    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public Integer population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public int areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public int densityPerSqKm;

    public IndiaCensusCSV()
    {
    }

    public IndiaCensusCSV(String state, Integer population, int populationDensity, int totalArea)
    {
        this.state = state;
        this.population = population;
        this.areaInSqKm = totalArea;
        this.densityPerSqKm = populationDensity;
    }
}