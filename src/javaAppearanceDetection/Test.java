package javaAppearanceDetection;

import java.util.*;


import org.opencv.imgproc.*;
import org.opencv.features2d.*;
import org.opencv.imgcodecs.*;
import org.opencv.highgui.*;
import org.opencv.videoio.*;

import org.opencv.core.*;

public class Test {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		final float threshold = 0.75f;
		
		VideoCapture cap = new VideoCapture(1);
		
		cap.open(1);
		
		
		MatOfKeyPoint kpTrain = new MatOfKeyPoint();
		MatOfKeyPoint kpQuery = new MatOfKeyPoint();
		Mat decsTrain = new Mat();
		Mat decsQuery = new Mat();
		
		Mat imgTrain = Imgcodecs.imread("C:\\Users\\Administrator\\source\\repos\\OpenCV_Course\\OpenCV_Course\\Resources\\kaisa.jfif", Imgcodecs.IMREAD_GRAYSCALE);
	
		
//		HighGui.imshow("IMAGE", imgTrain);
//		HighGui.waitKey(0);
		
		ORB orb = ORB.create(1000);
		
		// Create empty mask to pass to detectAndCompute
		// since no mask is necessary.
		Mat mask = new Mat();
		orb.detectAndCompute(imgTrain, mask, kpTrain, decsTrain);
		
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
		
		List<MatOfDMatch> knn_matches = new ArrayList<MatOfDMatch>();
		List<DMatch> good_matches = new ArrayList<DMatch>();
		
		Mat img = new Mat();
		Mat imgGray = new Mat();
		
		int frame_counter = 0;
		
		while (true) {			

			try {
				cap.read(img);
				
				good_matches.clear();
				knn_matches.clear();
				
				if (frame_counter % 5 == 0) {
					Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
					
					orb.detectAndCompute(imgGray, mask, kpQuery, decsQuery);
					matcher.knnMatch(decsQuery, decsTrain, knn_matches, 2);	
					
					
					for (int i = 0; i < knn_matches.size(); i++) {
						List<DMatch> mylist = knn_matches.get(i).toList();
												
						if (mylist.get(0).distance < threshold * mylist.get(1).distance) {
							good_matches.add(mylist.get(0));
						}					
					}
					
					System.out.println(good_matches.size());
					
					if (good_matches.size() > 25) {
						System.out.println("KAISHSS HSEEEESH");
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
