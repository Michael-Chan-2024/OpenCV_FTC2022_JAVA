package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.util.Log;

import java.nio.file.Path;
import java.util.*;

// OPENCV IMPORTS
import org.opencv.imgproc.*;
import org.opencv.features2d.*;
import org.opencv.imgcodecs.*;
import org.opencv.core.*;
import org.openftc.easyopencv.OpenCvPipeline;


public class VisionPipeline extends OpenCvPipeline {


    public final String pantheon = "/data/data/com.qualcomm.ftcrobotcontroller/Images/ftc1v2.5.png";
        public final String logo = "/data/data/com.qualcomm.ftcrobotcontroller/Images/ftc2.png";
    public final String lightning = "/data/data/com.qualcomm.ftcrobotcontroller/Images/ftc3.png";


    //object creation
    private final ORB orb = ORB.create(1000);

    private final float threshold = 0.75f;

    //Mat creation
    private MatOfKeyPoint kpQuery = new MatOfKeyPoint();
    private MatOfKeyPoint kpTrain = new MatOfKeyPoint();
    public  Mat imgTrain = Imgcodecs.imread(pantheon, Imgcodecs.IMREAD_GRAYSCALE);
    private Mat imgTrain2 = Imgcodecs.imread(logo, Imgcodecs.IMREAD_GRAYSCALE);
    private Mat imgTrain3 = Imgcodecs.imread(lightning, Imgcodecs.IMREAD_GRAYSCALE);
    private Mat decsTrain = new Mat();
    private Mat decsQuery = new Mat();
    private Mat imgGray = new Mat();

    //List creation
    private List<MatOfDMatch> knn_matches = new ArrayList<MatOfDMatch>();
    public List<DMatch> good_matches = new ArrayList<DMatch>();
    public List<DMatch> good_matches2 = new ArrayList<DMatch>();
    public List<DMatch> good_matches3 = new ArrayList<DMatch>();

    DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);


    enum IMG {
        NONE_DETECTED,
        PANTHEON,
        LOGO,
        LIGHTNING
    }

    private IMG curr_img = IMG.NONE_DETECTED;
    private int frame_counter = 0;
    private int counter = 0;

    @Override
    public void init(Mat firstFrame) {

    }
    @Override
    public Mat processFrame(Mat input) {
        frame_counter++;
        Imgproc.cvtColor(input, imgGray, Imgproc.COLOR_RGBA2GRAY);
        if (frame_counter % 15 == 0) {
            if (calcMatches(imgGray, this.imgTrain, this.orb, this.matcher, this.decsQuery,
                    this.decsTrain, this.kpQuery, this.kpTrain,
                    this.knn_matches, this.good_matches) >= 15) {

                curr_img = IMG.PANTHEON;

            } else if (calcMatches(imgGray, this.imgTrain2, this.orb, this.matcher, this.decsQuery,
                    this.decsTrain, this.kpQuery, this.kpTrain,
                    this.knn_matches, this.good_matches2) >= 15) {

                curr_img = IMG.LOGO;

            } else if (calcMatches(imgGray, this.imgTrain3, this.orb, this.matcher, this.decsQuery,
                    this.decsTrain, this.kpQuery, this.kpTrain,
                    this.knn_matches, this.good_matches3) >= 15) {

                curr_img = IMG.LIGHTNING;

            } else {
                curr_img = IMG.NONE_DETECTED;
            }
        }
        return imgGray;
    }

    private int calcMatches (Mat inputImgQuery, Mat inputImgTrain, ORB func_orb, DescriptorMatcher decsMatcher, Mat decsQuery, Mat decsTrain,
                             MatOfKeyPoint kpQuery, MatOfKeyPoint kpTrain, List<MatOfDMatch> knn_matches, List<DMatch> good_matches) {

        good_matches.clear();

        func_orb.detectAndCompute(inputImgTrain, new Mat(), kpTrain, decsTrain);
        func_orb.detectAndCompute(inputImgQuery, new Mat(), kpQuery, decsQuery);
        decsMatcher.knnMatch(decsQuery, decsTrain, knn_matches, 2);

        for (int i = 0; i < knn_matches.size(); i++) {
            List<DMatch> mylist = knn_matches.get(i).toList();

            if (mylist.get(0).distance < this.threshold * mylist.get(1).distance) {
                good_matches.add(mylist.get(0));
            }
        }

        return good_matches.size();

    }

    public IMG getImg() {
        return this.curr_img;
    }

}

