package meteo.util.heatmaps;

import java.awt.Graphics2D;

public interface HeatmapLayer {

	void render(Graphics2D g2d, float scale);

}
