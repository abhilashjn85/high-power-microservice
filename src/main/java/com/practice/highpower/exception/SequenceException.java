package com.practice.highpower.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class SequenceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMessage;

	public SequenceException(String errorMessage) {
		this.errorCode = errorMessage;
	}

}