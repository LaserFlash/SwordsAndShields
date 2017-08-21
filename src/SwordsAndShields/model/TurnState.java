package SwordsAndShields.model;

/**
 * Created by brynt on 10/08/2017.
 */
public enum TurnState {
    CREATE
            {
                @Override
                public <T> T getCommand(CommandMap<T> map) { return map.creatingCommand();}

                @Override
                public TurnState next() {
                    return MOVE_ROTATE;
                }
            },
    MOVE_ROTATE
            {
                @Override
                public <T> T getCommand(CommandMap<T> map) { return map.moveRotateCommand();}

                @Override
                public TurnState next() {
                    return CREATE;
                }
            },
    REACTIONS
            {
                @Override
                public <T> T getCommand(CommandMap<T> map) {
                    return map.processReactionCommand();
                }

                @Override
                public TurnState next() {
                    return MOVE_ROTATE;
                }
            },
    GAME_FINISHED
            {
                @Override
                public <T> T getCommand(CommandMap<T> map) {
                    return map.getGameFinished();
                }

                @Override
                public TurnState next() {
                    return GAME_FINISHED;
                }
            };

    public abstract <T> T getCommand(CommandMap<T> map);
    public abstract TurnState next();


    public interface CommandMap<T>{
        T creatingCommand();
        T moveRotateCommand();
        T processReactionCommand();
        T getGameFinished();
    }
}
