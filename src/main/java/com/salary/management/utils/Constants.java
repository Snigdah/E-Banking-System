package com.salary.management.utils;

public final class Constants {

    public static class Response{
        public static final String MESSAGE_KEY = "message";
        public static final String STATUS_KEY = "status";
        public static final String DATA_KEY = "data";
        public static final String STATUS_CODE = "code";
        public static final String META_DATA = "meta";

        private Response() {
            // Private constructor to prevent instantiation
        }
    }

    public static class Employee {
        public static final int[] GRADE_LIMITS = {1, 1, 2, 2, 2, 2};

        private Employee(){
            // Private constructor to prevent instantiation
        }
    }

    private Constants() {
        // Private constructor to prevent instantiation
    }
}
