package commands;

public interface Command {
    boolean isLegal();
    void execute();
}
