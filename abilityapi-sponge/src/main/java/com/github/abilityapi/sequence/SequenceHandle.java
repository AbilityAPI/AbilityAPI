package com.github.abilityapi.sequence;

import java.util.ArrayList;
import java.util.List;

public class SequenceHandle {

    private final List<Sequence> executing = new ArrayList<>();

    public List<Sequence> getExecuting() {
        return executing;
    }

}
