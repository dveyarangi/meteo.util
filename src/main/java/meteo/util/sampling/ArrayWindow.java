package meteo.util.sampling;

public class ArrayWindow <E>
{
	protected int xoff;
	protected int yoff;
	private int sx;
	private int sy;
	protected ArrayProvider <E> arr;

	public ArrayWindow(ArrayProvider <E> arr, int xoff, int yoff, int sx, int sy)
	{
		this.arr = arr;
		this.xoff = xoff;
		this.yoff = yoff;
		this.sx = sx;
		this.sy = sy;
	}
	
	public ArrayWindow(E [][] arr, int xoff, int yoff, int sx, int sy)
	{
		this(ArrayProvider.wrap(arr), xoff, yoff, sx, sy);
	}
	
	public int sx() { return sx; }
	public int sy() { return sy; }
	
	public E at(int x, int y) { return arr.at(xoff+x,yoff+y); }
}
