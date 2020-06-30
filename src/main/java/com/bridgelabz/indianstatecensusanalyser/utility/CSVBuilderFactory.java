package com.bridgelabz.indianstatecensusanalyser.utility;

public class CSVBuilderFactory
{
    /**
     * METHOD TO CREATE OBJECT OF OpenCSVBuilder
     * @return object of OpenCSVBuilder
     */
    public static ICSVBuilder createCSVBuilder()
    {
        return new OpenCSVBuilder();
    }
}