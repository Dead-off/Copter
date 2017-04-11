package position;

import util.Observer;

import java.util.ArrayList;
import java.util.List;

public class GyroscopeSensor implements Gyroscope {

    private final List<Observer<Rotation>> observers = new ArrayList<>();

    public GyroscopeSensor() {
    }

    private void onDataReceived(Rotation data) {
        observers.forEach(it -> it.notify(data));
    }

    @Override
    public void addObserver(Observer<Rotation> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<Rotation> observer) {
        observers.remove(observer);
    }
}
