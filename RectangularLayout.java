import java.awt.Shape;
import java.awt.geom.Rectangle2D;


public class RectangularLayout implements BoardLayout {

	@Override
	public Shape mancalaLayout(int x, int y, int width, int height) {
		return new Rectangle2D.Double(x, y, width, height);
	}

	@Override
	public Shape pitsLayout(int x, int y, int width, int height) {
		return new Rectangle2D.Double(x, y, width, height);
	}

}
