package com.litmus7.inventoryfeed.inventoryfeedui;

import java.io.File;

import com.litmus7.inventoryfeed.constants.FilePaths;
import com.litmus7.inventoryfeed.controller.InventoryFeedController;
import com.litmus7.inventoryfeed.dao.InventoryFeedDao;
import com.litmus7.inventoryfeed.dto.Product;
import com.litmus7.inventoryfeed.dto.Response;
import com.litmus7.inventoryfeed.exception.ProductApplicationException;

public class InventoryFeedUI {
	public static void main(String[] args) {
		Response res;
		String folderpath=FilePaths.INPUT_FOLDER_PATH;
		InventoryFeedController controller=new InventoryFeedController();
		res=controller.saveFilesToDb(folderpath);
		System.out.println(res.message);
	}
}
