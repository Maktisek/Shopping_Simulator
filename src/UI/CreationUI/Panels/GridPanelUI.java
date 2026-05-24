package UI.CreationUI.Panels;

import Utilities.Important;

import javax.swing.*;
import java.awt.*;

public class GridPanelUI extends JPanel {

    private JPanel grid;
    private final int cols;
    private final int gridWidth;

    public GridPanelUI(int cols, int widthOfBox){
        super();
        this.cols = cols;
        this.gridWidth = (cols * widthOfBox) + ((cols - 1) * Important.calculateDimension(20));
        initialize();
    }

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
