package com.gw.stupid.naming.core;

import java.io.Serializable;
import java.util.List;

public class Instances implements Record, Serializable {


    private static final long serialVersionUID = -6459423214771748311L;

    private List<Instance> instanceList;

    public Instances() {

    }

    public void setInstanceList(List<Instance> list) {
        this.instanceList = list;
    }

    @Override
    public String checkSum() {
        return null;
    }

    @Override
    public void recalculateCheckSum() {

    }
}
