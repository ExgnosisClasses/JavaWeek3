
public class Runner {
    public static void main(String[] args) {
        Device blender = new Device();
        blender.exec();
        blender.turnOn();
        blender.exec();
        blender.turnOff();
        blender.exec();
    }
}


class Device {

    // These are the states the device the can take
    private enum deviceState{
        ON,
        OFF;
    }

    // The state objects
    private State on = null;
    private State off = null;
    private State currentState = null;

    private deviceState status = deviceState.OFF;

    // Construcotr

    Device() {
        this.on = new On();
        this.off = new Off();
        this.currentState = this.off;
        this.status = deviceState.OFF;
    }

    public void exec() {
        this.currentState.exec();
    }

    public void turnOn() {
        this.currentState = this.on;
        this.status = deviceState.ON;
    }

    public void turnOff() {
        this.currentState = this.off;
        this.status = deviceState.OFF;
    }

    public deviceState getStatus() {
        return this.status;
    }

}


interface State  {
    void exec();
}

class On implements State {

    @Override
    public void exec() {
        System.out.println("The device is on");
    }

}

class Off implements State {
    @Override
    public void exec() {
        System.out.println("The device is off");
    }

}