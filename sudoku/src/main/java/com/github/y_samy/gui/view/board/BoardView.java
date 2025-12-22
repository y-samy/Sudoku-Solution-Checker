package com.github.y_samy.gui.view.board;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class BoardView extends JPanel {
    private JSeparator vSep1, vSep2, hSep1, hSep2;
    private ArrayList<JToggleButton> cellList;
    private int CELL_INNER_PADDING = 24;
    private int BOX_INSET = 4;
    private Insets CELL_INSETS = new Insets(1, 1, 1, 1);
    private CellSelectedCallback selectionCallback = () -> {
    }; // default no-op

    public BoardView(CellSelectedCallback selectionCallback) {
        if (selectionCallback != null)
            this.selectionCallback = selectionCallback;
        initComponents();
    }

    public BoardView() {
        this(null);
    }

    public void setSelectionCallback(CellSelectedCallback selectionCallback) {
        this.selectionCallback = selectionCallback;
    }

    public void setCellValue(int row, int column, int value) {
        if (value == 0)
            getCell(row, column).setText(" ");
        else
            getCell(row, column).setText(String.valueOf(value));
    }

    public void setSelectedCellValue(int value) {
        if (value == 0)
            getSelectedCell().setText(" ");
        else
            getSelectedCell().setText(String.valueOf(value));
    }

    public int getCellValue(int row, int column) {
        var valueStr = getCell(row, column).getText();
        if (" ".equals(valueStr))
            return 0;
        return Integer.parseInt(valueStr);
    }

    private boolean isCellSelectable(JToggleButton cell) {
        return cell.isContentAreaFilled();
    }

    private JToggleButton getCell(int row, int column) {
        return cellList.get(row * 9 + column);
    }

    public void setCellSelectable(int row, int column, boolean selectable) {
        var cell = getCell(row, column);
        cell.setContentAreaFilled(selectable);
        if (cell.isSelected() && !selectable) {
            cell.setSelected(false);
            selectionCallback.notifyUpdated();
        }
    }

    public JToggleButton getSelectedCell() {
        for (var cell : cellList)
            if (cell.isSelected())
                return cell;
        return null;
    }

    public int getSelectedRow() {
        var cell = getSelectedCell();
        if (cell == null)
            return -1;
        var cellIdx = cellList.indexOf(cell);
        var row = cellIdx / 9;
        return row;
    }

    public int getSelectedColumn() {
        var cell = getSelectedCell();
        if (cell == null)
            return -1;
        var cellIdx = cellList.indexOf(cell);
        var column = cellIdx % 9;
        return column;
    }

    public void clearSelection() {
        for (var cell : cellList) {
            cell.setSelected(false);
        }
    }

    public void markValid(int row, int column) {
        getCell(row, column).setForeground(new Color(255, 255, 255));
    }

    public void markInvalid(int row, int column) {
        getCell(row, column).setForeground(new Color(255, 0, 0));
    }

    private void cellActionPerformed(java.awt.event.ActionEvent evt) {
        var cell = (JToggleButton) evt.getSource();
        if (!isCellSelectable(cell)) {
            // ItemEventListener will have already changed it, must revert!!
            cell.setSelected(!cell.isSelected());
            return;
        }
        var newlySelected = cell.isSelected();
        if (newlySelected) {
            clearSelection();
            cell.setSelected(true);
        }
        selectionCallback.notifyUpdated();
    }

    public void showDeadlockPopup() {
        JOptionPane.showMessageDialog(null, "Pre-solution state is prospectively invalid",
                "Invalid State - Can Not Solve",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showSolvedPopup() {
        JOptionPane.showMessageDialog(null, "The game has been solved. Returning to main menu.",
                "Solved!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void initComponents() {
        setLayout(new java.awt.GridBagLayout());
        cellList = new ArrayList<>(81);
        for (int i = 0; i < 81; i++) {
            var cell = new JToggleButton();
            cell.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            cell.setFont(cell.getFont().deriveFont(cell.getFont().getStyle() | java.awt.Font.BOLD,
                    cell.getFont().getSize() + 7));
            var constraints = new GridBagConstraints();
            constraints.gridx = i % 9;
            constraints.gridx += constraints.gridx / 3;
            constraints.gridy = i / 9;
            constraints.gridy += constraints.gridy / 3;
            constraints.ipadx = CELL_INNER_PADDING;
            constraints.ipady = CELL_INNER_PADDING;
            constraints.insets = (Insets) CELL_INSETS.clone();
            add(cell, constraints);
            cellList.add(cell);
            cell.setText(" ");
            for (var l : cell.getActionListeners())
                cell.removeActionListener(l);
            cell.addActionListener(this::cellActionPerformed);
        }

        hSep1 = new JSeparator();
        hSep1.setBorder(javax.swing.BorderFactory
                .createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.foreground"), 10));
        var constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 11;
        constraints.ipady = 2;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        add(hSep1, constraints);

        hSep2 = new JSeparator();
        hSep2.setBorder(javax.swing.BorderFactory
                .createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.foreground"), 10));
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 11;
        constraints.ipady = 2;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        add(hSep2, constraints);

        vSep1 = new JSeparator();
        vSep1.setOrientation(SwingConstants.VERTICAL);
        vSep1.setBorder(javax.swing.BorderFactory
                .createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.foreground"), 10));
        constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridheight = 11;
        constraints.ipadx = 2;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        add(vSep1, constraints);

        vSep2 = new JSeparator();
        vSep2.setBorder(javax.swing.BorderFactory
                .createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.foreground"), 10));
        vSep2.setOrientation(SwingConstants.VERTICAL);
        constraints = new GridBagConstraints();
        constraints.gridx = 7;
        constraints.gridy = 0;
        constraints.gridheight = 11;
        constraints.ipadx = 2;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        add(vSep2, constraints);

        this.setBorder(javax.swing.BorderFactory
            .createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.foreground"), 10));
    }

}
