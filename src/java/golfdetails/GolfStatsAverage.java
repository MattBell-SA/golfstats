package golfdetails;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class GolfStatsAverage {
    double scoreTotal;
    double puttsTotal;
    double greensTotal;
    double fairwaysTotal;
    double tempTotal;
    double windTotal;

    double scoreAvg;
    double puttsAvg;
    double greensAvg;
    double fairwaysAvg;
    double tempAvg;
    double windAvg;

    BigDecimal bd;

    public double getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(double scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public double getPuttsTotal() {
        return puttsTotal;
    }

    public void setPuttsTotal(double puttsTotal) {
        this.puttsTotal = puttsTotal;
    }

    public double getGreensTotal() {
        return greensTotal;
    }

    public void setGreensTotal(double greensTotal) {
        this.greensTotal = greensTotal;
    }

    public double getFairwaysTotal() {
        return fairwaysTotal;
    }

    public void setFairwaysTotal(double fairwaysTotal) {
        this.fairwaysTotal = fairwaysTotal;
    }

    public double getScoreAvg() {
        return scoreAvg;
    }

    public void setScoreAvg(double scoreAvg) {
        this.scoreAvg = round(scoreAvg);
    }

    public double getPuttsAvg() {
        return puttsAvg;
    }

    public void setPuttsAvg(double puttsAvg) {
        this.puttsAvg = round(puttsAvg);
    }

    public double getGreensAvg() {
        return greensAvg;
    }

    public void setGreensAvg(double greensAvg) {
        this.greensAvg = round(greensAvg);
    }

    public double getFairwaysAvg() {
        return fairwaysAvg;
    }

    public void setFairwaysAvg(double fairwaysAvg) {
        this.fairwaysAvg = round(fairwaysAvg);
    }

    public double getTempTotal() {
        return tempTotal;
    }

    public void setTempTotal(double tempTotal) {
        this.tempTotal = tempTotal;
    }

    public double getWindTotal() {
        return windTotal;
    }

    public void setWindTotal(double windTotal) {
        this.windTotal = windTotal;
    }

    public double getTempAvg() {
        return tempAvg;
    }

    public void setTempAvg(double tempAvg) {
        this.tempAvg = round(tempAvg);
    }

    public double getWindAvg() {
        return windAvg;
    }

    public void setWindAvg(double windAvg) {
        this.windAvg = round(windAvg);
    }

    public double round(double value) {
        int places = 2;
        bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public void setGolfStatsAverage(List<GolfStats> golfStatsList) {
        double count = 0;
        if (golfStatsList == null) {
            return;
        }
        for (GolfStats golfStats : golfStatsList) {
            // calculate the average
            setScoreTotal(getScoreTotal() + golfStats.getStatsScore());
            setPuttsTotal(getPuttsTotal() + golfStats.getStatsPutts());
            setGreensTotal(getGreensTotal() + golfStats.getStatsGreens());
            setFairwaysTotal(getFairwaysTotal() + golfStats.getStatsFairways());
            setTempTotal(getTempTotal() + golfStats.getStatsTemp());
            setWindTotal(getWindTotal() + golfStats.getStatsWind());
            count++;
        }

        if (count > 0) {
            setScoreAvg(getScoreTotal() / count);
            setPuttsAvg(getPuttsTotal() / count);
            setGreensAvg(getGreensTotal() / count);
            setFairwaysAvg(getFairwaysTotal() / count);
            setTempAvg(getTempTotal() / count);
            setWindAvg(getWindTotal() / count);
        }
    }

}
