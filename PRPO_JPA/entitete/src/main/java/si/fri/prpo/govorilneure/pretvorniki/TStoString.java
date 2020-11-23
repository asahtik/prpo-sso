package si.fri.prpo.govorilneure.pretvorniki;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class TStoString {
    public static String TStoStr(long ts) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("+1"));
        return sdf.format(new Date(new Timestamp(ts).getTime()));
    }
}
