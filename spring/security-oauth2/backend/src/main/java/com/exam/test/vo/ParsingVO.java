package com.exam.test.vo;

import java.util.List;

import lombok.Data;

@Data
public class ParsingVO {

	private boolean authenticated;
	private List<AuthoritiesVO> authorities;
	private Object authorizedClientRegistrationId;
	private String credentials;
	private Object details;
	private String name;
	private Object principal;
}
