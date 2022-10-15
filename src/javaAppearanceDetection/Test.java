package javaAppearanceDetection;

import java.util.*;


import org.opencv.imgproc.*;
import org.opencv.features2d.*;
import org.opencv.imgcodecs.*;
import org.opencv.highgui.*;
import org.opencv.videoio.*;

import org.opencv.core.*;

public class Test {
	private static int yessuh (Mat img, Mat imgTrain, ORB orb) {
			
		final float threshold = 0.75f;
			
		MatOfKeyPoint kpQuery = new MatOfKeyPoint();
		MatOfKeyPoint kpTrain = new MatOfKeyPoint();
		Mat decsTrain = new Mat();
		Mat decsQuery = new Mat();
		Mat mask = new Mat();
		Mat imgGray = new Mat();
			
		List<MatOfDMatch> knn_matches = new ArrayList<MatOfDMatch>();
		List<DMatch> good_matches = new ArrayList<DMatch>();
			
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
		Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
	
			
			orb.detectAndCompute(imgTrain, mask, kpTrain, decsTrain);
			orb.detectAndCompute(imgGray, mask, kpQuery, decsQuery);
			
			matcher.knnMatch(decsQuery, decsTrain, knn_matches, 2);	
			
			
			for (int i = 0; i < knn_matches.size(); i++) {
				List<DMatch> mylist = knn_matches.get(i).toList();
										
				if (mylist.get(0).distance < threshold * mylist.get(1).distance) {
					good_matches.add(mylist.get(0));
				}					
			}
			
			return good_matches.size();
		
	}

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				
		VideoCapture cap = new VideoCapture(1);
		
		cap.open(1);
		
		Mat imgTrain = Imgcodecs.imread("C:\\Users\\Administrator\\source\\repos\\OpenCV_Course\\OpenCV_Course\\Resources\\kaisa.jfif", Imgcodecs.IMREAD_GRAYSCALE);
		
		ORB orb = ORB.create(1000);		
		
		Mat img = new Mat();
		
		int frame_counter = 0;
		
		while (true) {			

			try {
				cap.read(img);

				if (frame_counter % 5 == 0) {
					
					if (yessuh(img, imgTrain, orb) > 25) {
						System.out.println("KaisaBITCH");
					}
					
					
				}
				
				
				
				HighGui.imshow("FEED", img);
				
				HighGui.waitKey(1);
				
				++frame_counter;
			}
			
			catch (Exception e) {
				
			}	
		}
	}
		
	
		
		
	


}
