package com.google.ads.mediation.millennial;

import android.location.Location;
import com.google.ads.mediation.NetworkExtras;
import com.millennialmedia.android.MMRequest;

public final class MillennialAdapterExtras implements NetworkExtras {
    public static final int NOT_SET = -1;
    private int age = -1;
    private Children children = null;
    private Education education = null;
    private Ethnicity ethnicity = null;
    private Gender gender = null;
    private Integer income = null;
    private InterstitialTime interstitialTime = InterstitialTime.UNKNOWN;
    private String keywords = null;
    private Location location = null;
    private MaritalStatus maritalStatus = null;
    private Politics politics = null;
    private String postalCode = null;

    public enum Children {
        HAS_KIDS("true"),
        NO_KIDS("false");
        
        private final String description;

        private Children(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }

    public enum Education {
        HIGH_SCHOOL(MMRequest.EDUCATION_HIGH_SCHOOL),
        IN_COLLEGE(MMRequest.EDUCATION_IN_COLLEGE),
        SOME_COLLEGE(MMRequest.EDUCATION_SOME_COLLEGE),
        ASSOCIATES(MMRequest.EDUCATION_ASSOCIATES),
        BACHELORS(MMRequest.EDUCATION_BACHELORS),
        MASTERS(MMRequest.EDUCATION_MASTERS),
        DOCTORATE(MMRequest.EDUCATION_DOCTORATE),
        OTHER("other");
        
        private final String description;

        private Education(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }

    public enum Ethnicity {
        HISPANIC(MMRequest.ETHNICITY_HISPANIC),
        AFRICAN_AMERICAN("africanamerican"),
        ASIAN(MMRequest.ETHNICITY_ASIAN),
        INDIAN(MMRequest.ETHNICITY_INDIAN),
        MIDDLE_EASTERN(MMRequest.ETHNICITY_MIDDLE_EASTERN),
        NATIVE_AMERICAN(MMRequest.ETHNICITY_NATIVE_AMERICAN),
        PACIFIC_ISLANDER(MMRequest.ETHNICITY_PACIFIC_ISLANDER),
        WHITE(MMRequest.ETHNICITY_WHITE),
        OTHER("other");
        
        private final String description;

        private Ethnicity(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }

    public enum Gender {
        MALE(MMRequest.GENDER_MALE),
        FEMALE(MMRequest.GENDER_FEMALE),
        OTHER("other");
        
        private final String description;

        private Gender(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }

    public enum InterstitialTime {
        UNKNOWN,
        APP_LAUNCH,
        TRANSITION
    }

    public enum MaritalStatus {
        SINGLE(MMRequest.MARITAL_SINGLE),
        DIVORCED(MMRequest.MARITAL_DIVORCED),
        ENGAGED(MMRequest.MARITAL_ENGAGED),
        RELATIONSHIP(MMRequest.MARITAL_RELATIONSHIP),
        MARRIED(MMRequest.MARITAL_MARRIED),
        OTHER("other");
        
        private final String description;

        private MaritalStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }

    public enum Politics {
        REPUBLICAN("republican"),
        DEMOCRAT("democrat"),
        CONSERVATIVE("conservative"),
        MODERATE("moderate"),
        LIBERAL("liberal"),
        INDEPENDENT("independent"),
        OTHER("other"),
        UNKNOWN("unknown");
        
        private final String description;

        private Politics(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }

    public MillennialAdapterExtras setLocations(Location location) {
        this.location = location;
        return this;
    }

    public MillennialAdapterExtras clearLocations() {
        return setLocations(null);
    }

    public Location getLocation() {
        return this.location;
    }

    public MillennialAdapterExtras setKeywords(String keywords) {
        this.keywords = keywords;
        return this;
    }

    public MillennialAdapterExtras clearKeywords() {
        return setKeywords(null);
    }

    public String getKeywords() {
        return this.keywords;
    }

    public MillennialAdapterExtras setAge(int age) {
        this.age = age;
        return this;
    }

    public MillennialAdapterExtras clearAge() {
        return setAge(-1);
    }

    public int getAge() {
        return this.age;
    }

    public MillennialAdapterExtras setInterstitialTime(InterstitialTime interstitialTime) {
        this.interstitialTime = interstitialTime;
        return this;
    }

    public MillennialAdapterExtras clearInterstitialTime() {
        return setInterstitialTime(null);
    }

    public InterstitialTime getInterstitialTime() {
        return this.interstitialTime;
    }

    public MillennialAdapterExtras setIncomeInUsDollars(Integer income) {
        this.income = income;
        return this;
    }

    public MillennialAdapterExtras clearIncomeInUsDollars() {
        return setIncomeInUsDollars(null);
    }

    public Integer getIncomeInUsDollars() {
        return this.income;
    }

    public MillennialAdapterExtras setChildren(Children children) {
        this.children = children;
        return this;
    }

    public MillennialAdapterExtras clearChildren() {
        return setChildren(null);
    }

    public Children getChildren() {
        return this.children;
    }

    public MillennialAdapterExtras setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public MillennialAdapterExtras clearMaritalStatus() {
        return setMaritalStatus(null);
    }

    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    public MillennialAdapterExtras setEthnicity(Ethnicity ethnicity) {
        this.ethnicity = ethnicity;
        return this;
    }

    public MillennialAdapterExtras clearEthnicity() {
        return setEthnicity(null);
    }

    public Ethnicity getEthnicity() {
        return this.ethnicity;
    }

    public MillennialAdapterExtras setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public MillennialAdapterExtras clearGender() {
        return setGender(null);
    }

    public Gender getGender() {
        return this.gender;
    }

    public MillennialAdapterExtras setPolitics(Politics politics) {
        this.politics = politics;
        return this;
    }

    public MillennialAdapterExtras clearPolitics() {
        return setPolitics(null);
    }

    public Politics getPolitics() {
        return this.politics;
    }

    public MillennialAdapterExtras setEducation(Education education) {
        this.education = education;
        return this;
    }

    public MillennialAdapterExtras clearEducation() {
        return setEducation(null);
    }

    public Education getEducation() {
        return this.education;
    }

    public MillennialAdapterExtras setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public MillennialAdapterExtras clearPostalCode() {
        return setPostalCode(null);
    }

    public String getPostalCode() {
        return this.postalCode;
    }
}
