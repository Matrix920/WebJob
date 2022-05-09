package com.example.wejob.model;

public class Job {
    public int JobID,CompanyID;
    public String Title;
    public int Salary;
    public String RequiredEducationLevel;
    public int RequiredExperienceLevel,RequiredExperienceYears;

    public static final String JOB_ID="JobID";
    public static final String COMPANY_ID=Company.COMPANY_ID;
    public static final String TITLE="Title";
    public static final String SALARY="Salary";
    public static final String REQUIRED_EDUCATION_LEVEL="RequiredEducationLevel";
    public static final String REQUIRED_EXPERIENCE_LEVEL="RequiredExperienceLevel";
    public static final String JOB="Job";

    public Job(int jobID, int companyID, String title, int salary, String requiredEducationLevel, int requiredExperienceLevel,int requiredExperienceYears) {
        JobID = jobID;
        CompanyID = companyID;
        Title = title;
        Salary = salary;
        RequiredExperienceYears=requiredExperienceYears;
        RequiredEducationLevel = requiredEducationLevel;
        RequiredExperienceLevel = requiredExperienceLevel;
    }
}
