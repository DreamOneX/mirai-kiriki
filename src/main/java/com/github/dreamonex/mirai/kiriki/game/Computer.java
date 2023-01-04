package com.github.dreamonex.mirai.kiriki.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedList;

public class Computer extends Player {
    public Computer() {
        // random id
        super((int) (Math.random() * 1000000));
        this.isComputer = true;
    }
}
