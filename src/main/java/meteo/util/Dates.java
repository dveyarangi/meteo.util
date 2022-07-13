package meteo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Dates {
	
	public static final long HOURms = 1000*60*60;
	public static final long DAYms = 24*HOURms;
	public static final DateFormat SIGNATURE_FMT = new SimpleDateFormat("yyyyMMdd_HHmm");

	public static enum Rounding 
	{
		FLOOR,
		ROUND,
		CEIL
	}
	
	public static long ceilToHours(long timeMs, int hourStep)
	{
		long hours = timeMs / HOURms;
		long flooredHours = hours - hours % hourStep;
		return flooredHours * HOURms + ((hours % hourStep > 0) ? HOURms*hourStep : 0);
	}

	
	public static long floorToHours(long timeMs, int hourStep)
	{
		long hours = timeMs / HOURms;
		long flooredHours = hours - hours % hourStep;
		return flooredHours * HOURms;
	}
	
	public static long [] createRange(long start, long end, long step)
	{
		int size = (int)((end-start)/step + 1);
		long [] times = new long[size];
		int i = 0;
		for(long time = start; time <= end; time += step)
			times[i++] = time;
		return times;
	}
	
	public static Date [] createDateRange(long start, long end, long step)
	{
		long [] timestamps = createRange(start, end, step);
		Date [] dates = new Date[timestamps.length];
		for(int idx = 0; idx < timestamps.length; idx ++)
			dates[idx] = new Date(timestamps[idx]);
		return dates;
	}
	
	public static void main(String [] args)
	{
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		long now = System.currentTimeMillis();
		System.out.println(new Date(now));
		System.out.println(new Date(floorToHours(now, 24)));
		System.out.println(new Date(floorToHours(now, 12)));
		System.out.println(new Date(floorToHours(now, 6)));
		System.out.println(new Date(floorToHours(now, 3)));
		System.out.println(new Date(floorToHours(now, 1)));
	}
	
	public static final String DATESTAMP_FMR = "yyyyMMdd";
	public static final String TIMESTAMP_FMR = "HHmm";
	
	
	public static final String UNIT_MS = "ms";
	public static final String UNIT_SECOND = "sec";
	public static final String UNIT_MINUTE = "min";
	public static final String UNIT_HOUR = "hr";
	public static final String UNIT_DAY = "d";
	public static final String UNIT_WEEK = "w";
	
	private static final long [] INTERVAL_CARDINALS = new long [] { 1000l, 1000l*60, 1000l*60*60, 1000l*60*60*24, 1000l*60*60*24*7 };   
	private static final String [] INTERVAL_UNITS = new String [] { "ms", "sec", "min", "hr", "d", "w" };   
	public static String toRoughIntervalString(long intervalMs) 
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < INTERVAL_CARDINALS.length; i ++)
		{
			long boundary = INTERVAL_CARDINALS[i];
			long pboundary = i > 0 ? INTERVAL_CARDINALS[i-1] : 1;
			String unit = INTERVAL_UNITS[i];
			if(intervalMs < boundary || i == INTERVAL_CARDINALS.length-1)
			{
				return sb.append(intervalMs/pboundary).append(unit).toString();
			}
		}
		
		throw new IllegalArgumentException("Overflow!");
	}
}
