package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import org.firstinspires.ftc.robotcore.external.Telemetry;


@TeleOp
public class TelemetrySB extends LinearOpMode {
    PIDSB PT = new PIDSB();
    ServoSB SV = new ServoSB();
    Telemetry dashboardTelemetry = PT.test.getTelemetry();

    public void GeneralTelemetry(){
        telemetry.addData("ShooterAngle", SV.shooterAngle);
        telemetry.addData("position", PT.position);
        telemetry.addData("target", PT.target);
        telemetry.addData("power", PT.power);
        telemetry.addData("error", PT.target - PT.position);
        telemetry.update();
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}
