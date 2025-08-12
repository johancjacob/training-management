package com.litmus7.employeemanager.constants;

public class LogMessages {
	public static final String RECEIVED_EMPLOYEE_DATA_LOG_MESSAGE="Received employee data:empId={},firstName={},lastName={},email={},phone={},department={},salary={},joinDate={}";
	public static final String SUCCESSFULLY_SAVED_LOG_MESSAGE="Successfully saved ";
	public static final String SAVE_FAILURE_LOG_MESSAGE="Save failure for ";
	public static final String BUSINESS_EXCEPTION_LOG_MESSAGE="Business exception occured!";
	public static final String UNEXPECTED_ERROR_LOG_MESSAGE="Unexpected error occured!";
	public static final String NOT_CSV_LOG_MESSAGE="Not a csv file";
	public static final String EXIT_METHOD_LOG_MESSAGE="Exiting from {} method";
	public static final String ENTER_METHOD_WITH_EMPIDS_LOG_MESSAGE="Entered {} method with {} empIds";
	public static final String ENTER_METHOD_LOG_MESSAGE="Entered {} method";
	public static final String ENTER_METHOD_WITH_EMPID_LOG_MESSAGE="Entered {} method with empId {}";
	public static final String ENTER_METHOD_WITH_DEPT_LOG_MESSAGE="Entered {} method with dept {}";
	public static final String ENTER_METHOD_WITH_EMPLOYEES_LOG_MESSAGE="Entered {} method with {} employees";
	public static final String VALID_AND_NON_EXISTENT_EMPLOYEE_LOG_MESSAGE="Employee with empId {} passed validation and does not exist in db";
	public static final String VALIDATION_FAILED_LOG_MESSAGE="Validation failed for employee with empId{}";
	public static final String ALREADY_EXISTS_IN_DB_LOG_MESSAGE="Employee with empId {} already exists in db";	
	public static final String EXITING_AFTER_SUCCESS_SAVE_IN_DB="Exiting after successfully saving employee with empId {} into db";
	public static final String EXITING_AFTER_FAILURE_SAVE_IN_DB="Exiting after failing to save employee with empId {} into db";
	public static final String VALID_AND_NON_EXISTENT_EMPLOYEES_LOG_MESSAGE="Saved {} employees who were valid and non existing in db";
	public static final String FETCHED_DETAILS_OF_EXISTING_IDS_FROM_DB_LOG_MESSAGE="Fetched the details of {} existing employees from db";
	public static final String EXITING_AFTER_SUCCESS_UPDATE_IN_DB="Exiting after successfully updating employee with empId {} in db";
	public static final String EXITING_AFTER_SUCCESS_UPDATES_IN_DB="Exiting after successfully updating {} employees in db";
	public static final String EXITING_AFTER_FAILURE_UPDATE_IN_DB="Exiting after failing to update employee with empId {} in db";
	public static final String NON_EXISTING_IN_DB_LOG_MESSAGE="Employee with empId {} does'nt exist in db";
	public static final String EXITING_AFTER_SUCCESS_SAVES_IN_DB="Exiting after successfully saving {} employee(s) into db";
	public static final String EXITING_AFTER_FAILURE_SAVES_IN_DB="Exiting after failing to save any of the employee(s) into db";
	public static final String DB_CONNECTION_SUCCESS="Successfully connected to the database";
	public static final String EXITING_AFTER_SUCCESS_DELETE_IN_DB="Exiting after successfuly deleting employee with empId {} from db";
}
