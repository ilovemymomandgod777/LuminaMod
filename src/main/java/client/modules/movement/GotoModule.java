package client.modules.movement;

import client.modules.Module;
import client.modules.Category;

public class GotoModule extends Module {

    private double targetX, targetY, targetZ;
    private double moveSpeed = 0.3;

    public GotoModule() {
        super("Goto", Category.MOVEMENT);
        this.registerKeybind("Goto Toggle", 71); // G key
    }

    @Override
    public void onTick() {
        if (!isEnabled()) return;
        if (!isOnLifeboat()) return;

        moveToTarget();
    }

    private void moveToTarget() {
        double dx = targetX - player.posX;
        double dy = targetY - player.posY;
        double dz = targetZ - player.posZ;

        double distance = Math.sqrt(dx*dx + dy*dy + dz*dz);
        if(distance < 0.1) return;

        double vx = (dx / distance) * moveSpeed;
        double vy = (dy / distance) * moveSpeed;
        double vz = (dz / distance) * moveSpeed;

        player.motionX = vx;
        player.motionY = vy; // Integrates with Fly module if enabled
        player.motionZ = vz;
    }

    private boolean isOnLifeboat() {
        String serverIP = player.getServerIP();
        return serverIP != null && serverIP.contains("lifeboat");
    }

    public void setTarget(double x, double y, double z) {
        this.targetX = x;
        this.targetY = y;
        this.targetZ = z;
    }
}