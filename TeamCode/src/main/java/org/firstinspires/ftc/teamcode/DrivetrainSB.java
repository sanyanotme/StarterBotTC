package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@Config
@TeleOp
public class DrivetrainSB extends LinearOpMode {

    DrivetrainCL HM = new DrivetrainCL(); // Drivetrain HardwareMap(names)
    DrivetrainCL DT = new DrivetrainCL(); // Drivetrain Motor powers(movement)
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
    FtcDashboard test = FtcDashboard.getInstance(); // PID
    Telemetry dashboardTelemetry = test.getTelemetry();
    public static double p = 0; // PID
    public static double i = 0; // PID
    public static double d = 0; // PID
    public static int target = 0; // PID
    PIDController controller; // PID

    @Override
    public void runOpMode() throws InterruptedException {
        
        // motor mapping
        HM.HardwareMap();
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // servo mapping
        
        servoLeft = hardwareMap.get(Servo.class, "leftServo");
        servoRight = hardwareMap.get(Servo.class, "rightServo");
        angleAdjust = hardwareMap.get(Servo.class, "angleAdjust");

        // PID

        telemetry = new MultipleTelemetry(telemetry, test.getTelemetry());
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(
                telemetry,
                FtcDashboard.getInstance().getTelemetry()
        );
        int position = shooter.getCurrentPosition();
        double power = controller.update(target, position);

        waitForStart();

        servoRight.setDirection(Servo.Direction.REVERSE);
        servoLeft.setDirection(Servo.Direction.REVERSE);
        servoRight.setPosition(rightServoPosition);
        servoLeft.setPosition(leftServoPosition);
        DT.frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        DT.backRight.setDirection(DcMotorEx.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {


            // Input requests
            rightServoPosition = servoRight.getPosition();
            leftServoPosition = servoLeft.getPosition();
            shooterAngle = angleAdjust.getPosition();
            
            // Mechanum Drivetrain

            DT.MechanumDriveTrain();

            // Shooter Motor Toggle

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

            // PID

            shooter.setPower(power);

            // Telemetry
            
            telemetry.addData("ShooterAngle", shooterAngle);
            telemetry.addData("position", position);
            telemetry.addData("target", target);
            telemetry.addData("power", power);
            telemetry.addData("error", target - position);
            telemetry.update();
        }
    }
}