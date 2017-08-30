package SwordsAndShields.ui;

import SwordsAndShields.model.Game;
import SwordsAndShields.model.TurnState;
import com.sun.org.apache.regexp.internal.RE;

public enum GUITurnState {
    Create {
        @Override
        public GUITurnState nextMajorState() {
            return Create_Rotate;
        }

        @Override
        public GUITurnState previousMajorState() {
            return Create;
        }

        @Override
        public GUITurnState passState() {
            return Move_Rotate;
        }
    },
    Create_Rotate {
        @Override
        public GUITurnState nextMajorState() {
            return Move_Rotate;
        }

        @Override
        public GUITurnState previousMajorState() {
            return Create;
        }

        @Override
        public GUITurnState passState() {
            return Move_Rotate;
        }
    },
    Move_Rotate {
        @Override
        public GUITurnState nextMajorState() {
            return Move_Rotate_Selected;
        }

        @Override
        public GUITurnState previousMajorState() {
            return Move_Rotate;
        }

        @Override
        public GUITurnState passState() {
            return Create;
        }
    },
    Reactions {
        @Override
        public GUITurnState nextMajorState() {
            return Move_Rotate;
        }

        @Override
        public GUITurnState previousMajorState() {
            return Move_Rotate;
        }

        @Override
        public GUITurnState passState() {
            return Reactions;
        }
    },
    GameOver {
        @Override
        public GUITurnState nextMajorState() {
            return GameOver;
        }

        @Override
        public GUITurnState previousMajorState() {
            return GameOver;
        }

        @Override
        public GUITurnState passState() {
            return GameOver;
        }
    },
    Move_Rotate_Selected {
        @Override
        public GUITurnState nextMajorState() {
            return Move_Rotate_Selected;
        }

        @Override
        public GUITurnState previousMajorState() {
            return Move_Rotate;
        }

        @Override
        public GUITurnState passState() {
            return Create;
        }
    };

    public abstract GUITurnState nextMajorState();
    public abstract GUITurnState previousMajorState();
    public abstract GUITurnState passState();

    public static GUITurnState fromGameTurnState(TurnState s){
        switch (s){
            case CREATE:
                return Create;
            case MOVE_ROTATE:
                return Move_Rotate;
            case REACTIONS:
                return Reactions;
            case GAME_FINISHED:
                return GameOver;
        }
        return GameOver;
    }
}
