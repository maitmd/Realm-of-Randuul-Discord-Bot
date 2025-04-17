package utils;

public enum ServerStatusEnum {
    ONLINE,
    OFFLINE,
    BOOTING,
    CRASHED, //If logs were updated in the last ~5 seconds and tail of log files doesn't contain Done message
    UNKNOWN
}