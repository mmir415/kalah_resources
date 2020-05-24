package kalah.Controller;
    public enum Player {
        ONE(1),
        TWO(2);

        private static final Player[] values = values();
        private final int number;

        Player(int number) {
            this.number = number;
        }

        public int player_number() {
            return number;
        }

        public Player nextTurnPlayer() {
            int score_size = values.length;
            int player_arrangement = this.ordinal() + 1;
            return values[(player_arrangement) % score_size];
        }
    }