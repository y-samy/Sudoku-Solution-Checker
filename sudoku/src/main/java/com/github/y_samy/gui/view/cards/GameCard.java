package com.github.y_samy.gui.view.cards;

public class GameCard extends javax.swing.JPanel {

        private com.github.y_samy.gui.view.board.BoardView boardView;
        private com.github.y_samy.gui.view.keypad.KeypadView keypadView;

        public GameCard() {
                initComponents();
        }

        public com.github.y_samy.gui.view.board.BoardView getBoardView() {
                return boardView;
        }

        public com.github.y_samy.gui.view.keypad.KeypadView getKeypadView() {
                return keypadView;
        }

        private void initComponents() {

                boardView = new com.github.y_samy.gui.view.board.BoardView();
                keypadView = new com.github.y_samy.gui.view.keypad.KeypadView();

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap(90, Short.MAX_VALUE)
                                                                .addComponent(boardView,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(90, Short.MAX_VALUE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(111, Short.MAX_VALUE)
                                                                .addComponent(keypadView,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(111, Short.MAX_VALUE)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap(50, Short.MAX_VALUE)
                                                                .addComponent(boardView,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(keypadView,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(50, Short.MAX_VALUE)));
        }
}
