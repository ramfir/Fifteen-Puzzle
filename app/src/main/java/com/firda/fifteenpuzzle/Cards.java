package com.firda.fifteenpuzzle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cards implements Serializable {
    private List<Integer> cards;

    public Cards() {
        cards = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            cards.add(i+1);
        }
    }

    public Integer get(int i) {
        return cards.get(i);
    }

    public boolean shuffleCards(boolean hard) {
        Collections.shuffle(cards);
        int result = 0;
        for (int i = 0; i < 16; i++) {
            if (cards.get(i) != 16) {
                for (int j = 0; j < i; j++) {
                    if (cards.get(j) != 16) {
                        if (cards.get(j) > cards.get(i)) {
                            result++;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 16; i++) {
            if (cards.get(i) == 16) {
                result += 1 + i/4;
                break;
            }
        }
        if (!hard && result % 2 == 1) {
            return shuffleCards(hard);
        }
        else if (hard && result % 2 == 0) {
            return shuffleCards(hard);
        }
        return true;
    }

    public void moveCard(int index) {
        if (index + 1 < cards.size() && cards.get(index+1) == 16) {
            cards.set(index+1, cards.get(index));
            cards.set(index, 16);
        } else if (index - 1 >= 0 && cards.get(index-1).toString().equals("16")) {
            cards.set(index-1, cards.get(index));
            cards.set(index, 16);
        } else if (index + 4 < cards.size() && cards.get(index+4).toString().equals("16")) {
            cards.set(index+4, cards.get(index));
            cards.set(index, 16);
        } else if (index - 4 >= 0 && cards.get(index-4).toString().equals("16")) {
            cards.set(index-4, cards.get(index));
            cards.set(index, 16);
        }

    }
}

