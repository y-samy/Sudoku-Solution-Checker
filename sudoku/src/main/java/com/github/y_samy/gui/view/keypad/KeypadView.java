package com.github.y_samy.gui.view.keypad;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class KeypadView extends JPanel {
    private JButton[] numButtons;
    private JButton delBtn;
    private JButton undoBtn;
    private JButton solveBtn;

    // default no-ops
    private CellModifiedCallback onModify = (v) -> {
    };
    private UndoPressedCallback onUndo = () -> {
    };
    private SolvePressedCallback onSolve = () -> {
    };

    public KeypadView(CellModifiedCallback onModify, UndoPressedCallback onUndo, SolvePressedCallback onSolve) {
        if (onModify != null)
            this.onModify = onModify;
        if (onUndo != null)
            this.onUndo = onUndo;
        if (onSolve != null)
            this.onSolve = onSolve;
        numButtons = new JButton[9];
        initComponents();
    }

    public CellModifiedCallback getOnModify() {
        return onModify;
    }

    public void setOnModify(CellModifiedCallback onModify) {
        if (onModify == null)
            this.onModify = (v) -> {
            }; // no-op
        else
            this.onModify = onModify;
    }

    public UndoPressedCallback getOnUndo() {
        return onUndo;
    }

    public void setOnUndo(UndoPressedCallback onUndo) {
        if (onUndo == null)
            this.onUndo = () -> {
            }; // no-op
        else
            this.onUndo = onUndo;
    }

    public SolvePressedCallback getOnSolve() {
        return onSolve;
    }

    public void setOnSolve(SolvePressedCallback onSolve) {
        if (onSolve == null)
            this.onSolve = () -> {
            }; // no-op
        else
            this.onSolve = onSolve;
    }

    public KeypadView() {
        this(null, null, null);
    }

    public void setEnabled(int numBtn, boolean enabled) {
        numButtons[numBtn - 1].setEnabled(enabled);
    }

    public void setEraseEnabled(boolean enabled) {
        delBtn.setEnabled(enabled);
    }

    public void setUndoEnabled(boolean enabled) {
        undoBtn.setEnabled(enabled);
    }

    public void setSolveEnabled(boolean enabled) {
        solveBtn.setEnabled(enabled);
    }

    private void initComponents() {
        setLayout(new GridLayout());
        for (int i = 1; i <= 9; i++) {
            final int val = i;
            var btn = new JButton(String.valueOf(i));
            btn.addActionListener((evt) -> {
                getOnModify().callback(val);
            });
            add(btn);
            numButtons[i - 1] = btn;
            btn.setFont(btn.getFont().deriveFont((float) 16));
        }
        var delImg = new ImageIcon(getClass().getResource("/icons/del.png")).getImage();
        var delIcon = new ImageIcon(delImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        delBtn = new JButton(delIcon);
        delBtn.addActionListener((evt) -> {
            getOnModify().callback(0);
        });
        add(delBtn);

        var undoImg = new ImageIcon(getClass().getResource("/icons/undo.png")).getImage();
        var undoIcon = new ImageIcon(undoImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        undoBtn = new JButton(undoIcon);
        undoBtn.addActionListener((evt) -> {
            getOnUndo().callback();
        });
        add(undoBtn);

        var redoImg = new ImageIcon(getClass().getResource("/icons/solve.png")).getImage();
        var redoIcon = new ImageIcon(redoImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        solveBtn = new JButton(redoIcon);
        solveBtn.addActionListener((evt) -> {
            getOnSolve().callback();
        });
        add(solveBtn);
    }
}
