package com.trade.store.helper;

public final class ConstantsVar {
	 public static final String UPLOAD_SUCCESS="Uploaded the file successfully";
	 public static final String UPLOAD_FAILED="Could not upload the file: ";
	 public static final String NO_FILE="Please upload an excel file!";
	 public static final String FILE_STORE_FAILED="fail to store excel data: ";
	 public static final String LOWER_VERSION="Trade with lower version cannot be stored";
	 public static final String SELECT_PK="select tradePk from Trade";
	 public static final String UPDATE_EXPIRED_FLAG="update trade set expired='Y' where maturity_date > :currDate";
	 public static final String PARSEFILE_FAILED	="fail to parse Excel file: ";
}
