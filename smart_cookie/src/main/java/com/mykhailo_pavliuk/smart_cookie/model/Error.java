package com.mykhailo_pavliuk.smart_cookie.model;

import com.mykhailo_pavliuk.smart_cookie.model.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Error {

	private String message;
	private ErrorType errorType;
	private LocalDateTime timeStamp;

}
