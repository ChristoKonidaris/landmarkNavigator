package com.example.landmarknavigator;
import java.util.ArrayList;
import java.util.List;

public class Root
{
    private List<Items> items;

    public void setItems(List<Items> items){
        this.items = items;
    }
    public List<Items> getItems(){
        return this.items;
    }
    /*
    Classes list
     */

    public class Items
    {
        private String title;

        private String id;

        private String ontologyId;

        private String resultType;

        private Address address;

        private Position position;

        private List<Access> access;

        private int distance;

        private List<Categories> categories;

        private List<Chains> chains;

        private List<Contacts> contacts;

        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setOntologyId(String ontologyId){
            this.ontologyId = ontologyId;
        }
        public String getOntologyId(){
            return this.ontologyId;
        }
        public void setResultType(String resultType){
            this.resultType = resultType;
        }
        public String getResultType(){
            return this.resultType;
        }
        public void setAddress(Address address){
            this.address = address;
        }
        public Address getAddress(){
            return this.address;
        }
        public void setPosition(Position position){
            this.position = position;
        }
        public Position getPosition(){
            return this.position;
        }
        public void setAccess(List<Access> access){
            this.access = access;
        }
        public List<Access> getAccess(){
            return this.access;
        }
        public void setDistance(int distance){
            this.distance = distance;
        }
        public int getDistance(){
            return this.distance;
        }
        public void setCategories(List<Categories> categories){
            this.categories = categories;
        }
        public List<Categories> getCategories(){
            return this.categories;
        }
        public void setChains(List<Chains> chains){
            this.chains = chains;
        }
        public List<Chains> getChains(){
            return this.chains;
        }
        public void setContacts(List<Contacts> contacts){
            this.contacts = contacts;
        }
        public List<Contacts> getContacts(){
            return this.contacts;
        }

    }

    public class Contacts
    {
        private List<Phone> phone;

        private List<Www> www;

        public void setPhone(List<Phone> phone){
            this.phone = phone;
        }
        public List<Phone> getPhone(){
            return this.phone;
        }
        public void setWww(List<Www> www){
            this.www = www;
        }
        public List<Www> getWww(){
            return this.www;
        }
    }
    public class Address
    {
        private String label;

        private String countryCode;

        private String countryName;

        private String state;

        private String county;

        private String city;

        private String street;

        private String postalCode;

        private String houseNumber;

        public void setLabel(String label){
            this.label = label;
        }
        public String getLabel(){
            return this.label;
        }
        public void setCountryCode(String countryCode){
            this.countryCode = countryCode;
        }
        public String getCountryCode(){
            return this.countryCode;
        }
        public void setCountryName(String countryName){
            this.countryName = countryName;
        }
        public String getCountryName(){
            return this.countryName;
        }
        public void setState(String state){
            this.state = state;
        }
        public String getState(){
            return this.state;
        }
        public void setCounty(String county){
            this.county = county;
        }
        public String getCounty(){
            return this.county;
        }
        public void setCity(String city){
            this.city = city;
        }
        public String getCity(){
            return this.city;
        }
        public void setStreet(String street){
            this.street = street;
        }
        public String getStreet(){
            return this.street;
        }
        public void setPostalCode(String postalCode){
            this.postalCode = postalCode;
        }
        public String getPostalCode(){
            return this.postalCode;
        }
        public void setHouseNumber(String houseNumber){
            this.houseNumber = houseNumber;
        }
        public String getHouseNumber(){
            return this.houseNumber;
        }
    }

    public class Position
    {
        private double lat;

        private double lng;

        public void setLat(double lat){
            this.lat = lat;
        }
        public double getLat(){
            return this.lat;
        }
        public void setLng(double lng){
            this.lng = lng;
        }
        public double getLng(){
            return this.lng;
        }
    }

    public class Access
    {
        private double lat;

        private double lng;

        public void setLat(double lat){
            this.lat = lat;
        }
        public double getLat(){
            return this.lat;
        }
        public void setLng(double lng){
            this.lng = lng;
        }
        public double getLng(){
            return this.lng;
        }
    }

    public class Categories
    {
        private String id;

        private String name;

        private boolean primary;

        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setPrimary(boolean primary){
            this.primary = primary;
        }
        public boolean getPrimary(){
            return this.primary;
        }
    }

    public class Chains
    {
        private String id;

        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
    }

    public class Phone
    {
        private String value;

        public void setValue(String value){
            this.value = value;
        }
        public String getValue(){
            return this.value;
        }
    }

    public class Www
    {
        private String value;

        public void setValue(String value){
            this.value = value;
        }
        public String getValue(){
            return this.value;
        }
    }

}

