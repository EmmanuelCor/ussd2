package com.example.ussd;

public class SentData {
    String amount;
    String number;

    SentData(String number, String amount) {
        this.number = number;
        this.amount = amount;
    }
}
