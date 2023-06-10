package pt.isec.pa.tinypac.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import pt.isec.pa.tinypac.gameEngine.IGameEngine;
import pt.isec.pa.tinypac.gameEngine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.fsm.GameContext;

import java.io.IOException;
import java.security.Key;

public class TinyPacLanternaUi implements IGameEngineEvolve {
    GameContext fsm;
    Screen screen;
    private boolean input=false;

    public TinyPacLanternaUi(GameContext gameContext) throws IOException {
        this.fsm=gameContext;
        TerminalSize terminalSize =new TerminalSize(29,35);
        screen=new DefaultTerminalFactory().setInitialTerminalSize(terminalSize).createScreen();
        screen.setCursorPosition(null);
        show();
    }

    private void show() throws IOException{
        char[][] maze = fsm.getEnvironmentManager().getEnvironment();
        screen.startScreen();
        for(int y=0;y<maze.length;y++){
            for(int x=0;x<maze[0].length;x++){
                TextColor bc = switch (maze[y][x]){
                    case 'x' -> TextColor.ANSI.BLUE;
                    case 'C' -> TextColor.ANSI.YELLOW_BRIGHT;
                    case 'I' -> TextColor.ANSI.BLUE_BRIGHT;
                    case 'P' -> TextColor.ANSI.MAGENTA;
                    case 'c' -> TextColor.ANSI.GREEN;
                    case 'B' -> TextColor.ANSI.RED;
                    case 'f' -> TextColor.ANSI.RED; //Spawned Fruit
                    case 'W' -> TextColor.ANSI.RED_BRIGHT;
                    case 'S' -> TextColor.ANSI.BLUE_BRIGHT;
                    case 'Y' -> TextColor.ANSI.WHITE;
                    default -> TextColor.ANSI.BLACK;
                };
                TextColor tc = switch (maze[y][x]){
                    case 'x'->TextColor.ANSI.BLUE;
                    case 'o'->TextColor.ANSI.WHITE;
                    case 'O'->TextColor.ANSI.YELLOW;
                    case 'S'->TextColor.ANSI.WHITE;
                    case 'y'->TextColor.ANSI.WHITE;
                    default -> TextColor.ANSI.BLACK;
                };
                screen.setCharacter(x,y, TextCharacter.fromCharacter(maze[y][x],tc,bc)[0]);
            }
        }
        screen.refresh();
    }

    private void getUserInput(KeyStroke key,IGameEngine gameEngine){
        try {
            if (key != null) {
                switch (key.getKeyType()) {
                    case ArrowLeft -> fsm.getEnvironmentManager().changePacmanDirection(1);
                    case ArrowUp -> fsm.getEnvironmentManager().changePacmanDirection(2);
                    case ArrowRight -> fsm.getEnvironmentManager().changePacmanDirection(3);
                    case ArrowDown -> fsm.getEnvironmentManager().changePacmanDirection(4);
                    case Escape -> {
                        gameEngine.stop();
                        screen.close();
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void WaitForUserInput(){
        try {
            System.out.println("Before");
            KeyStroke key = screen.readInput();
            System.out.println("After");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void evolve(IGameEngine gameEngine,long currentTime){
        try{
            KeyStroke key = screen.pollInput();
            show();
            switch (fsm.getState()){
                case WAITING_FOR_START -> {
                    gameEngine.pause();
                    WaitForUserInput();
                    gameEngine.resume();
                    fsm.toNormalGame();
                }
                case NORMAL_GAME -> {
                    getUserInput(key,gameEngine);
                }
                case INVINCIBLE_GAME -> {
                    System.out.println("Invincible");
                    getUserInput(key,gameEngine);
                }
                case PAUSE_GAME -> {
                    System.out.println("Pause state");
                }
                case END_GAME -> {
                    System.out.println("EndGameState");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
