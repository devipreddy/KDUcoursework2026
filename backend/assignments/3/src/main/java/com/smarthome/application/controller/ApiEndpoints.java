package com.smarthome.application.controller;

public final class ApiEndpoints {

    private ApiEndpoints() {}

    public static final String AUTH_LOGIN = "/login";
    public static final String AUTH_REGISTER = "/register";
    public static final String USER_HOUSES = "/user/houses";
    public static final String USER_ROOMS = "/{houseId}/rooms";
    public static final String USER_DELETED_ROOMS = "/{houseId}/deleted-rooms";
    public static final String USER_ACCOUNT = "/account";
    public static final String HOUSES = "/houses";
    public static final String HOUSES_ALL = "/all";
    public static final String HOUSES_ADD_USER = "/houses/{houseId}/add/user/{userId}";
    public static final String HOUSES_USERS = "/users";
    public static final String HOUSES_ADDRESS = "/houses/{houseId}/address";
    public static final String HOUSES_TRANSFER = "/houses/{houseId}/transfer/{userId}";
    public static final String HOUSES_ROOMS = "/houses/{houseId}/rooms";
    public static final String HOUSES_DELETE = "/houses/{houseId}";
    public static final String HOUSES_DELETE_ROOM = "/houses/{houseId}/rooms/{roomId}";
    public static final String HOUSES_DELETE_MEMBER = "/houses/{houseId}/members/{memberId}";
    public static final String WHOAMI = "/whoami";
    public static final String DELETED_HOUSES = "/deleted-houses";
    public static final String DEVICES_REGISTER = "/houses/{houseId}/devices/register";
    public static final String DEVICES_MOVE = "/houses/{houseId}/devices/{deviceId}/move-room";
    public static final String DEVICES_ROOMS_WITH = "/houses/{houseId}/rooms-with-devices";
    public static final String DEVICES_DELETE = "/houses/{houseId}/devices/{deviceId}";
    public static final String DEVICES_DELETED = "/houses/{houseId}/deleted-devices";
}
