import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;


public class ImageWindow extends JFrame {

	public BufferedImage toImage(Mat img)
	{
		MatOfByte bytemat = new MatOfByte();
		Highgui.imencode(".jpg", img, bytemat);
		byte[] bytes = bytemat.toArray();
	
		InputStream in = new ByteArrayInputStream(bytes);
		BufferedImage im = null;
		try {
			im = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bytemat.release();
		return im;
	}
	Object lockObj = new Object();
	ImageIcon img = new ImageIcon();
	JLabel lbl = new JLabel();
	

	
	public ImageWindow(String title)
	{
		setTitle(title);
		add(lbl);
		lbl.setIcon(img);
		show();
	}
	
	public void setImage(Mat cvimg)
	{
		BufferedImage jImg = toImage(cvimg);
		img.setImage(jImg);
		lbl.repaint();
		resize(jImg.getWidth(), jImg.getHeight());
	}
	






}
