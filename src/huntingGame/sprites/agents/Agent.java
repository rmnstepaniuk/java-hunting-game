package huntingGame.sprites.agents;

import huntingGame.Main;
import huntingGame.sprites.Player;
import huntingGame.sprites.Sprite;

import javax.swing.*;
import javax.vecmath.Vector2d;
import java.awt.*;
import java.util.ArrayList;


public class Agent extends Sprite {

    public static final float MASS = 1f;

    protected double maxSpeed;
    protected double viewRadius;

    protected boolean visible, alive;

    protected Vector2d velocity;
    protected Vector2d acceleration = new Vector2d();
    protected Vector2d steerForce = new Vector2d();
    protected Vector2d desiredVelocity;

    protected String behavior;

    public Agent(int x, int y) {
        super(x, y);
        this.desiredVelocity = new Vector2d();
        this.velocity = new Vector2d(0, 0);
        visible = true;
        alive = true;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean aliveStatus) {
        this.alive = aliveStatus;
    }



    public void seek(Vector2d targetPosition) {
        desiredVelocity.sub(targetPosition, position);
        desiredVelocity.normalize();
        desiredVelocity.scale(maxSpeed);
        steerForce.sub(desiredVelocity, velocity);
    }

    public void update(Vector2d targetPosition) {
        chooseBehavior();
        switch (this.behavior) {
            case "SEEK":
                seek(targetPosition);
                break;
            default:
                break;
        }
        acceleration.setX(steerForce.getX() / MASS);
        acceleration.setY(steerForce.getY() / MASS);
        velocity.add(acceleration);
        position.add(velocity, position);
    }

    public void checkEdges() {
        if (this.position.x < 0) {
            this.setAlive(false);
            this.setVisible(false);
        }
        if (this.position.y < 0) {
            this.setAlive(false);
            this.setVisible(false);
        }
        if (this.position.x > Main.SCREEN_WIDTH) {
            this.setAlive(false);
            this.setVisible(false);
        }
        if (this.position.y > Main.SCREEN_HEIGHT) {
            this.setAlive(false);
            this.setVisible(false);
        }
    }

    public Sprite findTarget(ArrayList<Agent> agents, Player player) {
        Agent agent;
        double distanceToTarget = Double.MAX_VALUE;
        Vector2d desired = new Vector2d();
        Sprite target = null;
        if (agents.size() == 0) return player;
        for (int i = 0; i < agents.size(); i++) {
            agent = agents.get(i);
            if (!this.equals(agent)) {
                desired.sub(agent.position, this.position);
                if (desired.length() < distanceToTarget) {
                    distanceToTarget = desired.length();
                    target = agent;
                }
            }
        }
        desired.sub(player.getPosition(), this.position);
        if (desired.length() < distanceToTarget) {
            target = player;
        }

        return target;
    }

    private void chooseBehavior() {
        this.behavior = "SEEK";
    }
}
