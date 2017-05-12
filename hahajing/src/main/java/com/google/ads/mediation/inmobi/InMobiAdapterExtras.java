package com.google.ads.mediation.inmobi;

import com.google.ads.mediation.NetworkExtras;
import com.inmobi.commons.EducationType;
import com.inmobi.commons.EthnicityType;
import com.inmobi.commons.HasChildren;
import com.inmobi.commons.IMIDType;
import com.inmobi.commons.MaritalStatus;
import com.inmobi.commons.SexualOrientation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class InMobiAdapterExtras implements NetworkExtras {
    private Integer age = null;
    private String areaCode = null;
    private String cities = null;
    private String countries = null;
    private EducationType education = null;
    private EthnicityType ethnicity = null;
    private HasChildren hasChildren = null;
    private Map<IMIDType, String> idtypeParams = new HashMap();
    private Integer income = null;
    private Set<String> interests = null;
    private String keywords = null;
    private String languages = null;
    private MaritalStatus martialStatus = null;
    private String postalCode = null;
    private String reftagKey = null;
    private String reftagValue = null;
    private Map<String, String> requestParams;
    private String searchString = null;
    private SexualOrientation sexualOrientations = null;
    private String states = null;
    private int uidMapFlag;

    public InMobiAdapterExtras setAreaCode(String areaCode) {
        this.areaCode = areaCode;
        return this;
    }

    public InMobiAdapterExtras clearAreaCode() {
        return setAreaCode(null);
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public InMobiAdapterExtras setRefTag(String reftagKey, String reftagValue) {
        this.reftagKey = reftagKey;
        this.reftagValue = reftagValue;
        return this;
    }

    public InMobiAdapterExtras clearRefTag() {
        return setRefTag(null, null);
    }

    public String getRefTagKey() {
        return this.reftagKey;
    }

    public String getRefTagValue() {
        return this.reftagValue;
    }

    public InMobiAdapterExtras setEducation(EducationType education) {
        this.education = education;
        return this;
    }

    public InMobiAdapterExtras clearEducation() {
        return setEducation(null);
    }

    public EducationType getEducation() {
        return this.education;
    }

    public InMobiAdapterExtras setEthnicity(EthnicityType ethnicity) {
        this.ethnicity = ethnicity;
        return this;
    }

    public InMobiAdapterExtras clearEthnicity() {
        return setEthnicity(null);
    }

    public EthnicityType getEthnicity() {
        return this.ethnicity;
    }

    public InMobiAdapterExtras setIncome(Integer income) {
        this.income = income;
        return this;
    }

    public InMobiAdapterExtras clearIncome() {
        return setIncome(null);
    }

    public Integer getIncome() {
        return this.income;
    }

    public InMobiAdapterExtras setAge(Integer age) {
        this.age = age;
        return this;
    }

    public InMobiAdapterExtras clearAge() {
        return setAge(null);
    }

    public Integer getAge() {
        return this.age;
    }

    public InMobiAdapterExtras setInterests(Collection<String> interests) {
        if (interests == null) {
            this.interests = new HashSet();
        } else {
            this.interests = new HashSet(interests);
        }
        return this;
    }

    public InMobiAdapterExtras clearInterests() {
        return setInterests(null);
    }

    public Set<String> getInterests() {
        if (this.interests == null) {
            return null;
        }
        return Collections.unmodifiableSet(this.interests);
    }

    public void addInterests(String interest) {
        if (this.interests == null) {
            this.interests = new HashSet();
        }
        this.interests.add(interest);
    }

    public InMobiAdapterExtras setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public InMobiAdapterExtras clearPostalCode() {
        return setPostalCode(null);
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public InMobiAdapterExtras setSearchString(String searchString) {
        this.searchString = searchString;
        return this;
    }

    public InMobiAdapterExtras clearSearchString() {
        return setSearchString(null);
    }

    public String getSearchString() {
        return this.searchString;
    }

    public InMobiAdapterExtras setHasChildren(HasChildren haschildren) {
        this.hasChildren = haschildren;
        return this;
    }

    public InMobiAdapterExtras clearhasChildren() {
        return setHasChildren(null);
    }

    public HasChildren getHasChildren() {
        return this.hasChildren;
    }

    public InMobiAdapterExtras setSexualOrientation(SexualOrientation sexualOrientation) {
        this.sexualOrientations = sexualOrientation;
        return this;
    }

    public InMobiAdapterExtras clearsexualOrientations() {
        return setSexualOrientation(null);
    }

    public SexualOrientation getSexualOrientations() {
        return this.sexualOrientations;
    }

    public InMobiAdapterExtras setMartialStatus(MaritalStatus martialStatus) {
        this.martialStatus = martialStatus;
        return this;
    }

    public InMobiAdapterExtras clearMartialStatus() {
        return setMartialStatus(null);
    }

    public MaritalStatus getMartialStatus() {
        return this.martialStatus;
    }

    public InMobiAdapterExtras setlanguage(String languages) {
        this.languages = languages;
        return this;
    }

    public InMobiAdapterExtras clearLanguage() {
        return setlanguage(null);
    }

    public String getLangauge() {
        return this.languages;
    }

    public InMobiAdapterExtras setKeywords(String keyword) {
        this.keywords = keyword;
        return this;
    }

    public InMobiAdapterExtras clearKeywords() {
        return setKeywords(null);
    }

    public String getKeywords() {
        return this.keywords;
    }

    public InMobiAdapterExtras setRequestParams(Map<String, String> requestParams) {
        this.requestParams = requestParams;
        return this;
    }

    public InMobiAdapterExtras clearRequestParams() {
        return setRequestParams(null);
    }

    public Map<String, String> getRequestParams() {
        return this.requestParams;
    }

    public InMobiAdapterExtras setCityStateCountry(String city, String state, String country) {
        this.states = state;
        this.cities = city;
        this.countries = country;
        return this;
    }

    public InMobiAdapterExtras clearCityStateCountry() {
        return setCityStateCountry(null, null, null);
    }

    public String getCity() {
        return this.cities;
    }

    public String getState() {
        return this.states;
    }

    public String getCountry() {
        return this.countries;
    }

    public void setDeviceIDMask(int mask) {
        this.uidMapFlag = mask;
    }

    public int getDeviceIdMask() {
        return this.uidMapFlag;
    }

    public InMobiAdapterExtras clearDeviceIdMask() {
        setDeviceIDMask(1);
        return this;
    }

    public void addIDType(IMIDType idtype, String value) {
        if (this.idtypeParams != null) {
            this.idtypeParams.put(idtype, value);
        }
    }

    public String getIDType(IMIDType idtype) {
        if (this.idtypeParams != null) {
            return (String) this.idtypeParams.get(idtype);
        }
        return null;
    }

    public void removeIDType(IMIDType idtype) {
        if (this.idtypeParams != null) {
            this.idtypeParams.remove(idtype);
        }
    }
}
