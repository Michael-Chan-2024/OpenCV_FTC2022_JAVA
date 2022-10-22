//package org.firstinspires.ftc.teamcode;
//
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
//import com.qualcomm.robotcore.hardware.Gamepad;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.util.Range;
//
//// Class for pivot payload used in Teleop.
//public class Pivot {
//
//    //declares new robotDeclarations object (access motors)
//    robotDeclarations robot = new robotDeclarations();
//
//    // Runs payload code when called.
//    public void operate(OpMode opMode, Gamepad gamepad) {
//        turn(gamepad);
//    }
//
//    // Initializes the pivot motor used in this class.
//    public void init(OpMode opMode) {
//        robot.init(opMode.hardwareMap);
//    }
//
//    // Set pivot's motor power to 0.
//    public void shutdown(OpMode opMode) {
//        robot.pivotMotor.setPower(0);
//    }
//
//    // Moves pivot up if 'a' is held.
//    // Stalls pivot if 'b' is held.
//    // Turns pivot off otherwise.
//    public void turn(Gamepad gamepad) {
//
//        if (gamepad.a) {
//            robot.pivotMotor.setPower(1);
//        }
//        else if (gamepad.b) {
//            robot.pivotMotor.setPower(0.05);
//        }
//        else {
//            robot.pivotMotor.setPower(0);
//        }
//    }
//}
