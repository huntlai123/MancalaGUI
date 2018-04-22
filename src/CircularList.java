import java.util.ArrayList;

/**
 * This class updates ArrayList with a new functionality allowing it
 * to loop through the elements
 */
public class CircularList<E> extends ArrayList<E>{
    
    /**
     * Allows the elements to be accessed sequentially
     * @param index element to be accessed
     * @return true element that is being accessed
     */
    @Override
    public E get(int index)
    {
        return super.get(index % size());
    }
}
