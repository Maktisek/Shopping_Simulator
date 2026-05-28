package UI.CreationUI.Utilities;

import UI.Exceptions.InvalidUILoadException;

/**
 * This interface brings a brilliant solution for update methods in all UI classes.
 * <p>
 *     If a UI class has to be updated, then it should implement this interface.
 * </p>
 * Because of this interface there are no name mismatch issues.
 * @author  Matěj Pospíšl
 * @since   1.0 - (pre-release version)
 */
public interface UpdateAble {

    /**
     * Any class implementing this method should update itself through it.
     * @throws InvalidUILoadException if there is a problem while updating (loading some pictures or setting new values)
     */
    void update() throws InvalidUILoadException;
}
