package org.firstinspires.ftc.teamcode;

import java.util.*;

// OPENCV IMPORTS
import org.opencv.imgproc.*;
import org.opencv.features2d.*;
import org.opencv.imgcodecs.*;
import org.opencv.core.*;
import org.openftc.easyopencv.OpenCvPipeline;


public class VisionPipeline extends OpenCvPipeline {

    private String imgPath, imgPath2, imgPath3;
    public VisionPipeline(String trainImgPath, String trainImgPath2, String trainImgPath3) {
        imgPath = trainImgPath;
        imgPath2 = trainImgPath2;
        imgPath3 = trainImgPath3;
    }

    //object creation
    private final ORB orb = ORB.create(1000);
    private final robotDeclarations robot = new robotDeclarations();

    private final float threshold = 0.75f;
    private int imgNumber = 0;

    //Mat creation
    private MatOfKeyPoint kpQuery = new MatOfKeyPoint();
    private MatOfKeyPoint kpTrain = new MatOfKeyPoint();
    private Mat imgTrain = Imgcodecs.imread(imgPath, Imgcodecs.IMREAD_GRAYSCALE);
    private Mat imgTrain2 = Imgcodecs.imread(imgPath2, Imgcodecs.IMREAD_GRAYSCALE);
    private Mat imgTrain3 = Imgcodecs.imread(imgPath3, Imgcodecs.IMREAD_GRAYSCALE);
    private Mat decsTrain = new Mat();
    private Mat decsQuery = new Mat();
    private Mat mask = new Mat();
    private Mat imgGray = new Mat();

    //List creation
    private List<MatOfDMatch> knn_matches = new ArrayList<MatOfDMatch>();
    private List<DMatch> good_matches = new ArrayList<DMatch>();

    @Override
    public void init (Mat firstFrame) {

    }

    @Override
    public Mat processFrame(Mat inputImg) {
        try {
            if (calcMatches(inputImg, imgTrain, orb) > 25) {
                imgNumber = 0;
            }
            else if (calcMatches(inputImg, imgTrain2, orb) > 25) {
                imgNumber = 1;
            }
            else if (calcMatches(inputImg, imgTrain3, orb) > 25) {
                imgNumber = 2;
            }
        }
        catch (Exception e) {

        }
        return inputImg;
    }

    private int calcMatches (Mat img, Mat imgTrain, ORB orb) {

        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_RGBA2GRAY);

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

    public int getImg() {
        return imgNumber;
    }

}

