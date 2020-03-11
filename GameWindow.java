import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame
{
    private GameModel model;
    private MenuBar menuBar;
    private JLabel scoreLabel;
    private JPanel mainPanel;
    public static JButton[][] grid = new JButton[7][7];
    private WindowAdapter exit = new WindowAdapter()
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
            System.exit(0);
        }
    };

    public GameWindow()
    {
        addWindowListener(exit);

        setTitle("Four Game");   //title of the window
        setSize(800, 800);         //size of the window

        this.mainPanel = new JPanel();

        menuBar = new MenuBar(start3x3GameAction, start5x5GameAction, start7x7GameAction);

        setJMenuBar(menuBar);
        start3x3GameAction.setEnabled(true);
        start5x5GameAction.setEnabled(true);
        start7x7GameAction.setEnabled(true);

        setVisible(true);

        scoreLabel = new JLabel();
    }

    private final Action start3x3GameAction = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            mainPanel.removeAll();

            setLayout(new BorderLayout());
            add(scoreLabel, BorderLayout.SOUTH);
            add(mainPanel, BorderLayout.CENTER);

            drawGrid(3);
            model=new GameModel(3,true);

            updateLabel();
        }
    };

    private final Action start5x5GameAction = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            mainPanel.removeAll();

            setLayout(new BorderLayout());
            add(scoreLabel, BorderLayout.SOUTH);
            add(mainPanel, BorderLayout.CENTER);

            drawGrid(5);
            model=new GameModel(5,true);

            updateLabel();
        }
    };

    private final Action start7x7GameAction = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            mainPanel.removeAll();

            setLayout(new BorderLayout());
            add(scoreLabel, BorderLayout.SOUTH);
            add(mainPanel, BorderLayout.CENTER);

            drawGrid(7);
            model=new GameModel(7,true);

            updateLabel();
        }
    };

    private void drawGrid(int size)
    {
        mainPanel.setLayout(new GridLayout(size, size, 2, 2));    //to create grid
		
        for (int i = 0; i < size; ++i)
        {
            for (int j = 0; j < size; ++j)
            {
                addButton(i, j);
            }
        }
    }

    private void addButton (int i, int j)
    {
        grid[i][j] = new JButton();
        mainPanel.add(grid[i][j]);
        updateTextOfButton(i,j,0);
        grid[i][j].addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(Integer.parseInt(grid[i][j].getText())!=4)
                    model.neighboursToIncrease(i, j);

                updateLabel();
                if(model.isGameFinished())
                    showEndGameWindow();
            }
        });
    }

    public static void updateTextOfButton(int i, int j, int buttonValue)
    {
        grid[i][j].setText(String.valueOf(buttonValue));
        grid[i][j].setFont(new Font("Arial", Font.PLAIN, 35));
    }

    private void updateLabel()
    {
        if(model==null)
            scoreLabel.setText("N/A");
        else
        {
            scoreLabel.setText("Red Player1 :    " + model.getScore1()+"        "
                    +"Blue Player 2 :   "+model.getScore2());
        }
    }

    public void showEndGameWindow()
    {
        int n = JOptionPane.showConfirmDialog(this,
                model.whoWon()+"\n"+"Click Yes to play again\nClick No to quit",
                "Approve", JOptionPane.YES_NO_OPTION);

        if (n == JOptionPane.YES_OPTION)
        {
            model.resetGrid();
            model.resetScore();
            model.resetColors();
            model.setPlayer(true);
            updateLabel();
        }
        if (n == JOptionPane.NO_OPTION)
        {
            System.exit(0);
        }
    }
}