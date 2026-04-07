package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


@Config
@TeleOp
public class DrivetrainSB extends LinearOpMode {

    DrivetrainCL DT = new DrivetrainCL(); // Drivetrain Object

    ServoSB SV = new ServoSB(); // Servo Object

    TelemetrySB TM = new TelemetrySB(); // Telemetry Object

    ShooterSB ST = new ShooterSB(); // Shooter Object

    PIDSB PT = new PIDSB(); // PID Object




    @Override
    public void runOpMode() throws InterruptedException {
        
        // motor mapping

        DT.HardwareMap();
        ST.ShooterMode();

        // servo mapping

        SV.ServoMapping();

        // PID

        PT.PIDTelemetry();

        waitForStart();

        // Motor and Servo Directions

        SV.ServoDirection();

        DT.MotorDirection();

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {


            // Input requests
            SV.ServoInput();
            
            // Mechanum Drivetrain

            DT.MechanumDriveTrain();

            // Shooter Motor Toggle

            ST.ShooterToggle();

            // NON-FUNCTIONAL // Shooter Angle Adjuster

           SV.ServoAngelAdjust();

            // Transfer Servo toggle

            SV.ServoToggle();

            // PID

            PT.PIDShooterP();

            // Telemetry

            TM.GeneralTelemetry();

        }
    }
}