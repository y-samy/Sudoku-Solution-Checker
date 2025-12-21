package com.github.y_samy.gui.view.cards;

public class GameCard extends javax.swing.JPanel {

    // GEN-BEGIN:variables
    private com.github.y_samy.gui.view.board.BoardView boardView;
    private com.github.y_samy.gui.view.keypad.KeypadView keypadView;
    // GEN-END:variables

    public GameCard() {
        initComponents();
    }

    public com.github.y_samy.gui.view.board.BoardView getBoardView() {
        return boardView;
    }

    public com.github.y_samy.gui.view.keypad.KeypadView getKeypadView() {
        return keypadView;
    }

    // GEN-BEGIN:initComponents
    private void initComponents() {

        boardView = new com.github.y_samy.gui.view.board.BoardView();
        keypadView = new com.github.y_samy.gui.view.keypad.KeypadView();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(88, Short.MAX_VALUE)
                                .addComponent(keypadView,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        480,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(88, Short.MAX_VALUE))
                        .addComponent(boardView, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                .createSequentialGroup()
                                .addContainerGap(34, Short.MAX_VALUE)
                                .addComponent(boardView,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        482,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(keypadView,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        51,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(16, Short.MAX_VALUE)));
    }// GEN-END:initComponents

}
