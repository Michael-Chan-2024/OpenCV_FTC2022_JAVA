package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
import org.opencv.imgcodecs.Imgcodecs;


@TeleOp(name="Autonomous", group="Pushbot")
public class Auto extends LinearOpMode {



    //private Mechanum mechanumDrive = new Mechanum();
    VisionPipeline visionPipeline = new VisionPipeline();
    robotDeclarations robot = new robotDeclarations();

    OpenCvWebcam webcam;
    WebcamName webcamName;


    @Override
    public void runOpMode() {
        telemetry.addData("Status:", "Initilized");
        initObjects();



        OpenCvWebcam webcam = OpenCvCameraFactory.getInstance().createWebcam(robot.hwMap.get(WebcamName.class,
                "Webcam 1"));

        webcam.setPipeline(visionPipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
                telemetry.addData("Streaming Status: ", "Streaming");
                telemetry.update();
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Streaming Status: ", "ERR, COULD NOT STREAM");
                telemetry.update();
            }
        });


        telemetry.addLine("Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            switch(visionPipeline.getImg()) {
                case PANTHEON:
                    telemetry.addData("Curr_img", "Kiasa");
                    break;
                case LOGO:
                    telemetry.addData("Curr_img", "Viktor");
                    break;
                case LIGHTNING:
                    telemetry.addData("Curr_img", "Vladimir");
                    break;
                case NONE_DETECTED:
                    telemetry.addData("Curr_img", "NONE DETECTED");

            }

            telemetry.addData("Size", visionPipeline.good_matches.size());
            telemetry.addData("Size", visionPipeline.good_matches2.size());
            telemetry.addData("Size", visionPipeline.good_matches3.size());
            telemetry.update();
        }

        //for testing
        //mechanumDrive.strafe(this, 1, 1, 2, 'v');

    }

    public void initObjects() {
        //mechanumDrive.init(this);
        robot.init(this.hardwareMap);
    }

    public void shutdownObjects() {
        //mechanumDrive.shutdown(this);
        // Vision.shutdown(this);
    }

}
