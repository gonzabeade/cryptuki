package ar.edu.itba.paw.cryptuki.mapper.Conflict;

public class ClosedComplainMapper extends ConflictMapper{
    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}
