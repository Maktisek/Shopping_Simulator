package UI.CreationUI.Panels;

import Utilities.Important;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents an implementation of {@link JPanel}.
 * <p>
 *     Purpose of this class is much more than just being another version of {@link JPanel}.
 *     The purpose is to hold a grid of components.
 * </p>
 * <p>
 *     The grid is being held in {@link #grid} and this grid is located inside this panel.
 * </p>
 * <p>
 *     Please insert only components of same size.
 * </p>
 * @author  Matěj Pospíšl
 * @since   1.0 - (pre-release version)
 */
public class GridPanelUI extends JPanel {

    private JPanel grid;
    private final int cols;
    private final int gridWidth;

    /**
     * {@link #gridWidth} is being initialized here, and it is calculated through parameter {@code widthOfBox}.
     * <p>
     *     The grid is set to 20 pixels between components, that is why it has to be counted too.
     * </p>
     * @param cols stands how many columns will the grid have
     * @param widthOfBox represents a width of one component. As it was mentioned earlier, make sure that all components
     *                   match this parameter.
     */
    public GridPanelUI(int cols, int widthOfBox){
        super();
        this.cols = cols;
        this.gridWidth = (cols * widthOfBox) + ((cols - 1) * Important.calculateDimension(20));
        initialize();
    }

    /**
     * Initializes this panel.
     * <p>
     *     But it is mentionable that the layout is set do {@link FlowLayout}. It is because this layout
     *     respects size of its components. Since we do not know how long will the grid be, this is the best way
     *     how to implement it.
     * </p>
     */
    private void initialize(){
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(Important.calculateDimension(20), 0, Important.calculateDimension(20), 0));

        initializeGrid();
    }

    private void initializeGrid(){
        this.grid = new JPanel();
        this.grid.setLayout(new GridLayout(0, cols, Important.calculateDimension(20), Important.calculateDimension(20)));
        this.grid.setOpaque(false);

        add(grid);
    }

    /**
     * This method "finishes" the grid.
     * <p>
     *     In reality, it calculates preferred size of the grid by extracting data from it.
     * </p>
     */
    public void finishGrid(){
        int componentCount = this.grid.getComponentCount();
        if (componentCount == 0) return;

        int rows = (int) Math.ceil((double) componentCount / cols);
        int componentHeight = this.grid.getComponent(0).getPreferredSize().height;
        int totalHeight = (rows * componentHeight) + ((rows - 1) * Important.calculateDimension(20));
        this.grid.setPreferredSize(new Dimension(gridWidth, totalHeight));
    }

    public JPanel getGrid() {
        return grid;
    }
}
