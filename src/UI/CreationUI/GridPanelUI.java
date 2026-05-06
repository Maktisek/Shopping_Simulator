package UI.CreationUI;

import javax.swing.*;
import java.awt.*;

public class GridPanelUI extends JPanel {

    private JPanel grid;
    private final int cols;
    private final int gridWidth;

    public GridPanelUI(int cols, int widthOfBox){
        super();
        this.cols = cols;
        this.gridWidth = (cols * widthOfBox) + ((cols - 1) * 20);
        initialize();
    }

    private void initialize(){
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        initializeGrid();
    }

    private void initializeGrid(){
        this.grid = new JPanel();
        this.grid.setLayout(new GridLayout(0, cols, 20, 20));
        this.grid.setOpaque(false);

        add(grid);
    }

    public void finishGrid(){
        this.grid.setPreferredSize(new Dimension(gridWidth, this.grid.getPreferredSize().height));
    }

    public JPanel getGrid() {
        return grid;
    }
}
