///* Copyright (c) 2017 FIRST. All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without modification,
// * are permitted (subject to the limitations in the disclaimer below) provided that
// * the following conditions are met:
// *
// * Redistributions of source code must retain the above copyright notice, this list
// * of conditions and the following disclaimer.
// *
// * Redistributions in binary form must reproduce the above copyright notice, this
// * list of conditions and the following disclaimer in the documentation and/or
// * other materials provided with the distribution.
// *
// * Neither the name of FIRST nor the names of its contributors may be used to endorse or
// * promote products derived from this software without specific prior written permission.
// *
// * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
// * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
// * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
// * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package org.firstinspires.ftc.teamcode;
//
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
//
//import com.qualcomm.hardware.bosch.BNO055IMU;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.util.Range;
//
///**
// * This file provides basic Telop driving for a Pushbot robot.
// * The code is structured as an Iterative OpMode
// *
// * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
// * All device access is managed through the HardwarePushbot class.
// *
// * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
// * It raises and lowers the claw using the Gampad Y and A buttons respectively.
// * It also opens and closes the claws slowly using the left and right Bumper buttons.
// *
// * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
// * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
// */
//
//@TeleOp(name="Tank Drive", group="Pushbot")
//
//public class Teleop extends OpMode {
//
//    //private  boolean gbVisionProb=false,gbNavXProb=false;
//    /* Declare OpMode members. */
//    private robotDeclarations robot = new robotDeclarations();
//    private Pivot pivot = new Pivot();
//    private Claw claw = new Claw();
//    private Mechanum mechanumDrive = new Mechanum();
//
//    @Override
//    public void init() {
//        pivot.init(this);
//        claw.init(this);
//        mechanumDrive.init(this);
//
//        telemetry.addData("Press Start", " >");
//        telemetry.update();
//    }
//
//    @Override
//    public void stop() {
//        pivot.shutdown(this);
//        mechanumDrive.shutdown(this);
//        claw.shutdown(this);
//
//        telemetry.addData("Shutting Down", "Disabling code.");
//        telemetry.update();
//    }
//
//    /*
//     * Code to run when the op mode is first enabled goes here
//     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
//     */
//    @Override
//    public void init_loop() {
//
//    }
//
//    /*
//     * This method will be called repeatedly in a loop
//     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
//     */
//    @Override
//    public void loop() {
//        mechanumDrive.operate(this, gamepad1);
//        claw.operate(this, gamepad1);
//        pivot.operate(this, gamepad2);
//    }
//
//    private void init_hardware() {
//
//
//    }
//}