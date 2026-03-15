package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class ShooterSB extends LinearOpMode {


    DcMotor shooter; // shooter flywheel motor
    boolean shooterToggleReversed = true; // state of shooter, true = released/updated, false = button held
    double shooterPower = 1; // power of shooter flywheel motor 0-1
    double currentShooterPower = shooterPower; // current power of shooter flywheel motor 0 - 1


    public void ShooterToggle(){
        if(gamepad1.aWasReleased() && shooterToggleReversed){
            shooter.setPower(currentShooterPower);
            shooterToggleReversed = false;
        }
        if (gamepad1.aWasReleased()) {
            shooterToggleReversed = true;
            if (currentShooterPower == shooterPower) {
                currentShooterPower = 0;
            } else {
                currentShooterPower = shooterPower;
            }
        }
    }
    public void ShooterMode(){
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    @Override
    public void runOpMode() throws InterruptedException {

    }
}
