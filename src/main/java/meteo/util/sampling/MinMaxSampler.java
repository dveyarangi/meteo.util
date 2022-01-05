package meteo.util.sampling;

import lombok.Getter;
/**
 * Given values via {@link #put(float)} method, will record the extrema values
 * 
 * @author Fima
 */
public class MinMaxSampler
{
	@Getter float min = Float.NaN;
	@Getter float max = Float.NaN;
	
	public void put(float value)
	{
		if(Float.isNaN(min) || value < min) min = value;
		if(Float.isNaN(max) || value > max) max = value;
	}
	
	@Override
	public String toString()
	{
		return String.format("min:%f max:%f", min, max);
	}
}
