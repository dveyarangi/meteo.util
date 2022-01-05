package meteo.util.heatmaps;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import lombok.extern.slf4j.Slf4j;
import meteo.util.colors.Colormap;
import meteo.util.colors.ColormapConf;
import meteo.util.colors.Colormaps;


@Slf4j
public class HeatmapImage
{

	static ColormapConf DEFAULT_COLORMAP;
	static {
		try (InputStream in = HeatmapImage.class.getResourceAsStream("Chromatic")){
			DEFAULT_COLORMAP = Colormaps.read(new InputStreamReader(in));
		} catch(IOException e) { throw new RuntimeException(e);	}
	}
	public static void makeImage(float [][] heatmap, int scale, String path, Array<HeatmapLayer> layers )
	{
		makeImage(heatmap, scale, DEFAULT_COLORMAP, Heightmap.minmax(heatmap), path, layers);
	}
	public static void makeImage(float [][] heatmap, int scale, ColormapConf colormap, float min, float max, String path, Array<HeatmapLayer> layers )
	{
		makeImage(heatmap, scale, colormap, new Vector2(min, max), path, layers);
	}
	public static void makeImage(float [][] heatmap, int scale, ColormapConf colormap, Vector2 minmax, String path, Array<HeatmapLayer> layers )
	{

		int w = heatmap[0].length;
		int h = heatmap.length;


		Colormap cm = new Colormap(minmax, colormap);


		BufferedImage image = new BufferedImage(w*scale, h*scale, BufferedImage.TYPE_INT_ARGB);
	
		// draw heatmap
		Color color = new Color();
		for(int x = 0; x < w*scale; x ++)
			for(int y = 0; y < h*scale; y ++)
			{
				cm.toColor(heatmap[x/scale][y/scale], color);
				image.setRGB(x, y, Color.argb8888(color));
			}
	
		// add layers
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		if( layers != null)
			for(HeatmapLayer layer : layers)
				layer.render(g2d, scale);
	
//		BufferedImage image = new BufferedImage(w*scale, h*scale, BufferedImage.TYPE_INT_ARGB);
		// flip vertically
		for(int x = 0; x < w*scale; x ++)
			for(int y = 0; y < h*scale/2; y ++)
			{
				int y2 = h*scale-y-1;
				int rgb = image.getRGB(x,y);
				image.setRGB(x, y, image.getRGB(x,y2));
				image.setRGB(x,y2,rgb);
			}
		
		File outfile =  new File(path);
		try
		{
			log.debug("Writing debug heatmap image to " + outfile.getAbsolutePath() + "...");
			ImageIO.write(image, "png", outfile);
		}
		catch (IOException e) { log.error("Failed to write heatmap debug image to " + outfile.getAbsolutePath()); }
	}

}
