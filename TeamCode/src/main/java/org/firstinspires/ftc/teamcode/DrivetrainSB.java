package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;




@TeleOp
public class DrivetrainSB extends LinearOpMode {

    DcMotorEx frontLeft; // Drivetrain Motor
    DcMotorEx frontRight; // Drivetrain Motor
    DcMotorEx backLeft; // Drivetrain Motor
    DcMotorEx backRight; // Drivetrain Motor
    DcMotor shooter; // shooter flywheel motor
    boolean shooterToggleReversed = true; // state of shooter, true = released/updated, false = button held
    double shooterPower = 1; // power of shooter flywheel motor 0-1 
    double currentShooterPower = shooterPower; // current power of shooter flywheel motor 0 - 1
    double leftServoPosition = 0; // current position of left transfer servo 0 - 1
    double rightServoPosition = 1; // current position of right transfer servo 0 - 1
    double transferServoMovement = 0.3; // how much the transfer servos move if the button is held until the end0 - 1
    double shooterAngle; // angle of the ramp relative to the servo 0 - 1
    Servo servoRight; // transfer servo
    Servo servoLeft; // transfer servo
    Servo angleAdjust; // NON-FUNCTIONAL // servo adjusting the slope of the ramp 0 - 1

    @Override
    public void runOpMode() throws InterruptedException {
        
        // motor mapping
        
        frontLeft = hardwareMap.get(DcMotorEx.class, "leftUp");
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        frontRight = hardwareMap.get(DcMotorEx.class, "rightUp");
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        backLeft = hardwareMap.get(DcMotorEx.class, "leftDown");
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        backRight = hardwareMap.get(DcMotorEx.class, "rightDown");
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        shooter = hardwareMap.get(DcMotor.class, "shooter");

        // servo mapping
        
        servoLeft = hardwareMap.get(Servo.class, "leftServo");
        servoRight = hardwareMap.get(Servo.class, "rightServo");
        angleAdjust = hardwareMap.get(Servo.class, "angleAdjust");

        waitForStart();

        servoRight.setDirection(Servo.Direction.REVERSE);
        servoLeft.setDirection(Servo.Direction.REVERSE);
        servoRight.setPosition(rightServoPosition);
        servoLeft.setPosition(leftServoPosition);
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {

            // Input requests
            rightServoPosition = servoRight.getPosition();
            leftServoPosition = servoLeft.getPosition();
            shooterAngle = angleAdjust.getPosition();
            
            // Mechanum Drivetrain

            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeft.setPower(frontLeftPower * 0.6);
            backLeft.setPower(backLeftPower * 0.6);
            frontRight.setPower(frontRightPower * 0.6);
            backRight.setPower(backRightPower * 0.6);

            // Shooter Motor Toggle

            if(gamepad1.crossWasPressed() && shooterToggleReversed){
                shooter.setPower(currentShooterPower);
                shooterToggleReversed = false;
            }
            if (gamepad1.crossWasReleased()) {
                shooterToggleReversed = true;
                if (currentShooterPower == shooterPower) {
                    currentShooterPower = 0;
                } else {
                    currentShooterPower = shooterPower;
                }
            }

            // NON-FUNCTIONAL // Shooter Angle Adjuster

            if (gamepad1.rightBumperWasPressed()) {
                shooterAngle = shooterAngle - 0.1;
                angleAdjust.setPosition(shooterAngle);
            }
            if (gamepad1.leftBumperWasPressed()) {
                shooterAngle = shooterAngle + 0.1;
                angleAdjust.setPosition(shooterAngle);
            }

            // Transfer Servo toggle

            if (gamepad1.triangleWasPressed()) {
                servoRight.setPosition(rightServoPosition - transferServoMovement);
                servoLeft.setPosition(leftServoPosition + transferServoMovement);
            }
            if (gamepad1.triangleWasReleased()) {
                servoRight.setPosition(rightServoPosition + transferServoMovement);
                servoLeft.setPosition(leftServoPosition - transferServoMovement);
            }
            
            // Telemetry
            
            telemetry.addData("ShooterAngle", shooterAngle);
            telemetry.update();

        }
    }
}