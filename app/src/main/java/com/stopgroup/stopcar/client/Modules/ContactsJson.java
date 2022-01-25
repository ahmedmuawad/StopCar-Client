package com.stopgroup.stopcar.client.Modules;

import java.util.ArrayList;
import java.util.List;

public class ContactsJson {
    public List<ResultContactsModel> result = new ArrayList<>();

    public static class ResultContactsModel {
        public String name;
        public String mobile;
        public String imageData;

    }
}

