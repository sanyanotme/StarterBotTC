package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class PIDSB extends LinearOpMode {

    ShooterSB ST = new ShooterSB();

    FtcDashboard test = FtcDashboard.getInstance(); // PID

    public static double p = 0; // PID

    public static double i = 0; // PID

    public static double d = 0; // PID

    public static int target = 0; // PID

    PIDController controller; // PID

    int position = ST.shooter.getCurrentPosition();

    double power = controller.update(target, position);

    public void PIDTelemetry(){
        telemetry = new MultipleTelemetry(telemetry, test.getTelemetry());
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(
                telemetry,
                FtcDashboard.getInstance().getTelemetry()
        );
    }

    public void PIDShooterP(){
        ST.shooter.setPower(power);

    }
    @Override
    public void runOpMode() throws InterruptedException {

    }
}
