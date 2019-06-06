package com.batch.real.entity;

/**
 * Created by Administrator on 2019/6/2.
 */
public class Security {
    private String SecurityID;
    private String SecurityIDSource;
    private String Symbol;
    private String EnglishName;
    private String ISIN;
    private String UnderlyingSecurityID;
    private String UnderlyingSecurityIDSource;
    private String ListDate;
    private String SecurityType;
    private String Currency;
    private String QtyUnit;
    private String DayTrading;
    private String PrevClosePx;
    private String SecurityStatus;
    private String OutstandingShare;
    private String PublicFloatShareQuantity;
    private String ParValue;
    private String GageFlag;
    private String GageRatio;
    private String CrdBuyUnderlying;
    private String CrdSellUnderlying;
    private String PriceCheckMode;
    private String PledgeFlag;
    private String ContractMultiplier;
    private String RegularShare;
    private String QualificationFlag;
    private String QualificationClass;

    private StockParam stockParam;

    public StockParam getStockParam() {
        return stockParam;
    }

    public void setStockParam(StockParam stockParam) {
        this.stockParam = stockParam;
    }

    public String getSecurityID() {
        return SecurityID;
    }

    public void setSecurityID(String securityID) {
        SecurityID = securityID;
    }

    public String getSecurityIDSource() {
        return SecurityIDSource;
    }

    public void setSecurityIDSource(String securityIDSource) {
        SecurityIDSource = securityIDSource;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public String getISIN() {
        return ISIN;
    }

    public void setISIN(String ISIN) {
        this.ISIN = ISIN;
    }

    public String getUnderlyingSecurityID() {
        return UnderlyingSecurityID;
    }

    public void setUnderlyingSecurityID(String underlyingSecurityID) {
        UnderlyingSecurityID = underlyingSecurityID;
    }

    public String getUnderlyingSecurityIDSource() {
        return UnderlyingSecurityIDSource;
    }

    public void setUnderlyingSecurityIDSource(String underlyingSecurityIDSource) {
        UnderlyingSecurityIDSource = underlyingSecurityIDSource;
    }

    public String getListDate() {
        return ListDate;
    }

    public void setListDate(String listDate) {
        ListDate = listDate;
    }

    public String getSecurityType() {
        return SecurityType;
    }

    public void setSecurityType(String securityType) {
        SecurityType = securityType;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getQtyUnit() {
        return QtyUnit;
    }

    public void setQtyUnit(String qtyUnit) {
        QtyUnit = qtyUnit;
    }

    public String getDayTrading() {
        return DayTrading;
    }

    public void setDayTrading(String dayTrading) {
        DayTrading = dayTrading;
    }

    public String getPrevClosePx() {
        return PrevClosePx;
    }

    public void setPrevClosePx(String prevClosePx) {
        PrevClosePx = prevClosePx;
    }

    public String getSecurityStatus() {
        return SecurityStatus;
    }

    public void setSecurityStatus(String securityStatus) {
        SecurityStatus = securityStatus;
    }

    public String getOutstandingShare() {
        return OutstandingShare;
    }

    public void setOutstandingShare(String outstandingShare) {
        OutstandingShare = outstandingShare;
    }

    public String getPublicFloatShareQuantity() {
        return PublicFloatShareQuantity;
    }

    public void setPublicFloatShareQuantity(String publicFloatShareQuantity) {
        PublicFloatShareQuantity = publicFloatShareQuantity;
    }

    public String getParValue() {
        return ParValue;
    }

    public void setParValue(String parValue) {
        ParValue = parValue;
    }

    public String getGageFlag() {
        return GageFlag;
    }

    public void setGageFlag(String gageFlag) {
        GageFlag = gageFlag;
    }

    public String getGageRatio() {
        return GageRatio;
    }

    public void setGageRatio(String gageRatio) {
        GageRatio = gageRatio;
    }

    public String getCrdBuyUnderlying() {
        return CrdBuyUnderlying;
    }

    public void setCrdBuyUnderlying(String crdBuyUnderlying) {
        CrdBuyUnderlying = crdBuyUnderlying;
    }

    public String getCrdSellUnderlying() {
        return CrdSellUnderlying;
    }

    public void setCrdSellUnderlying(String crdSellUnderlying) {
        CrdSellUnderlying = crdSellUnderlying;
    }

    public String getPriceCheckMode() {
        return PriceCheckMode;
    }

    public void setPriceCheckMode(String priceCheckMode) {
        PriceCheckMode = priceCheckMode;
    }

    public String getPledgeFlag() {
        return PledgeFlag;
    }

    public void setPledgeFlag(String pledgeFlag) {
        PledgeFlag = pledgeFlag;
    }

    public String getContractMultiplier() {
        return ContractMultiplier;
    }

    public void setContractMultiplier(String contractMultiplier) {
        ContractMultiplier = contractMultiplier;
    }

    public String getRegularShare() {
        return RegularShare;
    }

    public void setRegularShare(String regularShare) {
        RegularShare = regularShare;
    }

    public String getQualificationFlag() {
        return QualificationFlag;
    }

    public void setQualificationFlag(String qualificationFlag) {
        QualificationFlag = qualificationFlag;
    }

    public String getQualificationClass() {
        return QualificationClass;
    }

    public void setQualificationClass(String qualificationClass) {
        QualificationClass = qualificationClass;
    }
}
