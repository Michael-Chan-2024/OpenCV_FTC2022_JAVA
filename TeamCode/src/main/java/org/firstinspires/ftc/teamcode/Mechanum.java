package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


public class Mechanum {

    robotDeclarations robot = new robotDeclarations();
    BNO055IMU imu = robot.hwMap.get(BNO055IMU.class, "imu");
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    //runs this code once this method is called
    public void operate(OpMode opMode, Gamepad gamepad) {
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        drive(gamepad);
    }

    public void init(OpMode opMode) {
        imu.initialize(parameters);
        robot.init(opMode.hardwareMap);
    }

    public void shutdown(OpMode opMode) {
        robot.backRightMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        robot.frontLeftMotor.setPower(0);
    }

    //mechanum drive for the robot (field oriented)
    public void drive(Gamepad gamepad) {
        double leftX = gamepad.left_stick_x;

        //multiply by a factor of x to counteract imperfect strafing
        double leftY = -gamepad.left_stick_y * 1.1;

        double rightX = gamepad.right_stick_x;

        double botHeading = -imu.getAngularOrientation().firstAngle;

        double rotX = leftX * Math.cos(botHeading) - leftY * Math.sin(botHeading);
        double rotY = leftX * Math.sin(botHeading) + leftY * Math.cos(botHeading);

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(leftY) + Math.abs(leftX) + Math.abs(rightX), 1);
        double frontLeftPower = (rotY - rotX - rightX) / denominator;
        double backLeftPower = (rotY + rotX - rightX) / denominator;
        double frontRightPower = (rotY + rotX + rightX) / denominator;
        double backRightPower = (rotY - rotX + rightX) / denominator;

        //if the left trigger is pressed past the halfway point, activates the half speed drive
        if(gamepad.left_trigger >= 0.5) {
            robot.frontLeftMotor.setPower(frontLeftPower * 0.5);
            robot.backLeftMotor.setPower(backLeftPower * 0.5);
            robot.frontRightMotor.setPower(frontRightPower * 0.5);
            robot.backRightMotor.setPower(backLeftPower * 0.5);
        }
        //sets the motors to full power otherwise
        else {
            robot.frontLeftMotor.setPower(frontLeftPower);
            robot.backLeftMotor.setPower(backLeftPower);
            robot.frontRightMotor.setPower(frontRightPower);
            robot.backRightMotor.setPower(backRightPower);
        }
    }
    /*
    *  Makes the robot move in a horizontal or vertical direction using encoders during auto.
    *  Takes a double that specifies the motor power, a double that specifies how far it'll travel,
    *  a double that specifies a time limit, and a char that specifies the direction the robot
    *  will move.
    *
    * Enter 'h' for horizontal or 'v' for vertical movement.
    */
    public void strafe(LinearOpMode opMode, double power, double distance, double timeOut,
                       char direction) {
        robot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Initializing variables that store the target position.
        double backLeftTarget = 0;
        double backRightTarget = 0;
        double frontRightTarget = 0;
        double frontLeftTarget = 0;

        // Checks if the user inputs 'h' or 'v' for the direction.
        // Changes the target position math depending on the direction.
        if(direction == 'v') {
            backLeftTarget = robot.backLeftMotor.getCurrentPosition() + robot.ticksPerInch * distance;
            backRightTarget = robot.backRightMotor.getCurrentPosition() + robot.ticksPerInch * distance;
            frontRightTarget = robot.frontRightMotor.getCurrentPosition() + robot.ticksPerInch * distance;
            frontLeftTarget = robot.frontLeftMotor.getCurrentPosition() + robot.ticksPerInch * distance;
        }
        else if(direction == 'h') {
            backLeftTarget = -(robot.backLeftMotor.getCurrentPosition() + robot.ticksPerInch * distance);
            backRightTarget = robot.backRightMotor.getCurrentPosition() + robot.ticksPerInch * distance;
            frontRightTarget = -(robot.frontRightMotor.getCurrentPosition() + robot.ticksPerInch * distance);
            frontLeftTarget = robot.frontLeftMotor.getCurrentPosition() + robot.ticksPerInch * distance;
        }

        // Sets the target position for each drive motors.
        robot.backLeftMotor.setTargetPosition((int)backLeftTarget);
        robot.backRightMotor.setTargetPosition((int)backRightTarget);
        robot.frontLeftMotor.setTargetPosition((int)frontLeftTarget);
        robot.frontRightMotor.setTargetPosition((int)frontRightTarget);

        // Switches mode to run to position.
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Resets timer to 0.
        robot.runtime.reset();

        // Switches the motor's power depending on 'h' or 'v' direction.
        //Note: horizontal strafing reverses certain motors, vertical doesn't.
        if(direction == 'v') {
            robot.backLeftMotor.setPower(power);
            robot.backRightMotor.setPower(power);
            robot.frontLeftMotor.setPower(power);
            robot.frontRightMotor.setPower(power);

        }
        else if(direction == 'h') {
            robot.backLeftMotor.setPower(-power);
            robot.backRightMotor.setPower(power);
            robot.frontLeftMotor.setPower(power);
            robot.frontRightMotor.setPower(-power);
        }

        // infinitely runs until either the motors have reached their target position or
        // the program reaches the time limit.
        while (robot.runtime.seconds() < timeOut && robot.checkBusy()) {

        }

        // Turn off all drive motors.
        robot.backLeftMotor.setPower(0);
        robot.backRightMotor.setPower(0);
        robot.frontLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);

        //turn off RUN_TO_POSITION
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
