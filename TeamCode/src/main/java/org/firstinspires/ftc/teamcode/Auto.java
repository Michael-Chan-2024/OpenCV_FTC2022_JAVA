package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;


public class Auto extends LinearOpMode {

    private String kaisa = "C:\\Users\\Administrator\\source\\repos\\OpenCV_Course\\OpenCV_Course\\Resources\\kaisa.jfif";
    private String victor = "C:\\Users\\Student Services\\Downloads\\viktor.jpg";
    private String vladimir = "C:\\Users\\Student Services\\Downloads\\vladimir.jpg";

    private Mechanum mechanumDrive = new Mechanum();
    private VisionPipeline visionPipeline = new VisionPipeline(kaisa, victor, vladimir);
    private robotDeclarations robot = new robotDeclarations();

    OpenCvCamera camera;
    WebcamName webcamName;

    @Override
    public void runOpMode() {

        webcamName = this.hardwareMap.get(WebcamName.class, "Webcam 1");
        int cameraMonitorViewId = this.hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewID", "id", this.hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        camera.setPipeline(visionPipeline);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        waitForStart();

        while (opModeIsActive()) {
            if (visionPipeline.getImg() == 0) {
                telemetry.addData("Image Shown", "KAISA");
            }
            else if (visionPipeline.getImg() == 1) {
                telemetry.addData("Image Shown", "VICTORR");
            }
            else if (visionPipeline.getImg() == 2) {
                telemetry.addData("Image Shown", "VLATAMIDRRR");
            }

        }

        //for testing
        mechanumDrive.strafe(this, 1, 1, 2, 'v');

    }

    public void initObjects() {
        mechanumDrive.init(this);
        robot.init(this.hardwareMap);
    }

    public void shutdownObjects() {
        mechanumDrive.shutdown(this);
        // Vision.shutdown(this);
    }

}
