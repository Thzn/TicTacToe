import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TicTacToe{

    //Static array of all tictactoe objects to reset.
    static ArrayList<TicTacToe> all = new ArrayList<>();
    //board saves all the moves that played by players.
    static int board[][] = new int[3][3];
    //In a given instance of the game if there is a winner.
    static Boolean winner = false;


    //Buttons for tictacttoe grid.
    JButton button;
    int buttonId;
    //Store weather the button is clicked or not.
    boolean state = false;

    private TicTacToe(int buttonId) {
        button = new JButton();
        this.buttonId = buttonId;
        this.button.addActionListener(new MyEvent(this.buttonId));
        this.button.setBackground(new Color(96,96,96));
        this.button.setPreferredSize(new Dimension(80, 80));
    }

    //add the player move to board.
    private   static void addtoBoard(int val, int i, int j) {
        board[i][j] = val;
    }

    //check the board is filled. if so then reset all.
    private Boolean boardFilled() {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                if (board[i][k] > 0) {
                    count++;
                }
            }
        }
        return count == 9;
    }
    //Generate empty id's for player 2's move.
    private String emptyIds() {
        String s = "-1";
        int count = 0;
        for (int i = 0; i < 3 && s.contains("-1"); i++) {
            for (int k = 0; k < 3 && s.contains("-1"); k++) {
                if (board[i][k] == 0 && s.contains("-1")) {
                    s = String.valueOf(count);
                }
                count++;
            }
        }
        return s;
    }

    //Winner of the game.
    public static int win() {
        int win=-1;
        for (int i = 0; i < 3; i++) {
            if ((board[i][0]*board[i][1]*board[i][2] == 1) && (board[i][0]+board[i][1]+board[i][2] == 3)){
                win=1;
            }
            if ((board[i][0]*board[i][1]*board[i][2] == 8) && (board[i][0]+board[i][1]+board[i][2] == 6)) {
                win=2;
            }
        }
        for (int i = 0; i < 3; i++) {
            if ((board[0][i]*board[1][i]*board[2][i] == 1) && (board[0][i]+board[1][i]+board[2][i]==3)){
                win=1;
            }
            if ((board[0][i]*board[1][i]*board[2][i] == 8) && (board[0][i]+board[1][i]+board[2][i]==6)){
                win=2;
            }
        }
        if ((board[0][0] == board[1][1]) && (board[1][1] == board[2][2]) && board[1][1] >= 1) {
            win= board[0][0];
        }else if ((board[0][2] == board[1][1]) && (board[1][1] == board[2][0]) && board[1][1] >= 1) {
            win= board[1][1];
        }

        return win;
    }

    class MyEvent implements ActionListener {
        int eventId;
        private MyEvent(int eventId) {
            this.eventId = eventId;
        }

        public void actionPerformed(ActionEvent e) {

            if (eventId == 10) {
                  resetAll();  //reset button is clicked reset all.
            } else if (board[eventId / 3][eventId % 3] == 0 && !winner) {
            //if the move is valied and  no winners yet then play
                    for (TicTacToe ticTacToe : all) {
                        if (ticTacToe.buttonId == eventId && !ticTacToe.state && !boardFilled()) {
                            //allocate a button for a player with properties.
                            setButton(ticTacToe, 1, "X", eventId, new Color(0, 76, 153));
                        }
                    }
            //After player1's move check he is win !!
                if (win() > 0) {
                    winner = true;
                    JOptionPane.showMessageDialog(null, "Player " + String.valueOf(win()) + " wins..!!");
                    resetAll();
                } else if (boardFilled()) {  //payer 1 have 5 moves so he is the only one who can fill the board.
                    JOptionPane.showMessageDialog(null, "Game over");
                    resetAll();
                } else {  //if the situation is ok to player 2 then generate a move for him.
                    for (TicTacToe ticTacToe : all) {
                        if (ticTacToe.buttonId == Integer.parseInt(ticTacToe.emptyIds())) {
                            setButton(ticTacToe, 2, "0", Integer.parseInt(ticTacToe.emptyIds()), new Color(0, 153, 76));
                            break;
                        }
                    }
                }
            //After player2's move check thw winner  !!
                if (win() > 0) {
                    winner = true;
                    JOptionPane.showMessageDialog(null, "Player " + String.valueOf(win()) + " wins..!!");
                    resetAll();
                }

            }

        }

        //set buttons is used to set button with different features. value. it's object, event, color
        //value is 1 for player 1 and 2 for player 2. Then the board is scanned according to the numbers.
        //Different colors for players.
        private void setButton(TicTacToe ticTacToe, int value, String text, int eventId, Color color){
            ticTacToe.state = true;
            ticTacToe.button.setFont(new Font("SansSerif", Font.BOLD, 60));
            ticTacToe.button.setText(text);
            ticTacToe.button.setBackground(color);
            ticTacToe.addtoBoard(value,eventId/ 3, eventId % 3);
        }

        //reset all if someone wins or board is filled or reset button clicked.
        //delete the text, state is false for not clicked,add default color.
        //new board create. and no winners.
        private void resetAll() {
            for (TicTacToe t : all) {
                t.button.setText("");
                t.state = false;
                t.button.setBackground(new Color(96,96,96));
            }
            board = new int[3][3];
            winner = false;
        }
    }
    public static void main(String args[]) {

        //jpanel contains the button grid. 3*3
        JPanel jPanel=new JPanel();
        jPanel.setLayout(new GridLayout(3,3));
        for (int i=0; i<9; i++){
            TicTacToe ticTacToe =new TicTacToe(i);
            jPanel.add(ticTacToe.button);
            all.add(ticTacToe);
        }

        //reset panel holds the reset button.
        JPanel reset=new JPanel();
        reset.setLayout(new GridLayout(1,1));
        //add some properties to button.
        TicTacToe buttonReset=new TicTacToe(10);
        buttonReset.button.setBackground(new Color(192,192,192));
        buttonReset.button.setText("RESET");
        buttonReset.button.setPreferredSize(new Dimension(20,40));
        reset.add(buttonReset.button);

        //Create a JFrame.
        JFrame frame=new JFrame("TIC-TAC-TOE");
        //place all panels in the plane.
        frame.add(jPanel,BorderLayout.NORTH);
        frame.add(new JSeparator(),BorderLayout.CENTER);
        frame.add(reset,BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}