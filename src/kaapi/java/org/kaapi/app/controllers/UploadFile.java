package org.kaapi.app.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class UploadFile {

	private MultipartFile[] files;

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public String[] multipleFileUpload(MultipartFile[] file1,String savePath) {

		
		System.err.println(file1.toString());
		String[] name = new String[file1.length];
		String message = "";
		String[] ramdom_file_name = new String[file1.length];
		for(int i=0;i<file1.length;i++){
			
			if(!file1[i].isEmpty()){
				//MultipartFile file = file1[i];
				name[i] = file1[i].getOriginalFilename();
				try{
					ramdom_file_name[i] = UUID.randomUUID() + ".jpg";
					byte[] bytes = file1[i].getBytes();
					
					// creating the directory to store file					
					File path = new File(savePath);
					if(!path.exists()){
						path.mkdir();
					}
					
					// creating the file on server
					
					File serverFile = new File(savePath + File.separator + ramdom_file_name[i]);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					
					System.out.println(serverFile.getAbsolutePath());
					
					message += "You are successfully upload file = " + ramdom_file_name + "</br>";
										
					
				}catch(Exception e){
					//return "You are failed to upload " + name + " => " + e.getMessage();
				}
			}else{
				System.out.println("You are failed to upload "+ ramdom_file_name + " because the file was empty!");
			}
		}
		
		return ramdom_file_name;

	}

	public String sigleFileUpload(MultipartFile file, String savePath) {
		String filename = file.getOriginalFilename();
		String ramdom_file_name = "";
		if (!file.isEmpty()) {
			try {
				ramdom_file_name = UUID.randomUUID() + ".jpg";

				byte[] bytes = file.getBytes();

				File path = new File(savePath);
				if (!path.exists()) {
					path.mkdir();
				}

				// creating the file on server
				File serverFile = new File(savePath + File.separator + ramdom_file_name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				System.out.println(serverFile.getAbsolutePath());
				System.out.println("You are successfully uploaded file " + ramdom_file_name);

			} catch (Exception e) {
				System.out.println("You are failed to upload " + ramdom_file_name + " => " + e.getMessage());
			}
		} else {
			System.out.println("You are failed to upload " + filename + " because the file was empty!");
		}

		return ramdom_file_name;
	}

}
