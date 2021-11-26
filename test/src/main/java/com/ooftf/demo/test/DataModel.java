package com.ooftf.demo.test;

import org.json.JSONObject;

import java.util.ArrayList;

public class DataModel {
    String id;
    String position;
    String templateId;
    String version;
    JSONObject field;
    LayoutModel layout;

    public static class LayoutModel {
        String type;
        StyleModel style;
    }

    public static class StyleModel{
        ArrayList<String> padding;
        String vGap;
        String hGap;
    }
}
