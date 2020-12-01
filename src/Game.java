import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Game extends JFrame {

    private static JFrame gameFrame = new JFrame();
    private JButton[]buttons = new JButton[16];
    private JPanel panel = new JPanel();
    private int[] initialBoard = new int[16];

    public static void main(String[] args) {
        Game game = new Game();
        gameFrame = new JFrame("15 Game");
        gameFrame.setLocation(400, 200);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(600, 600 );
        game.startGame();
    }

    private void startGame(){
        makeSolvableBoard();
        //initial setting of buttons
        for (int i = 0; i < 16; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font. PLAIN, 40));
            //buttons[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setForeground(Color.BLACK);
            int finalI = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //JButton buttonPressed = (JButton) e.getSource();
                    //int pressedLocation = getLocation(buttonPressed.getText());
                    moves(finalI);
                }
            });
            panel.add(buttons[i]);
        }
        setButtons();

        //create and add new game button to panel
        JButton newGameButton = new JButton("new game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeSolvableBoard();
                setButtons();
            }
        });
        panel.add(newGameButton);


        //initialize frame
        gameFrame = new JFrame("15 Game");
        gameFrame.setLocation(400, 200);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.pack();
        gameFrame.setSize(600, 600 );

        //panel
        panel.setLayout(new GridLayout(5, 4));
        panel.setBackground(Color.GRAY);




        //content pane
        Container content = gameFrame.getContentPane();
        content.add(panel, BorderLayout.CENTER);
        content.setBackground(Color.GRAY);

        gameFrame.setVisible(true);

    }

    //stackoverflow algorithm
    public boolean isSolvable(int[] puzzle) {
        int parity = 0;
        int gridWidth = (int) Math.sqrt(puzzle.length);
        int row = 0; // the current row we are on
        int blankRow = 0; // the row with the blank tile

        for (int i = 0; i < puzzle.length; i++) {
            if (i % gridWidth == 0) { // advance to next row
                row++;
            }
            if (puzzle[i] == 0) { // the blank tile
                blankRow = row; // save the row on which encountered
                continue;
            }
            for (int j = i + 1; j < puzzle.length; j++) {
                if (puzzle[i] > puzzle[j] && puzzle[j] != 0) {
                    parity++;
                }
            }
        }
        if (gridWidth % 2 == 0) { // even grid
            if (blankRow % 2 == 0) { // blank on odd row; counting from bottom
                return parity % 2 == 0;
            } else { // blank on even row; counting from bottom
                return parity % 2 != 0;
            }
        } else { // odd grid
            return parity % 2 == 0;
        }
    }

    private void moves(int mx){
        String temp;
        try {
            if (mx % 4 != 3 && buttons[mx + 1].getText().equals("")) {
                temp = buttons[mx].getText();
                buttons[mx].setText("");
                buttons[mx + 1].setText(temp);
                //System.out.println("left");
            }else if (mx % 4 != 0 && buttons[mx - 1].getText().equals("")) {
                temp = buttons[mx].getText();
                buttons[mx].setText("");
                buttons[mx - 1].setText(temp);
                //System.out.println("right");
            }else if (mx < 12 && buttons[mx + 4].getText().equals("")) {
                temp = buttons[mx].getText();
                buttons[mx].setText("");
                buttons[mx + 4].setText(temp);
                //System.out.println("up");
            }else if (mx > 3 && buttons[mx - 4].getText().equals("")) {
                temp = buttons[mx].getText();
                buttons[mx].setText("");
                buttons[mx - 4].setText(temp);
                //System.out.println("down");
            }else {
                //System.out.println("invalidmove");
            }
            if (isWin()){
                JOptionPane.showMessageDialog(null, "you won, press ok to start a new game");
                makeSolvableBoard();
                setButtons();
            }

        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("oob");
        }
    }

    public int getLocation(String lx) {
        for (int i = 0; i < 16; i++) {
            if (buttons[i].getText().equals(lx)) {
                return (i);
            }
        }
        return(1000);
    }

    private boolean isWin(){
        int correctPlaces = 15;
        for (int i = 0; i < correctPlaces; i++) {
            if(!buttons[i].getText().equals(String.valueOf(i+1))) {
                //System.out.println(buttons[i].getText());
                return false;
            }
        }
        return true;
    }

    //turns board into a solvable
    private void makeSolvableBoard(){
        boolean isSolvable = false;
        while (!isSolvable) {
            ArrayList<Integer> randomNumbers = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                randomNumbers.add(i);
            }
            Collections.shuffle(randomNumbers);
            for (int i = 0; i < 16; i++) {
                initialBoard[i] = randomNumbers.get(i);

            }
            isSolvable = isSolvable(initialBoard);
        }
    }

    private void setButtons(){
        for (int i = 0; i < 16; i++) {
            if (initialBoard[i]==0){
                buttons[i].setText("");
            }else {
                buttons[i].setText(String.valueOf(initialBoard[i]));
            }
        }

    }

}

