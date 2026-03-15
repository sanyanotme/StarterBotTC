package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class ServoSB extends LinearOpMode {

    double leftServoPosition = 0; // current position of left transfer servo 0 - 1
    double rightServoPosition = 1; // current position of right transfer servo 0 - 1
    double transferServoMovement = 0.3; // how much the transfer servos move if the button is held until the end0 - 1
    double shooterAngle; // angle of the ramp relative to the servo 0 - 1
    Servo servoRight; // transfer servo
    Servo servoLeft; // transfer servo
    Servo angleAdjust; // NON-FUNCTIONAL // servo adjusting the slope of the ramp 0 - 1

    // Methods
    public void ServoMapping (){
        servoLeft = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "leftServo");
        servoRight = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "rightServo");
        angleAdjust = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "angleAdjust");
    }
    public void ServoDirection(){
        servoRight.setDirection(Servo.Direction.REVERSE);
        servoLeft.setDirection(Servo.Direction.REVERSE);
        servoRight.setPosition(rightServoPosition);
        servoLeft.setPosition(leftServoPosition);
    }

    public void ServoInput(){
        rightServoPosition = servoRight.getPosition();
        leftServoPosition = servoLeft.getPosition();
        shooterAngle = angleAdjust.getPosition();
    }
    public void ServoAngelAdjust(){
        if (gamepad1.rightBumperWasPressed()) {
            shooterAngle = shooterAngle - 0.1;
            angleAdjust.setPosition(shooterAngle);
        }
        if (gamepad1.leftBumperWasPressed()) {
            shooterAngle = shooterAngle + 0.1;
            angleAdjust.setPosition(shooterAngle);
        }
    }
    public void ServoToggle(){
        if (gamepad1.triangleWasPressed()) {
            servoRight.setPosition(rightServoPosition - transferServoMovement);
            servoLeft.setPosition(leftServoPosition + transferServoMovement);
        }
        if (gamepad1.triangleWasReleased()) {
            servoRight.setPosition(rightServoPosition + transferServoMovement);
            servoLeft.setPosition(leftServoPosition - transferServoMovement);
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}
