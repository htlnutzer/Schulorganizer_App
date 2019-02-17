package com.jodli.organizr.model;

import java.io.Serializable;

public abstract class Content implements Serializable {

    public abstract String getJSON();

    public abstract void setJSON();

}
