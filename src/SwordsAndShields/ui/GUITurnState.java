package SwordsAndShields.ui;

public enum GUITurnState {
    Create {
        @Override
        public GUITurnState nextMajorState() {
            return Move_Rotate;
        }
    },
    Create_Rotate {
        @Override
        public GUITurnState nextMajorState() {
            return Move_Rotate;
        }
    },
    Move_Rotate {
        @Override
        public GUITurnState nextMajorState() {
            return Create;
        }
    },
    Move {
        @Override
        public GUITurnState nextMajorState() {
            return Create;
        }
    },
    Rotate {
        @Override
        public GUITurnState nextMajorState() {
            return Create;
        }
    },
    Reactions {
        @Override
        public GUITurnState nextMajorState() {
            return Move_Rotate;
        }
    },
    GameOver {
        @Override
        public GUITurnState nextMajorState() {
            return null;
        }
    };

    public abstract GUITurnState nextMajorState();
}
