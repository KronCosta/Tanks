package objects.pojo.abstracts;

public abstract class AbstractMovableObject extends AbstractBattleField{
    int x;
    int y;
    int direction;


    public AbstractMovableObject(int x, int y) {
        super(x, y);
    }
}
