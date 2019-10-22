package meteo.common.util.geodesy;

import meteo.common.util.IParametricSurface;
import meteo.common.util.Vector3;

/**
 * 
 * @author Fima
 *
 */
public class ParametricGeoid implements IParametricSurface 
{
	
	private Datum datum;
	
	public ParametricGeoid(Datum datum)
	{
		this.datum = datum;
	}
	

	@Override
	public Vector3 at(double u, double v, Vector3 result) {
		return null;
	}

}
