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
    private JButton redoBtn;

    private CellModifiedCallback onKeyPress = (v) -> {
    }; // default no-op

    public KeypadView(CellModifiedCallback keyPressCallback) {
        if (keyPressCallback != null)
            onKeyPress = keyPressCallback;
        numButtons = new JButton[9];
        initComponents();
    }

    public KeypadView() {
        this(null);
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

    public void setRedoEnabled(boolean enabled) {
        redoBtn.setEnabled(enabled);
    }

    public void setModificationCallback(CellModifiedCallback keypressCallback) {
        if (keypressCallback == null)
            onKeyPress = (v) -> {
            }; // no-op
        else
            onKeyPress = keypressCallback;
    }

    private void initComponents() {
        setLayout(new GridLayout());
        for (int i = 1; i <= 9; i++) {
            final int val = i;
            var btn = new JButton(String.valueOf(i));
            btn.addActionListener((evt) -> {
                onKeyPress.callback(val);
            });
            add(btn);
            numButtons[i - 1] = btn;
        }
        var delImg = new ImageIcon(getClass().getResource("/icons/del.png")).getImage();
        var delIcon = new ImageIcon(delImg.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        delBtn = new JButton(delIcon);
        delBtn.addActionListener((evt) -> {
            onKeyPress.callback(0);
        });
        add(delBtn);

        var undoImg = new ImageIcon(getClass().getResource("/icons/undo.png")).getImage();
        var undoIcon = new ImageIcon(undoImg.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        undoBtn = new JButton(undoIcon);
        undoBtn.addActionListener((evt) -> {
            onKeyPress.callback(0);
        });
        add(undoBtn);

        var redoImg = new ImageIcon(getClass().getResource("/icons/redo.png")).getImage();
        var redoIcon = new ImageIcon(redoImg.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        redoBtn = new JButton(redoIcon);
        redoBtn.addActionListener((evt) -> {
            onKeyPress.callback(0);
        });
        add(redoBtn);
    }
}
