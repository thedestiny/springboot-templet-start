package com.destiny.cat.config.plugins;

public enum SensitiveRule {
	
	USER_NAME(s->s.replaceAll("(\\S)\\S(\\S)","$1*$2*")),
	ID_CARD(s->s.replaceAll("(\\d{4})(\\d{4})\\d{6}(\\w{4})","$1$2****$3")),
	PHONE(s->s.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")),
	ADDRESS(s->s.replaceAll("(\\S{8})\\S{4}(\\S*)\\S{4}","$1****$2****"));
	
	public Desensitizer desensitizer;
	
	SensitiveRule(Desensitizer desensitizer) {
		this.desensitizer = desensitizer;
	}
	
	
}
