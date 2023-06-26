package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static final double minimalAmount = 300;
    static final String dateFormat = "dd-MMM-yy";
    public static void main(String[] args) {

        double[] transfers = {6057.40, 8836.62, 9764.25, 7497.91, 4357.27, 720.01, 6172.99, 3955.23, 6139.59, 6789.37,
                              3784.11, 8038.22, 5890.01, 6968.98, 5482.94, 262.01, 4106.93, 9971.85, 7207.67, 4488.62};
        String[] transfersDates = {"13-Jul-05", "15-Oct-22", "15-Apr-22", "15-Jan-22", "13-Jul-22", "13-Jul-22", "15-Mar-23",
                                   "15-Feb-23", "15-Jan-23", "15-Jul-21", "15-Apr-23", "15-Dec-20", "15-May-22", "13-Jul-22",
                                   "15-Jun-23", "13-Jul-22", "15-Mar-22", "3-May-23", "13-Jul-22", "13-Jul-22"};

        // Create ordered list of dats in Date format
        Set<Date> dates = createSortedDateFormatSet(transfersDates);

        // Create ordered list of dates in String Format
        String[] sortedTransfersDates = createSortedDateArrayInStringFormat(dates);

        // Create map of dates and amounts
        SortedMap<String, Double> sortedMap = createMapOfDatesAndAmounts(sortedTransfersDates, transfersDates, transfers);

        // TASK 1
        calculateLastSixMonthsAverageTransfers(sortedTransfersDates, sortedMap);

        // TASK 2
        calculateMaximumTransferredMoneyInOneMonth(sortedTransfersDates, sortedMap);


        // Print each member of map
//        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }

        // Print last 6 months and summery transferred amounts
//        for (int i = sortedTransfersDates.length - 6; i < sortedTransfersDates.length; i++) {
//            System.out.println(sortedTransfersDates[i] + " : " + sortedMap.get(sortedTransfersDates[i]));
//        }

    }

    public static Set<Date> createSortedDateFormatSet(String[] stringArray) {
        Set<Date> dates = new TreeSet<>();
        for (String s : stringArray) {
            try {
                dates.add(new SimpleDateFormat(dateFormat).parse(s));
            } catch (ParseException e) {
                System.err.println("Error parsing date: " + s);
            }
        }
        return dates;
    }

    public static String[] createSortedDateArrayInStringFormat(Set datesTreeSet) {
            int counter = 0;
            String[] sortedTransfersDates = new String[datesTreeSet.size()];
            for (Object date : datesTreeSet) {
                String stringValue = new SimpleDateFormat(dateFormat).format(date);
                sortedTransfersDates[counter] = stringValue.substring(stringValue.length()-6);
                counter++;
            }
            return sortedTransfersDates;
    }

    public static SortedMap<String, Double> createMapOfDatesAndAmounts(String[] sortedTransfersDates, String[] transfersDates, double[] transfers) {
        SortedMap<String, Double> sortedMap = new TreeMap<>();
        double sum = 0;
        for (String sortedTransfersDate : sortedTransfersDates) {
            for (int j = 0; j < transfers.length; j++) {
                String transferDate = transfersDates[j].substring(transfersDates[j].length() - 6);
                if (sortedTransfersDate.equals(transferDate) && transfers[j] > minimalAmount) {
                    sum += transfers[j];
                }
            }
            if (sum != 0) {
                sortedMap.put(sortedTransfersDate.substring(sortedTransfersDate.length() - 6), sum);
                sum = 0;
            }
        }
        return sortedMap;
    }

    public static void calculateLastSixMonthsAverageTransfers(String[] sortedTransfersDates, SortedMap<String, Double> sortedMap) {
        double sixMonthsTransfer = 0;
        for (int i = sortedTransfersDates.length - 6; i < sortedTransfersDates.length; i++) {
            sixMonthsTransfer += sortedMap.get(sortedTransfersDates[i]);
        }

        System.out.println("####################### TASK 1 #######################");
        System.out.println("Last 6 months average transfers from client's card : " + Math.round(sixMonthsTransfer / 6));
    }

    public static void calculateMaximumTransferredMoneyInOneMonth(String[] sortedTransfersDates, SortedMap<String, Double> sortedMap) {
        double maximumTransferredMoney = sortedMap.get(sortedTransfersDates[0]);
        for (int i = 1; i < sortedTransfersDates.length; i++) {
            if (sortedMap.get(sortedTransfersDates[i]) > maximumTransferredMoney) {
                maximumTransferredMoney = sortedMap.get(sortedTransfersDates[i]);
            }
        }

        System.out.println("####################### TASK 2 #######################");
        System.out.println("Maximum transferred money amount in one month : " + maximumTransferredMoney);
    }
}