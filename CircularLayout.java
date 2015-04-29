import java.awt.Shape;
import java.awt.geom.Ellipse2D;


public class CircularLayout implements BoardLayout {

	@Override
	public Shape mancalaLayout(int x, int y, int width, int height) {
		return new Ellipse2D.Double(x, y, width, height);
	}

	@Override
	public Shape pitsLayout(int x, int y, int width, int height) {
		return new Ellipse2D.Double(x, y, width, height);
	}

}
