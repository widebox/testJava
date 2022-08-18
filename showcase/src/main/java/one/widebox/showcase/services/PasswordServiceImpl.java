package one.widebox.showcase.services;

import one.widebox.foggyland.utils.PasswordHelper;

public class PasswordServiceImpl implements PasswordService {

	public String digest(String planText) {
		return PasswordHelper.sha256Hex(planText);
	}
}
