package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.Cryptocurrency;

import javax.persistence.Column;
import javax.persistence.Id;

public class CryptocurrencyDto {

    private String code;
    private String commercialName;

    public static CryptocurrencyDto fromCryptocurrency(Cryptocurrency cryptocurrency) {
        CryptocurrencyDto dto = new CryptocurrencyDto();
        dto.code = cryptocurrency.getCode();
        dto.commercialName = cryptocurrency.getCommercialName();
        return dto;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCommercialName() {
        return commercialName;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }
}
