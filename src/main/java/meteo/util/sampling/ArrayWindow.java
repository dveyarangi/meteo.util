package meteo.util.sampling;

public class ArrayWindow <E>
{
	private int xoff;
	private int yoff;
	private int sx;
	private int sy;
	private E[][] arr;

	public ArrayWindow(E [][] arr, int xoff, int yoff, int sx, int sy)
	{
		this.arr = arr;
		this.xoff = xoff;
		this.yoff = yoff;
		this.sx = sx;
		this.sy = sy;
	}
	
	public int sx() { return sx; }
	public int sy() { return sy; }
	
	public E at(int x, int y) { return arr[xoff+x][yoff+y]; }
}
