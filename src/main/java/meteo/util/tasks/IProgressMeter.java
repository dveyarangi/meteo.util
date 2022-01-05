package meteo.util.tasks;

public interface IProgressMeter
{

	void setProgress( int value, String label );

	Integer bar();

}
