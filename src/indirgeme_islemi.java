import javafx.util.Pair;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;



class indirgeme_islemi {
	public static List<Point> yollamaList = new ArrayList<>();
	public static ArrayList<Point> yollamaList1 = new ArrayList<>();


	private static class Point extends Pair<Double, Double> {
		Point(Double key, Double value) {
			super(key, value);
		}

		@Override
		public String toString() {
			return String.format("%f,%f", getKey(), getValue());
		}
	}

	private static double perpendicularDistance(Point pt, Point lineStart, Point lineEnd) {
		double dx = lineEnd.getKey() - lineStart.getKey();
		double dy = lineEnd.getValue() - lineStart.getValue();

		// Normalize
		double mag = Math.hypot(dx, dy);
		if (mag > 0.0) {
			dx /= mag;
			dy /= mag;
		}
		double pvx = pt.getKey() - lineStart.getKey();
		double pvy = pt.getValue() - lineStart.getValue();

		// Get dot product (project pv onto normalized direction)
		double pvdot = dx * pvx + dy * pvy;

		// Scale line direction vector and subtract it from pv
		double ax = pvx - pvdot * dx;
		double ay = pvy - pvdot * dy;

		return Math.hypot(ax, ay);
	}

	private static void ramerDouglasPeucker(List<Point> pointList, double epsilon, List<Point> out) {
		if (pointList.size() < 2)
			throw new IllegalArgumentException("Not enough points to simplify");

		// Find the point with the maximum distance from line between the start and end
		double dmax = 0.0;
		int index = 0;
		int end = pointList.size() - 1;
		for (int i = 1; i < end; ++i) {
			double d = perpendicularDistance(pointList.get(i), pointList.get(0), pointList.get(end));
			if (d > dmax) {
				index = i;
				dmax = d;
			}
		}

		// If max distance is greater than epsilon, recursively simplify
		if (dmax > epsilon) {
			List<Point> recResults1 = new ArrayList<>();
			List<Point> recResults2 = new ArrayList<>();
			List<Point> firstLine = pointList.subList(0, index + 1);
			List<Point> lastLine = pointList.subList(index, pointList.size());
			ramerDouglasPeucker(firstLine, epsilon, recResults1);
			ramerDouglasPeucker(lastLine, epsilon, recResults2);

			// build the result list
			out.addAll(recResults1.subList(0, recResults1.size() - 1));
			out.addAll(recResults2);
			if (out.size() < 2)
				throw new RuntimeException("Problem assembling output");
		} else {
			// Just return start and end points
			out.clear();
			out.add(pointList.get(0));
			out.add(pointList.get(pointList.size() - 1));
		}
	}

	public void indirgeme() {
		Server veri = new Server();

		List<Point> pointList = new ArrayList<Point>();
		for (int i = 0; i <Server.sayac*2; i+=2) {      // Clientten gelen verileri pointListe atýyor.

			pointList.add(new Point(Server.gelen_veri_bolunmus.get(i),Server.gelen_veri_bolunmus.get(i+1)));
		}
		List<Point> pointListOut = new ArrayList<>();
		ramerDouglasPeucker(pointList,0.0001,pointListOut);
		//Point Listi indirgemenin olacagi fonksiyona yönlendiriyor.
		System.out.println("Indirgenen veriler:");
		yollamaList=null;
		yollamaList=pointListOut;
		pointListOut.forEach(System.out::println);  //Indirgenen veriyi ekrana yazdýrma.
		
	}
}
