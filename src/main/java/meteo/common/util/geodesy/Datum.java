package meteo.common.util.geodesy;


public abstract class Datum {

	public abstract double getEquatorialRadius();
	
	public abstract double getPolarRadius();
	
	public abstract String getName();
	
	public double getAverageRadius() { return getPolarRadius() + (getEquatorialRadius() - getPolarRadius()) / 2; }
	
	public static Datum WGS_84 = new Datum() 
	{
		public static final double EARTH_EQUATORIAL_RADIUS_KM = 6378.1370;
		public static final double EARTH_POLAR_RADIUS_KM = 6356.7523;

		@Override
		public double getEquatorialRadius() { return EARTH_EQUATORIAL_RADIUS_KM; }

		@Override
		public double getPolarRadius() { return EARTH_POLAR_RADIUS_KM; }

		@Override
		public String getName() { return "WGS84"; }
		
		
	};
	public static Datum SPHERICAL = new Datum() 
	{
		public static final double EARTH_RADIUS_KM = 6378;

		@Override
		public double getEquatorialRadius() { return EARTH_RADIUS_KM; }

		@Override
		public double getPolarRadius() { return EARTH_RADIUS_KM; }
		@Override
		public String getName() { return "SPHERICAL"; }

		
	};	

}
