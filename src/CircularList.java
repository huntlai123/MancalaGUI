import java.util.ArrayList;

/**
 * This class updates ArrayList with a new functionality allowing it
 * to loop through the elements
 */
public class CircularList<E> extends ArrayList<E>{
    
    /**
     * Allows the elements to be accessed sequentially
     * @param index element to be accessed
     * @return actual element that is being accessed
     * Precondition: index should be a positive real number
     * Postcondition: an element contained in the data structure will be returned
     */
    @Override
    public E get(int index)
    {
        return super.get(index % size());
    }
}
