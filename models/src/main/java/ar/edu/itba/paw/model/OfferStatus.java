package ar.edu.itba.paw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;


public enum OfferStatus {
    APR,
    PEN,
    PSE,
    PSU,
    DEL
}
