package theater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents the data structure passed around for generating statements,
 * holding the invoice details and play information.
 */
public class StatementData {
    private final Invoice invoice;
    private final Map<String, Play> plays;
    private final List<PerformanceData> performances;

    public StatementData(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
        this.performances = new ArrayList<>();
        for (Performance performance : invoice.getPerformances()) {
            performances.add(createPerformanceData(performance));
        }
    }

    private PerformanceData createPerformanceData(Performance performance) {
        final Play play = plays.get(performance.getPlayID());
        final AbstractPerformanceCalculator calculator =
                AbstractPerformanceCalculator.createPerformanceCalculator(performance, play);

        return new PerformanceData(performance, play, calculator.amountFor(), calculator.volumeCredits());
    }

    public String getCustomer() {
        return invoice.getCustomer();
    }

    public List<PerformanceData> getPerformances() {
        return performances;
    }

    /**
     * Nothing.
     *
     * @return nothing
     */
    public int totalAmount() {
        int result = 0;
        for (PerformanceData performanceData : performances) {
            result += performanceData.getAmount();
        }
        return result;
    }

    /**
     * Nothing.
     *
     * @return nothing
     */
    public int volumeCredits() {
        int result = 0;
        for (PerformanceData performanceData : performances) {
            result += performanceData.getVolumeCredits();
        }
        return result;
    }
}
