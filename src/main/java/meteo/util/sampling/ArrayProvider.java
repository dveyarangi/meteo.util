package meteo.util.sampling;

import lombok.AllArgsConstructor;

public interface ArrayProvider <E>
{
	@AllArgsConstructor
	public static class Wrapper <E>implements ArrayProvider<E> {
		
		E[][] arr;
		
		@Override public E at( int i, int j ) { return arr[i][j]; }

		@Override public int si() { return arr.length; }

		@Override public int sj() { return arr[0].length; }
		
	}
	
	public static <E> ArrayProvider <E> wrap(E[][] arr) { return new Wrapper<E>(arr); }
	
	
	public int si();
	public int sj();
	
	public E at(int i, int j);
}
