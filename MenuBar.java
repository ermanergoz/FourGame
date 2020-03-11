import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar
{
    private JMenu gameMenu;

    private void createMenuItem(String name, Action action)
    {
        JMenuItem item = new JMenuItem(action);
        item.setText(name);
        gameMenu.add(item);
    }

    public MenuBar(Action start3x3GameAction,Action start5x5GameAction, Action start7x7GameAction)
    {
        gameMenu = new JMenu("Game Menu");

        createMenuItem("Start 3X3 Game", start3x3GameAction);
        createMenuItem("Start 5x5 Game", start5x5GameAction);
        createMenuItem("Start 7x7 Game", start7x7GameAction);

        add(gameMenu);
    }
}