package com.batch.real.entity;

public class StockParam {

    private String IndustryClassification;
    private String PreviousYearProfitPerShare;
    private String CurrentYearProfitPerShare;
    private String OfferingFlag;
    private String Attribute;
    private String NoProfit;
    private String WeightedVotingRights;

    public String getIndustryClassification() {
        return IndustryClassification;
    }

    public void setIndustryClassification(String industryClassification) {
        IndustryClassification = industryClassification;
    }

    public String getPreviousYearProfitPerShare() {
        return PreviousYearProfitPerShare;
    }

    public void setPreviousYearProfitPerShare(String previousYearProfitPerShare) {
        PreviousYearProfitPerShare = previousYearProfitPerShare;
    }

    public String getCurrentYearProfitPerShare() {
        return CurrentYearProfitPerShare;
    }

    public void setCurrentYearProfitPerShare(String currentYearProfitPerShare) {
        CurrentYearProfitPerShare = currentYearProfitPerShare;
    }

    public String getOfferingFlag() {
        return OfferingFlag;
    }

    public void setOfferingFlag(String offeringFlag) {
        OfferingFlag = offeringFlag;
    }

    public String getAttribute() {
        return Attribute;
    }

    public void setAttribute(String attribute) {
        Attribute = attribute;
    }

    public String getNoProfit() {
        return NoProfit;
    }

    public void setNoProfit(String noProfit) {
        NoProfit = noProfit;
    }

    public String getWeightedVotingRights() {
        return WeightedVotingRights;
    }

    public void setWeightedVotingRights(String weightedVotingRights) {
        WeightedVotingRights = weightedVotingRights;
    }
}
