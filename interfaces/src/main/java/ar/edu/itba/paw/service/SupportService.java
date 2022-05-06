package ar.edu.itba.paw.service;
import ar.edu.itba.paw.persistence.Complain;
import ar.edu.itba.paw.service.digests.SupportDigest;

public interface SupportService {

    @Deprecated
    void getSupportFor(SupportDigest digest);

    void getSupportFor(Complain.Builder builder);
}
