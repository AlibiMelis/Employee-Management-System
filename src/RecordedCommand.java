package src;
import java.util.ArrayList;

public abstract class RecordedCommand implements Command {
    private static ArrayList<RecordedCommand> undoList = new ArrayList<RecordedCommand>();
    private static ArrayList<RecordedCommand> redoList = new ArrayList<RecordedCommand>();

    public void undoMe() {};
    public void redoMe() {};

    protected void addUndoCommand(RecordedCommand cmd) {
        undoList.add(cmd);
    }
    protected void addRedoCommand(RecordedCommand cmd) {
        redoList.add(cmd);
    }
    protected void clearRedoList() {
        redoList.clear();
    }

    public static void undoOneCommand() {
        if (undoList.isEmpty()) {
            System.out.println("Nothing to undo.");
        } else {
            undoList.remove(undoList.size() - 1).undoMe();
        }
    }

    public static void redoOneCommand() {
        if (redoList.isEmpty()) {
            System.out.println("Nothing to redo.");
        } else {
            redoList.remove(redoList.size() - 1).redoMe();
        }
    }
}
