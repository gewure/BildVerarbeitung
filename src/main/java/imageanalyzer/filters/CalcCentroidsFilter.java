package imageanalyzer.filters;

import imageanalyzer.datacontainers.Coordinate;
import thirdparty.filter.DataEnrichmentFilter;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import javax.media.jai.PlanarImage;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This filter expects the bonding discs to be completely white: pixel value of 255 on a scale of 0..255
 * all other pixels in the image are expected to have a pixel value < 255
 * use this filter adapting eventually the package name
 */
public class CalcCentroidsFilter extends DataEnrichmentFilter<PlanarImage, LinkedList<Coordinate>>{

	private HashMap<Coordinate, Boolean> _general = new HashMap<>();
	private LinkedList<LinkedList<Coordinate>> _figures = new LinkedList<>();
	private PlanarImage _image;


	public CalcCentroidsFilter(Readable<PlanarImage> input) throws InvalidParameterException {
		super(input);
	}

	public CalcCentroidsFilter(Writable<LinkedList<Coordinate>> output) throws InvalidParameterException {
		super(output);
	}

	@Override
	protected boolean fillEntity(PlanarImage nextVal, LinkedList<Coordinate> entity) {
		_image = nextVal;

//        Collections.addAll(entity, process(nextVal));
		process(nextVal);
		entity = null;
        return true;
	}

	@Override
	protected LinkedList<Coordinate> getNewEntityObject() {
		return new LinkedList<>();
	}

	private Coordinate[] process(PlanarImage entity) {
		BufferedImage bi = entity.getAsBufferedImage();

		for (int x = 0; x < bi.getWidth(); x++){
			for (int y = 0; y < bi.getHeight(); y++){
				int p = bi.getRaster().getSample(x, y, 0);
				if (p==255 && _general.containsKey(new Coordinate(x,y)) == false){
					//if there is a not visited white pixel, save all pixels belonging to the same figure
                    getNextFigure(bi, x, y);
				}
			}
		}

		return calculateCentroids();	//calculate the centroids of all figures
	}

	private void getNextFigure(BufferedImage img, int x, int y){
		LinkedList<Coordinate> figure = new LinkedList<>();
		_general.put(new Coordinate(x,y), true);
		figure.add(new Coordinate(x,y));

		addConnectedComponents(img, figure, x, y);

		_figures.add(figure);
	}

	private void addConnectedComponents(BufferedImage img, LinkedList<Coordinate> figure, int x, int y){
		if (x-1>=0 && _general.containsKey((new Coordinate(x-1, y))) == false && img.getRaster().getSample(x-1, y, 0) == 255){
			_general.put(new Coordinate(x-1,y), true);
			figure.add(new Coordinate(x-1, y));
			addConnectedComponents(img, figure, x-1, y);
		}
		if (x+1<img.getWidth() && _general.containsKey((new Coordinate(x+1, y))) == false && img.getRaster().getSample(x+1, y, 0) == 255){
			_general.put(new Coordinate(x+1,y), true);
			figure.add(new Coordinate(x+1, y));
			addConnectedComponents(img, figure, x+1, y);
		}
		if (y-1>=0 && _general.containsKey((new Coordinate(x, y-1))) == false && img.getRaster().getSample(x, y-1, 0) == 255){
			_general.put(new Coordinate(x,y-1), true);
			figure.add(new Coordinate(x, y-1));
			addConnectedComponents(img, figure, x, y-1);
		}
		if (y+1 < img.getHeight() && _general.containsKey((new Coordinate(x, y+1))) == false && img.getRaster().getSample(x, y+1, 0) == 255){
			_general.put(new Coordinate(x,y+1), true);
			figure.add(new Coordinate(x, y+1));
			addConnectedComponents(img, figure, x, y+1);
		}
	}

	private Coordinate[] calculateCentroids(){
		Coordinate[] centroids = new Coordinate[_figures.size()];
		int i = 0;
		for (LinkedList<Coordinate> figure : _figures){
			LinkedList<Integer> xValues= new LinkedList<>();
			LinkedList<Integer> yValues= new LinkedList<>();

			for (Coordinate c : figure){
				xValues.add(c._x);
				yValues.add(c._y);
			}

			Collections.sort(xValues);
			Collections.sort(yValues);

			int xMedian = xValues.get(xValues.size() / 2);
			int yMedian = yValues.get(yValues.size() / 2);

			centroids[i] = new Coordinate(
                xMedian + (int) _image.getProperty("ThresholdX"),
                yMedian + (int) _image.getProperty("ThresholdY")
            );

			++i;
		}
		return centroids;
	}
}
