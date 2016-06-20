package com.battleshippark.test.contact;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MainData {
    public long id;
    public String displayName;
    public String nameDisplayName;
    public String nameFamilyName;
    public String nameMiddleName;
    public String nameGivenName;
    public String namePrefix;
    public String nameSuffix;

    public List<Phone> phones = new ArrayList<>();

    public static class Phone {
        public String phoneNormNumber;
        public String phoneNumber;
        public String phoneType;
        public String phoneLabel;
    }
}
