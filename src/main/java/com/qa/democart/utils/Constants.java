package com.qa.democart.utils;

import java.util.Arrays;
import java.util.List;

public class Constants {

	public static final int DEFAULT_TIME_OUT = 10;

	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String PAGE_HEADER = "Your Store"; // headder of all page

	public static final String Account_PAGE_Title = "My Account";

	public static final String ACCOUNT_PAGE_URL_FRACTION = "route=account/account";

	public static final List<String> EXPECTED_ACC_SEC_List = Arrays.asList("My Account", "My Orders",
			                                                               "My Affiliate Account", "Newsletter");

	public static final String REGISTER_SUCCESS_MESSG = "Your Account Has Been Created";
	
	public static final String REGISTER_SHEET_NAME = "register";
	public static final String PRODUCT_SHEET_NAME = "product";
	
	public static final String SEARCH_SHEET_NAME = "search";
}
