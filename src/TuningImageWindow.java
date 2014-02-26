import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;


public class TuningImageWindow extends ImageWindow  implements MouseListener {

	Mat clickimg;
	boolean isFirstCalibrate = true;
	double[] highThresh = {0,0,0};
	double[] lowThresh = {0,0,0};
	
	public TuningImageWindow(String title) {
		super(title);
		lbl.addMouseListener(this);
	}
	
	public void setClickPointImage(Mat clickimg)
	{
		synchronized(lockObj)
		{
			this.clickimg = clickimg;
		}
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		if ( this.clickimg != null )
		{
			synchronized(lockObj)
			{
				double[] point = clickimg.get(e.getX(), e.getY());
				if ( point != null )
				{
					if ( isFirstCalibrate )
					{
						highThresh = point;
						lowThresh = point;
						System.out.println("Set first calibration to "+point[0]+" "+point[1]+" "+point[2]);
						isFirstCalibrate = false;
					}
					else
					{
						for ( int i = 0; i < 3; i++ )
						{
							if ( lowThresh[i] > point[i] )
							{
								System.out.println("Set lowThresh "+i+" to "+point[i]);
								lowThresh[i] = point[i];
							}
							else if ( highThresh[i] < point[i] )
							{
								System.out.println("Set highThresh to "+i+" to "+point[i]);
								highThresh[i] = point[i];
							}
						}
					}
				}
			}
		}
		
	}
	
	
	public Scalar getHighThresh()
	{
		return new Scalar(highThresh);
	}
	
	public Scalar getLowThresh()
	{
		return new Scalar(lowThresh);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
