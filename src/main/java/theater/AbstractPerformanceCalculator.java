package theater;

/**
 * N.
 */
public abstract class AbstractPerformanceCalculator {

    private final Performance performance;
    private final Play play;

    public AbstractPerformanceCalculator(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }

    public Play getPlay() {
        return play;
    }

    public Performance getPerformance() {
        return performance;
    }

    /**
     * N.
     * @param performance .
     * @param play .
     * @return .
     */
    public static AbstractPerformanceCalculator createPerformanceCalculator(Performance performance, Play play) {
        return null;
    }

    /**
     * Nothing.
     * @return nothing
     * @throws RuntimeException throws unknow type error
     */
    public int amountFor() {
        int result = 0;
        switch (play.getType()) {
            case "tragedy":
                result = Constants.PASTORAL_BASE_AMOUNT;
                if (performance.getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
                    result += Constants.TRAGEDY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience() - Constants.TRAGEDY_AUDIENCE_THRESHOLD);
                }
                break;
            case "comedy":
                result = Constants.COMEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                    result += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                            + (Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD));
                }
                result += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.getAudience();
                break;
            default:
                throw new RuntimeException(String.format("unknown type: %s", play.getType()));
        }
        return result;
    }

    /**
     * Nothing.
     * @return n
     */
    public int volumeCredits() {
        int result = Math.max(performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        // add extra credit for every five comedy attendees
        if ("comedy".equals(play.getType())) {
            result += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }
        return result;
    }
}
