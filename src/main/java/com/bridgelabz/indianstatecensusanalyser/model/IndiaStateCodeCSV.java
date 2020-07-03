package com.bridgelabz.indianstatecensusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV
{
    @CsvBindByName(column = "StateName", required = true)
    public String StateName;

    @CsvBindByName
    public String StateCode;

    @Override
    public String toString()
    {
        return "IndiaStateCodeCSV{" +
                ", StateName='" + StateName + '\'' +
                ", StateCode='" + StateCode + '\'' +
                '}';
    }
}