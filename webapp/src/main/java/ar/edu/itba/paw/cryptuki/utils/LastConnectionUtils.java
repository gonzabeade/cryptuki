package ar.edu.itba.paw.cryptuki.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class LastConnectionUtils {

    public enum RelativeTime {
        ONLINE("En línea", 0, 60),
        LESS_FIVE("Hace menos de 5 minutos", 60, 300),
        LESS_THIRTY("Hace menos de 30 minutos", 300, 30*60),
        LESS_HOUR("Hace menos de 1 hora", 30*60, 60*60),
        TODAY("Hoy", 60*60, 24*60*60),
        YESTERDAY("Ayer", 24*60*60, 2*24*60*60),
        THIS_WEEK("Esta semana", 2*24*60*60, 7*24*60*60),
        LONG_TIME_AGO("Hace más de una semana", 7*24*60*60, Long.MAX_VALUE);


        private String relativeTime;
        private final long secondsLowerBound;
        private final long secondsUpperBound;

        RelativeTime(String relativeTime, long secondsLowerBound, long secondsUpperBound) {
            this.relativeTime = relativeTime;
            this.secondsUpperBound = secondsUpperBound;
            this.secondsLowerBound = secondsLowerBound;
        }

        public boolean isInRange(long seconds) {
            return secondsLowerBound <= seconds && seconds < secondsUpperBound;
        }
        public String getRelativeTime(){
            return relativeTime;
        }

    }

    public static RelativeTime toRelativeTime(LocalDateTime date) {
        long seconds = Duration.between(date, LocalDateTime.now()).getSeconds();
        for (RelativeTime x: RelativeTime.values())
            if (x.isInRange(seconds))
                return x;
        return RelativeTime.LONG_TIME_AGO;
    }


    private LastConnectionUtils() {

    }
}
