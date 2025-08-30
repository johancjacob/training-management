package com.litmus7.inventoryfeed.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.inventoryfeed.constants.LogMessages;
import com.litmus7.inventoryfeed.dao.SqlConstants;
import com.litmus7.inventoryfeed.dto.Product;
import com.litmus7.inventoryfeed.exception.ProductApplicationException;
import com.litmus7.inventoryfeed.util.DbConnection;
import java.util.List;

public class InventoryFeedDao{
	private static final Logger logger=LogManager.getLogger(InventoryFeedDao.class);
	public boolean saveProductsInFileToDb(List<Product> products,File inputFile) throws ProductApplicationException{
		logger.trace(LogMessages.ENTERED_METHOD_LOG_MESSAGE,"saveProductsInFileToDb()");
		try(Connection conn=DbConnection.getConnection()) {
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement(SqlConstants.INSERT_PRODUCT_QUERY);
			for(Product product:products) {
				pstmt.setInt(1,product.getSku());
				pstmt.setString(2,product.getProductName());
				pstmt.setInt(3,product.getQuantity());
				pstmt.setDouble(4,product.getPrice());
				int inserted=pstmt.executeUpdate();
				if(inserted==0) {
					logger.warn(LogMessages.EXITING_FROM_DAO_AFTER_NOT_SAVING_RECORDS_LOG_MESSAGE,inputFile.getName());
					conn.rollback();
					throw new ProductApplicationException(102);
				}
				
			}
			conn.commit();
			logger.info(LogMessages.EXITING_FROM_DAO_AFTER_SAVING_RECORDS_LOG_MESSAGE,inputFile);
			return true;
		}
		catch(SQLException e) {
			logger.warn(LogMessages.EXITING_FROM_DAO_AFTER_NOT_SAVING_RECORDS_LOG_MESSAGE,inputFile.getName());
			throw new ProductApplicationException(102,e);
		}
	}
}