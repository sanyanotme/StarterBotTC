package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class DrivetrainCL extends LinearOpMode {

    DcMotorEx frontLeft; // Drivetrain Motor
    DcMotorEx frontRight; // Drivetrain Motor
    DcMotorEx backLeft; // Drivetrain Motor
    DcMotorEx backRight; // Drivetrain Motor
    public void HardwareMap() {

        frontLeft = hardwareMap.get(DcMotorEx.class, "leftUp");
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        frontRight = hardwareMap.get(DcMotorEx.class, "rightUp");
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        backLeft = hardwareMap.get(DcMotorEx.class, "leftDown");
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        backRight = hardwareMap.get(DcMotorEx.class, "rightDown");
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
    }
    public void MechanumDriveTrain(){
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
    }

    public void runOpMode() throws InterruptedException {

    }
}