package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class DrivetrainSB extends LinearOpMode {

    DcMotorEx frontLeft;
    DcMotorEx frontRight;
    DcMotorEx backLeft;
    DcMotorEx backRight;
    DcMotorEx shooter;
    DcMotorEx highShooter;
    boolean shooterToggle = true;
    double shooterPower = 0.71;
    double prevServoPos = 1;
    double shooterAngle;
    Servo servoRight;
    Servo servoLeft;
    Servo angleAdjust;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotorEx.class, "leftUp");
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        frontRight = hardwareMap.get(DcMotorEx.class, "rightUp");
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        backLeft = hardwareMap.get(DcMotorEx.class, "leftDown");
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        backRight = hardwareMap.get(DcMotorEx.class, "rightDown");
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        highShooter = hardwareMap.get(DcMotorEx.class, "highShooter");

        servoLeft = hardwareMap.get(Servo.class, "leftServo");
        servoRight = hardwareMap.get(Servo.class, "rightServo");
        angleAdjust = hardwareMap.get(Servo.class, "angleAdjust");

        waitForStart();

        servoRight.setDirection(Servo.Direction.REVERSE);
        servoLeft.setDirection(Servo.Direction.REVERSE);
        servoRight.setPosition(prevServoPos);
        servoLeft.setPosition(1 - prevServoPos);

        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {

            // Input requests

            shooterAngle = angleAdjust.getPosition();
            // Drivetrain

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

            if(gamepad1.crossWasPressed() && shooterToggle){
                shooter.setPower(shooterPower);
                highShooter.setPower(shooterPower);
                shooterToggle = false;
            }
            if (gamepad1.crossWasReleased()) {
                shooterToggle = true;
                if (shooterPower == 0.71) {
                    shooterPower = 0;
                } else {
                    shooterPower = 0.71;
                }
            }

            // Shooter Angle Adjuster

            if (gamepad1.rightBumperWasPressed()) {
              shooterAngle--;
              angleAdjust.setPosition(shooterAngle);
            }
            if (gamepad1.leftBumperWasPressed()) {
                shooterAngle++;
                angleAdjust.setPosition(shooterAngle);
            }

            // Servo for Shooter

            if (gamepad1.triangleWasPressed() ) {
                prevServoPos = prevServoPos - 0.4;
                servoRight.setPosition(prevServoPos);
                servoLeft.setPosition(1 - prevServoPos);

                telemetry.addData("LeftServo", servoLeft.getPosition());
                telemetry.addData("RightServo", servoRight.getPosition());

            }
            if (gamepad1.triangleWasReleased()) {
                prevServoPos = prevServoPos + 0.4;
                servoRight.setPosition(prevServoPos);
                servoLeft.setPosition(1 - prevServoPos);
            }

            telemetry.addData("ShooterAngle", shooterAngle);
            telemetry.update();

        }
    }
}