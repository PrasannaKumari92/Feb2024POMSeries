package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {

	public static final String CONFIG_FILE_PATH = "./src/test/resources1/config/config.properties";
	public static final String CONFIG_QA_FILE_PATH = "./src/test/resources1/config/qa.properties";
	public static final String CONFIG_DEV_FILE_PATH = "./src/test/resources1/config/dev.properties";
	public static final String CONFIG_UAT_FILE_PATH = "./src/test/resources1/config/uat.properties";
	public static final String CONFIG_STAGE_FILE_PATH = "./src/test/resources1/config/stage.properties";

	public static final String LOGIN_PAGE_TITLE = "Account Login";

	public static final String ACCOUNTS_PAGE_TITLE = "My Account";

	public static final String LOGIN_PAGE_FRACTION_URL = "route=account/login";

	public static final String ACCOUNT_PAGE_FRACTION_URL = "route=account/account";

	public static final List<String> ACC_PAGE_HEADERS_LIST =
			Arrays.asList("My Account", "My Orders", "My Affiliate Account","Newsletter");

	public static final String USER_REGISTER_SUCCESS_MESSG = "Your Account Has Been Created";


	//******************SHEET CONSTANTS***********//

	public static final String REGISTER_SHEET_NAME = "register";
	public static final String PRODUCT_IMAGES_SHEET_NAME = "productimages";


}

//Note:- App Constants usecases
// 1. what if there is a file path change, we can make a change in one place.
// 2. Keep it in one place, and use it in another place.
// 3. In a team there are 5 people working in the same project,
//    what if they wanted to verify the title and they no needed to used the hard code value
//    instead they can use AppConstants.CONFIG_FILE_PATH etc.
// 4. Another major point is Strings will go to String Constant pool because they are String literals.