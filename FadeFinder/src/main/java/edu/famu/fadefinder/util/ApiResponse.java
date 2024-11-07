package edu.famu.fadefinder.util;


public record ApiResponse<T>(boolean success, String message, T data, Object error) {

}