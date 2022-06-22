package ar.edu.itba.paw.model;


import java.util.Locale;

public class TestUser extends User{

    private KycInformation testKyc;

    public TestUser(String email, String phoneNumber, int ratingCount, int ratingSum, Locale locale){
        super(email, phoneNumber, ratingCount, ratingSum, locale);
    }

    public void setKyc(KycInformation kyc){
        testKyc = kyc;
    }

    @Override
    public KycInformation getKyc(){
        return testKyc;
    }
}
