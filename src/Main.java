import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.MatOfPoint;


public class Main implements CaptureProcessor {
	ImageWindow w = new ImageWindow("Capture");
	CaptureLoop cl = new CaptureLoop(0, this);
	
	double[] topThresh = {80.0,80.0,255.0};
	double[] bottomThresh = {0.0,0.0,150.0};
	
	public Main()
	{
		
	}
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		new Main();
		
	}
	boolean isFirstLoop = true;
	double firstPos;
	double lastPos;
	double lastTime;
	double lastVelocity;
	double startTime;
	@Override
	public void processImage(Mat img) {
		Mat threshImg = new Mat();
		Core.inRange(img, new Scalar(bottomThresh), new Scalar(topThresh), threshImg);
		
		Imgproc.erode(threshImg, threshImg, new Mat(), new Point(-1,-1), 1);
		Imgproc.dilate(threshImg, threshImg, new Mat(), new Point(-1, -1), 7);
		
		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>(); 
		
		Imgproc.findContours(threshImg, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		
		Rect biggestPoint = null;
		
		for ( int i = 0; i < contours.size(); i++ )
		{
			Rect r = Imgproc.minAreaRect(new MatOfPoint2f(contours.get(i).toArray())).boundingRect();
			
			if ( biggestPoint == null || r.area() > biggestPoint.area() )
				biggestPoint = r;
		}
		
		if ( biggestPoint != null )
		{
			Core.rectangle(img, biggestPoint.br(),biggestPoint.tl(),  new Scalar(0,255,0));
			
			if ( isFirstLoop )
			{
				lastTime = startTime = System.currentTimeMillis();
				firstPos = biggestPoint.x;
				lastVelocity = 0;
				isFirstLoop = false;
			}
			else
			{
				double now = System.currentTimeMillis();
				double position = (biggestPoint.x+(img.width()/2));
				double delta_time = (now-lastTime)/1000;
			
				double delta_position = position-lastPos;
				double velocity = delta_position/delta_time;
			
				double delta_velocity = velocity-lastVelocity;
				double acceleration = delta_velocity/delta_time;
			
				double time_since_start = (now-startTime)/1000;
				System.out.println(time_since_start+","+position+","+velocity+","+acceleration);
			
				lastTime = now;
				lastPos = position;
				lastVelocity = velocity;
			}
		}
		w.setImage(img);
	}

}
