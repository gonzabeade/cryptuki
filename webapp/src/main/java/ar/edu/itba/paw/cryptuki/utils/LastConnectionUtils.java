package ar.edu.itba.paw.cryptuki.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class LastConnectionUtils {

    public enum RelativeTime {
        ONLINE("online", 0, 60),
        LESS_FIVE("5mins", 60, 300),
        LESS_THIRTY("30mins", 300, 30*60),
        LESS_HOUR("1hr", 30*60, 60*60),
        TODAY("today", 60*60, 24*60*60),
        YESTERDAY("yesterday", 24*60*60, 2*24*60*60),
        THIS_WEEK("thisweek", 2*24*60*60, 7*24*60*60),
        LONG_TIME_AGO("morethanaweek", 7*24*60*60, Long.MAX_VALUE);


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
