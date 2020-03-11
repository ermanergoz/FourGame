import java.awt.*;

public class GameModel
{
    private int gridSize;
    private boolean playersTurn;
    private int assignedPoints [][];
    private boolean isScored [][];
    private int score1;
    private int score2;

    public GameModel(int _gridSize, boolean _playersTurn)
    {
        this.gridSize=_gridSize;
        this.playersTurn=_playersTurn;
        this.score1=0;
        this.score2=0;

        assignedPoints = new int[_gridSize][_gridSize];

        for (int i = 0; i < _gridSize; ++i)
        {
            for (int j = 0; j < _gridSize; ++j)
            {
                assignedPoints[i][j]=0;
            }
        }

        isScored = new boolean[_gridSize][_gridSize];

        for (int i = 0; i < _gridSize; ++i)
        {
            for (int j = 0; j < _gridSize; ++j)
            {
                isScored[i][j]=false;
            }
        }
    }

    public void assignPoints(int i, int j)
    {
        if(playersTurn)
            this.assignedPoints[i][j]=1;
        else
            this.assignedPoints[i][j]=2;
    }

    public void setPlayer(boolean _player)
    {
        this.playersTurn=_player;
    }

    public void resetScore()
    {
        score1=0;
        score2=0;
    }

    public int getScore1()
    {
        return score1;
    }

    public int getScore2()
    {
        return score2;
    }

    public void neighboursToIncrease(int i, int j)
    {
        for(int row = i-1; row <= i+1; ++row)
        {
            if(row>-1 && row<gridSize)
            {
                for (int column = j - 1; column <= j + 1; ++column)
                {
                    if (column > -1 && column < gridSize)
                    {
                        increaseNeighbours(row, column);
                    }
                }
            }
        }
        playersTurn = switchTurns(playersTurn);
        keepScore();
    }

    public void increaseNeighbours(int i, int j)
    {
            int value=Integer.parseInt(GameWindow.grid[i][j].getText());
            if(value!=4)
                GameWindow.updateTextOfButton(i, j,++value);
            if(value==4 && !isScored[i][j])
            {
                buttonColors(playersTurn, i, j);
                assignPoints(i,j);
            }
    }

    public boolean isGameFinished ()
    {
        return score1 + score2 == gridSize * gridSize;
    }

    private boolean switchTurns(boolean isPlayer1)
    {
        return !isPlayer1;
    }

    private void buttonColors(boolean player, int i, int j)
    {
        if(player)
            GameWindow.grid[i][j].setBackground(Color.RED);
        else
        {
            GameWindow.grid[i][j].setBackground(Color.BLUE);
            GameWindow.grid[i][j].setForeground(Color.LIGHT_GRAY);
        }
    }

    private void keepScore ()
    {
        for (int i = 0; i < gridSize; ++i)
        {
            for (int j = 0; j < gridSize; ++j)
            {
                if(assignedPoints[i][j]==1 && !isScored[i][j])
                {
                    ++score1;
                    isScored[i][j]=true;
                }
                if(assignedPoints[i][j]==2 && !isScored[i][j])
                {
                    ++score2;
                    isScored[i][j]=true;
                }
            }
        }
    }

    public String whoWon()
    {
        String winner = "";

        if(score1>score2)
        {
            return winner="Player 1 Won! Score: "+score1;
        }
        else
            return winner="Player 2 Won! Score: "+score2;
    }

    public void resetGrid()
    {
        for (int i = 0; i < gridSize; ++i)
        {
            for (int j = 0; j < gridSize; ++j)
            {
                GameWindow.updateTextOfButton(i, j,0);
                assignedPoints [i][j]=0;
                isScored [i][j]=false;
            }
        }
    }

    public void resetColors()
    {
        for (int i = 0; i < gridSize; ++i)
        {
            for (int j = 0; j < gridSize; ++j)
            {
                GameWindow.grid[i][j].setBackground(null);
                GameWindow.grid[i][j].setForeground(null);
            }
        }
    }
}
