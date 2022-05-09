package com.example.wejob.model;

public class Candidate {

    public int CandidateID;
    public String Login,Password,FullName,FulllName;
    public Long Tel;
    public int ExperienceYears;

    public static final String CANDIDATE_ID="CandidateID";
    public static final String LOGIN="Login";
    public static final String PASSWORD="Password";
    public static final String FULL_NAME="FullName";
    public static final String TEL="Tel";
    public static final String EXPERIENCE_YEARS="ExperienceYears";
    public static final String CANDIDATE="candidate";

    public Candidate(int candidateID, String login, String password, String fullName, Long tel, int experienceYears,String fulllName) {
        CandidateID = candidateID;
        Login = login;
        Password = password;
        FullName = fullName;
        Tel = tel;
        ExperienceYears = experienceYears;
        FulllName=fulllName;
    }
}
