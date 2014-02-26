import org.opencv.core.Mat;


public interface CaptureProcessor {
	public void processImage(Mat img);
}
