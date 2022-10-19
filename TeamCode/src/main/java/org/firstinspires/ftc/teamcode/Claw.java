/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

// Class for claw payload used in Teleop.
public class Claw {

    //declares new robotDeclarations object (access servos)
    robotDeclarations robot = new robotDeclarations();

    // Runs the code for the claw using the gamepad specified in the parameters.
    // Primarily used in Teleop.
    public void operate(OpMode opMode, Gamepad gamepad) {
        close(gamepad);
    }

    // Initializes the servos used in this class
    public void init(OpMode opMode) {
        robot.init(opMode.hardwareMap);
    }

    // Turns claw off.
    public void shutdown(OpMode opMode) {
        robot.leftClaw.setPosition(0);
        robot.rightClaw.setPosition(0);
    }

    boolean toggleOn = false; //checks if the claw is closed or open

    // Toggles the claw between opened and closed by pressing the 'a' button
    public void close(Gamepad gamepad) {
        if(gamepad.a && !toggleOn) {
            if(robot.leftClaw.getPosition() == 0 && robot.rightClaw.getPosition() == 0) {
                robot.leftClaw.setPosition(1);
                robot.rightClaw.setPosition(1);
            }
            else {
                robot.leftClaw.setPosition(0);
                robot.rightClaw.setPosition(0);
            }
            toggleOn = true;
        }
        else if(!gamepad.a) {
            toggleOn = false;
        }
    }
}