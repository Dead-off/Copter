package sensors;

import util.Observable;
import util.Observer;

import java.util.ArrayList;
import java.util.List;

public class SensorThread<T> implements Observable<T> {

    private final List<Observer<T>> observers = new ArrayList<>();
    private final static int READ_DATA_TIME_INTERVAL = 40;
    private final Thread thread;

    public SensorThread(final Sensor<T> sensor) {
        this.thread = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(READ_DATA_TIME_INTERVAL);
                } catch (InterruptedException e) {
                    break;
                }
                notifyObservers(sensor.getData());
            }
        });
    }

    @Override
    public void addObserver(Observer<T> observer) {

    }

    @Override
    public void removeObserver(Observer<T> observer) {

    }

    @Override
    public void notifyObservers(T data) {
        observers.forEach(it -> it.notify(data));
    }

    public void startReading() {
        thread.start();
    }

    public void stopReading() {
        thread.interrupt();
    }
}
