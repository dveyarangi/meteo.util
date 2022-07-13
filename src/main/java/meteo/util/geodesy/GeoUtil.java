package meteo.util.geodesy;

import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import meteo.util.Angles;
import meteo.util.Vector3;

public class GeoUtil
{


	public static double getEarthRadiusKm(double lat)
	{
//		System.out.println("");
		return getEarthRadiusKm(lat, Datum.WGS_84);
	}

	public static double getEarthRadiusKm(double lat, Datum datum)
	{
		double sin = Math.sin( lat*Angles.TO_RAD );
		double cos = Math.cos( lat*Angles.TO_RAD );
		double a = datum.getEquatorialRadius();
		double b = datum.getPolarRadius();

		return Math.sqrt(((a*a*cos)*(a*a*cos) + (b*b*sin)*(b*b*sin)) / ((a*cos)*(a*cos) + (b*sin)*(b*sin)));
	}

	/**
	 * Returns spherical coordinates for provided vector

	 * @param v
	 * @return
	 */
	public static PolarCoord toGeodeticCoords( final Vector3 v )
	{
		return toGeodeticCoords( v.x(), v.y(), v.z() );
	}
	public static PolarCoord toGeodeticCoords( final double x, final double y, final double z)
	{
		double radius = Math.sqrt( x*x + y*y + z*z );
		double theta = Math.atan( y / x ) * Angles.TO_DEG;
		double phi = Math.acos( z / radius ) * Angles.TO_DEG;
		double longitude = theta;
		double latitude = 90 - phi;

		return new PolarCoord(
				radius,
				latitude,
				longitude
			);
	}

	public static PolarCoord toSphericalCoords( final double x, final double y, final double z)
	{
		double radius = Math.sqrt( x*x + y*y + z*z );

		double phi = Math.acos( z / radius ) * Angles.TO_DEG;
		double theta = Math.atan( y / x ) * Angles.TO_DEG;

		return new PolarCoord(
				radius,
				phi,
				theta
			);
	}

	/*public static com.badlogic.gdx.math.Vector3 toCartesianVector( float lat, float lon, float distance )
	{
		return new com.badlogic.gdx.math.Vector3(
				Math.sin( (90-lat)*Angles.TO_RAD ) * Math.cos( lon*Angles.TO_RAD ) * distance,
				Math.sin( (90-lat)*Angles.TO_RAD ) * Math.sin( lon*Angles.TO_RAD ) * distance,
				Math.cos( (90-lat)*Angles.TO_RAD ) * distance
			);

	}*/
	
	/**
	 * Calculates haversine formula
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @param datum
	 * @return
	 */
	public static double calcDistance(double lat1, double lon1, double lat2, double lon2, Datum datum)
	{
		double latr1 = lat1*Angles.TO_RAD;
		double latr2 = lat2*Angles.TO_RAD;
		double lotr1 = lon1*Angles.TO_RAD;
		double lotr2 = lon2*Angles.TO_RAD;
		double earthRadius = getEarthRadiusKm((lat2-lat1)/2);
		return 2*earthRadius*asin(sqrt(
					pow(sin((latr2-latr1)/2), 2) +
					cos(latr1)*cos(latr2)*pow(sin(lotr2-lotr1)/2, 2)
				));
	
	}
	
	/**
	 * Calculates haversine formula,
	 * assuming earth radius as average WSG84 radius at middle lat between lat1 and lat2.
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public static double calcDistance(double lat1, double lon1, double lat2, double lon2)
	{
		return calcDistance(lat1, lon1, lat2, lon2, Datum.WGS_84);
	}	
	public static void main(String [] args)
	{
		System.out.println("Telaviv-Haifa:" + calcDistance(32.073, 34.773, 32.807, 34.984));
		System.out.println("Haifa-Antalia:" + calcDistance(32.807, 34.984, 36.89372, 30.70871));
		System.out.println("Antalia-London:" + calcDistance(36.89372, 30.70871, 51.50838,-0.16290));
	}
}
