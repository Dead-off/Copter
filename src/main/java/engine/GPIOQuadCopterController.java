package engine;

public class GPIOQuadCopterController implements CopterController<QuadEnginePowerContainer> {

    //todo implementation
//    пока не очень ясно, как будут работать esc. Возможно потребуется ещё что-то типа GPIOWriter
//    который будет запускаться в отдельном потоке и переключать пины с заданными интервалом
//    можно тогда добавить package-private getter , который будет отдавать текущее состояние мотора (крутится/нет)
//    и юнитом это тестировать. То есть засетили - проверили, подождали N секунд - проверили, подождали ещё - проверили

    @Override
    public void setEnginesPower(QuadEnginePowerContainer powerContainer) {
//        тут будет парсинг мощности типа 0.7 в "крутить 70мс и не крутить 30мс
    }
}
