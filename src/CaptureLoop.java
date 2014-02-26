import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;


public class CaptureLoop extends Thread {

	CaptureProcessor cp = null;
	VideoCapture cv = null;
	public CaptureLoop(int capID, CaptureProcessor cp)
	{
		this.cv = new VideoCapture(capID);
		this.cp = cp;
		
		start();
	}
	
	public void run()
	{
		while ( true )
		{
			Mat img = new Mat();
			cv.read(img);
			if ( cp != null )
				cp.processImage(img);
			img.release();
			try {
				Thread.sleep(34);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
